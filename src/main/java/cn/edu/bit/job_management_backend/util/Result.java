package cn.edu.bit.job_management_backend.util;

import cn.edu.bit.job_management_backend.constant.ResultConstant;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private Integer code; //编码：1成功，0和其它数字为失败
    private String msg; //错误信息
    private Object data; //数据

    /**
     * 执行成功，没有返回数据
     * @return
     */
    public static Result success() {
        Result result = new Result();
        result.code = ResultConstant.SUCCESS.getCode();
        result.msg = ResultConstant.SUCCESS.getMsg();
        return result;
    }

    /**
     * 执行成功，有返回数据
     * @param data 返回数据
     * @return
     */
    public static Result success(Object data) {
        Result result = new Result();
        result.data = data;
        result.code = ResultConstant.SUCCESS.getCode();
        result.msg = ResultConstant.SUCCESS.getMsg();
        return result;
    }

    /**
     *  执行失败，只返回错误码和原因
     * @param resultConstant 错误状态
     * @return
     */
    public static Result error(ResultConstant resultConstant) {
        Result result = new Result();
        result.code = resultConstant.getCode();
        result.msg = resultConstant.getMsg();
        return result;
    }
}
