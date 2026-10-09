package com.atguigu.tingshu.common.login;

import com.atguigu.tingshu.common.constant.RedisConstant;
import com.atguigu.tingshu.common.execption.GuiguException;
import com.atguigu.tingshu.common.result.ResultCodeEnum;
import com.atguigu.tingshu.common.util.AuthContextHolder;
import com.atguigu.tingshu.vo.user.UserInfoVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author: atguigu
 * @create: 2026-06-08 09:46
 */
@Slf4j
@Aspect
@Component
public class GuiGuLoginAspect {


    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 登录切面 对所有业务服务模块api包下控制层 且使用自定义登录注解的方法进行增强
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.atguigu.tingshu.*.api.*.*(..)) && @annotation(guiGuLogin)")
    public Object doBasicProfiling(ProceedingJoinPoint pjp, GuiGuLogin guiGuLogin) throws Throwable {
        //1.获取请求头中token
        //1.1 获取请求对象-请求山下文对象获取 RequestAttributes(接口) ServletRequestAttributes(实现类)
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = sra.getRequest();
        //1.2 通过请求对象获取请求头token的值
        String token = request.getHeader("token");

        //2.查询Redis登录时候存入的用户基本信息
        //2.1 构建登录信息缓存的Key 形式 = 前置:令牌值
        String loginKey = RedisConstant.USER_LOGIN_KEY_PREFIX + token;
        //2.2 获取Redis登录时候存入的用户基本信息 UserInfoVo
        UserInfoVo userInfoVo = (UserInfoVo) redisTemplate.opsForValue().get(loginKey);

        //3.如果接口要求必须登录、且用户信息为空。则抛出未登录异常 返回业务状态码208，小程序引导用户登录页
        if (guiGuLogin.required() && userInfoVo == null) {
            throw new GuiguException(ResultCodeEnum.LOGIN_AUTH);
        }

        //4.如果用户信息有值，将用户ID获取到，存入ThreadLocal
        if (userInfoVo != null) {
            AuthContextHolder.setUserId(userInfoVo.getId());
        }
        //5.执行目标方法
        Object retVal = pjp.proceed();

        //6.清理ThreadLocal，避免出现内存泄漏
        AuthContextHolder.removeUserId();
        return retVal;
    }

}
