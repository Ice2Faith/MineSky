package com.i2f.service.user.model;


import com.i2f.common.data.ISysUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ltb
 * @date 2021/9/2
 */
@Data
@NoArgsConstructor
public class SysUser implements ISysUser, Serializable {
    private static final long serialVersionUID = 1L;
    private String userName;
    private String password;

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
