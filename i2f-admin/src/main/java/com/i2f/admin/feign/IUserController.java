package com.i2f.admin.feign;

import com.i2f.admin.feign.fallback.UserControllerFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author ltb
 * @date 2021/9/1
 */

/**
 * 踩坑大全：
 * Feign
 * FeignClient：
 * value:指定微服务名称
 * fallback:指定降级处理类，需要实现此接口，并且在容器中，也就是由@Component注解
 *
 * GetMapping
 * 如果指定GET方法时，所有参数需要指定@RequestParam注解，否则会按照post处理
 *
 * 如果有自定义的请求头时，固定值可以直接使用GetMapping等中的headers即可
 * 但是如果是变量，可以使用@RequestHeader进行设置
 *
 * 返回值问题，不能图简单，直接用Object接受，否则调用成功但反序列化失败，同样会触发降级
 * 因此，可以考虑直接用String接收，这样拿到原始的JSON串，自行反序列化
 */
@FeignClient(value = "ui-app",fallback = UserControllerFallback.class)
public interface IUserController {
    @GetMapping(value = "/app/test/svc")
    String testSvc(@RequestParam("val") String val, @RequestHeader("Authorization") String token);
}
