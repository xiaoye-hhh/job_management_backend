package cn.edu.bit.job_management_backend.constant;

public enum ResultConstant {
    SUCCESS(0, "执行成功"),
    ERROR(1, "发生未知错误"),
    LOGIN_ERROR(2, "用户名或密码错误");

    private Integer code; // 状态码
    private String msg; // 对应信息

    ResultConstant(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
