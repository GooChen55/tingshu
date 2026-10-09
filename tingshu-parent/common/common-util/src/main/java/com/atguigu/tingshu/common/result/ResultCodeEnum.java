package com.atguigu.tingshu.common.result;

import lombok.Getter;

/**
 * 统一返回结果状态信息类
 *
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    SERVICE_ERROR(2012, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    ILLEGAL_REQUEST(205, "非法请求"),
    REPEAT_SUBMIT(206, "重复提交"),
    ARGUMENT_VALID_ERROR(210, "参数校验异常"),
    SIGN_ERROR(300, "签名错误"),
    SIGN_OVERDUE(301, "签名已过期"),

    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限"),
    ACCOUNT_ERROR(214, "账号不正确"),
    PASSWORD_ERROR(215, "密码不正确"),
    PHONE_CODE_ERROR(215, "手机验证码不正确"),
    LOGIN_MOBLE_ERROR( 216, "账号不正确"),
    ACCOUNT_STOP( 216, "账号已停用"),
    NODE_ERROR( 217, "该节点下有子节点，不可以删除"),

    VOD_FILE_ID_ERROR( 220, "声音媒体id不正确"),

    XXL_JOB_ERROR(210, "调度操作失败"),

    ACCOUNT_LESS(220, "账户余额不足"),
    ACCOUNT_LOCK_ERROR(221, "账户余额锁定失败"),
    ACCOUNT_UNLOCK_ERROR(221, "账户余额解锁失败"),
    ACCOUNT_MINUSLOCK_ERROR(221, "账户余额扣减失败"),
    ACCOUNT_LOCK_REPEAT(221, "重复锁定"),
    ACCOUNT_LOCK_RESULT_NULL(221, "锁定账号结果对象为空"),
    ORDER_SUBMIT_REPEAT(221, "超时或重复提交订单"),

    NO_BUY_NOT_SEE(230, "未购买不能观看"),

    EXIST_NO_EXPIRE_LIVE(230, "当前存在未过期直播"),

    REPEAT_BUY_ERROR(231, "已经购买过该专辑"),

    ;

    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
