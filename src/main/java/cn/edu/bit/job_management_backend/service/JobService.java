package cn.edu.bit.job_management_backend.service;

import cn.edu.bit.job_management_backend.constant.JobConstant;
import cn.edu.bit.job_management_backend.pojo.Job;
import cn.edu.bit.job_management_backend.pojo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JobService {

    private static Integer id = 0;
    private List<Job> jobs = new ArrayList<>(); // 第0个是正在执行的

    synchronized public void add(Job job){
        job.setId(id++);
        job.setState(JobConstant.WAIT);
        job.setCreateTime(LocalDateTime.now());
        jobs.add(job);
    }

    // 分页查询
    synchronized public PageResult getPage(int page, int pageSize){
        PageResult pageResult = new PageResult();

        // 总记录数
        pageResult.setTotal(jobs.size());

        // 获取相应页的数据
        List records = new ArrayList();
        int i = (page - 1) * pageSize;
        int limit = Math.min(i + pageSize, jobs.size());
        while(i < limit){
            records.add(jobs.get(i++));
        }
        pageResult.setRecords(records);

        return pageResult;
    }

    // 向前移动任务
    synchronized public void upByID(Integer id){
        int len = jobs.size();
        for(int i = 2; i<len; i++){
            if(jobs.get(i).getId().equals(id)){
                Job pre = jobs.get(i-1);
                jobs.set(i-1, jobs.get(i));
                jobs.set(i, pre);

                break;
            }
        }
    }

    // 向后移动任务
    synchronized public void downByID(Integer id){
        int len = jobs.size() - 1;
        for(int i = 1; i < len; i++){
            if(jobs.get(i).getId().equals(id)){
                Job next = jobs.get(i+1);
                jobs.set(i+1, jobs.get(i));
                jobs.set(i, next);

                break;
            }
        }
    }

    // 删除任务
    synchronized public void deleteByID(Integer id){
        jobs = jobs.stream()
                .filter(job -> !job.getId().equals(id))
                .collect(Collectors.toList());
    }

    // 执行新任务
    synchronized public void tryRunNewJob(){
        log.info("尝试调用任务...");
        // 如果没有任务或有任务正在执行直接返回
        if(this.jobs.size() == 0){
            return;
        }

        Job job = jobs.get(0);

        /*
        * 判断首个job的情况:
        *   - 如果是正在执行，那么直接返回;
        *   - 如果是执行结果了，那么移除;
        *   - 如果是WAIT，那么什么都不做
         * */
        synchronized (job){
            if(job.getState() == JobConstant.RUN){
                return;
            }else if(job.getState() == JobConstant.FINISH){
                jobs.remove(0);
            }
        }

        if(jobs.size() == 0){
            return;
        }

        final Job newJob = jobs.get(0);
        newJob.setState(JobConstant.RUN);

        // 创建一个线程取执行
        new Runnable(){
            @Override
            public void run() {
                log.info("开始执行任务" + newJob);
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", newJob.getCmd());
                    processBuilder.directory(new File(newJob.getDir()));
                    Process process = processBuilder.start();
                    int exitCode = process.waitFor();

                    if(exitCode != 0){
                        log.info("执行任务失败:" + newJob.getCmd());
                    }

                    synchronized (newJob){
                        newJob.setState(JobConstant.FINISH);
                    }
                }catch (IOException | InterruptedException e){
                    throw new RuntimeException(e);
                }

                log.info("任务完成：" + newJob);
            }
        }.run();
    }

    // 置顶
    synchronized public void moveToTop(Integer id){
        int len = jobs.size();
        for(int i = 1; i<len; i++){
            if(jobs.get(i).getId().equals(id)){
                Job job = jobs.get(i);

                for(int j = i; j > 1; j--){
                    jobs.set(j, jobs.get(j-1));
                }

                jobs.set(1, job);
            }
        }
    }

    // 修改
    synchronized public void update(Job job){
        int len = jobs.size();
        for(int i = 1; i < len; i++){
            if(jobs.get(i).getId().equals(job.getId())){
                jobs.set(i, job);
                break;
            }
        }
    }
}
