package cn.edu.bit.job_management_backend.controller;

import cn.edu.bit.job_management_backend.pojo.Job;
import cn.edu.bit.job_management_backend.pojo.PageResult;
import cn.edu.bit.job_management_backend.service.JobService;
import cn.edu.bit.job_management_backend.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/job")
@Slf4j
public class JobController {

    @Autowired
    private JobService jobService;

    @PutMapping
    public Result add(@RequestBody Job job){
        jobService.add(job);
        return Result.success();
    }

    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        jobService.deleteByID(id);
        return Result.success();
    }

    /**
     * 根据ID前移
     * @param id
     * @return
     */
    @PostMapping("/up/{id}")
    public Result up(@PathVariable Integer id){
        jobService.upByID(id);
        return Result.success();
    }

    /**
     * 根据ID后移
     * @param id
     * @return
     */
    @PostMapping("/down/{id}")
    public Result down(@PathVariable Integer id){
        jobService.downByID(id);
        return Result.success();
    }

    /**
     * 根据ID置顶
     * @param id
     * @return
     */
    @PostMapping("/top/{id}")
    public Result top(@PathVariable Integer id){
        jobService.moveToTop(id);
        return Result.success();
    }

    /**
     * 根据ID修改
     */
    @PostMapping("/update")
    public Result update(@RequestBody Job job){
        jobService.update(job);
        return Result.success();
    }

    /**
     * 查页面
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result getPage(Integer page, Integer pageSize){
        PageResult pageResult = jobService.getPage(page, pageSize);
        return Result.success(pageResult);
    }
}
