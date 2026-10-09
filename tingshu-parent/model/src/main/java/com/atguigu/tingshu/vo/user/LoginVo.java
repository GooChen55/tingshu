package com.atguigu.tingshu.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description="登录对象")
public class LoginVo {

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "手机验证码")
    private String code;
}
