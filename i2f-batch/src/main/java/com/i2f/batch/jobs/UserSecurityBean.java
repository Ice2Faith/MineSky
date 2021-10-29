package com.i2f.batch.jobs;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author ltb
 * @date 2021/9/18
 */
@Data
@NoArgsConstructor
@Entity(name="sys_user_secure")
public class UserSecurityBean{
    @Id
    private Integer id;
    private String account;
    private String password;
    private String tel;
    private Integer age;
    private String sex;

}
