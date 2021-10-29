package com.i2f.feign.api.fallback;

import com.i2f.feign.api.ITestAdminApi;
import org.springframework.stereotype.Component;

/**
 * @author ltb
 * @date 2021/9/1
 */
@Component
public class TestAdminApiFallback implements ITestAdminApi {
    @Override
    public String testSvc(String val,String token) {
        return "admin fallback svc"+val;
    }
}
