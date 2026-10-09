package com.atguigu.tingshu.common.config.knife4j;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public GroupedOpenApi webApi() {
        return GroupedOpenApi.builder()
                .group("web-api")
                .pathsToMatch("/api/**")
                .build();
    }

    /*后台管理相关文档 接口地址以/admin开头 */
    //@Bean
    //public GroupedOpenApi adminApi() {
    //    return GroupedOpenApi.builder()
    //            .group("admin-api")
    //            .pathsToMatch("/admin/**")
    //            .build();
    //}

    /***
     * @description 自定义接口信息
     */
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title(appName + "听书API接口文档")
                        .version("1.0")
                        .description("听书API接口文档")
                        .contact(new Contact().name("atguigu")));
    }


}
