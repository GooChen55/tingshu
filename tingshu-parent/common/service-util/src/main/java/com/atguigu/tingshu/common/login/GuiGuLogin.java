package com.atguigu.tingshu.common.login;

import java.lang.annotation.*;

/***
 * 自定义认证注解，用于校验认证状态
 * 使用到业务微服务模块 api 包 controller层方法上
 * 元注解：
 *  @Target：表示该注解用于什么地方
 *  @Retention: 注解保留到哪个阶段 如果是SOURCE，注解仅存在于源码中，编译为字节码后丢弃
 *  @Inherited: 表示子类可以继承父类中的该注解
 *  @Documented: 表示该注解将被包含在javadoc中
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface GuiGuLogin {


    /**
     * 是否需要登录
     * @return
     */
    boolean required() default true;

}
