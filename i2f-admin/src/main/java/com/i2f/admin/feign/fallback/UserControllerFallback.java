package com.i2f.admin.feign.fallback;

import com.i2f.admin.feign.IUserController;
import org.springframework.stereotype.Component;

/**
 * @author ltb
 * @date 2021/9/1
 */
@Component
public class UserControllerFallback implements IUserController {
    @Override
    public String testSvc(String val,String token) {
        return "admin fallback svc"+val;
    }
}
