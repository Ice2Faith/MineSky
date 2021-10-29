package com.i2f.common.util;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ltb
 * @date 2021/9/2
 */
@Data
@NoArgsConstructor
public class TestBean {
    private String key;
    private String val;

    public static void main(String[] args){
        TestBean bean=new TestBean();
        String key="253";
        key=AESUtil.genKey(key);
        bean.setKey(key);
        bean.setVal("aaa");
        String ss=AESUtil.encryptObj(bean,key);
        System.out.println("ss:"+ss);
        TestBean rb=AESUtil.decryptObj(ss,key,TestBean.class);
        System.out.println("rb:"+rb);

        String bd="clm28xbKwcoPX7k3EWjVVFLx9k0bnfMHyULZr3nDUiInhNBmGY65Ck3p7M68AR/0ztJInjyWCj4uyzcIqmSUJg==";
        TestBean bb=AESUtil.decryptObj(bd,key,TestBean.class);
        System.out.println("bb:"+bb);
    }
    //
}
