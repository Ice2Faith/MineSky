package com.i2f.common.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ltb
 * @date 2021/8/31
 */
@Data
@NoArgsConstructor
public class RespData {
    public static final int CODE_SUCCESS=200;
    public static final int CODE_ERROR=0;
    public static enum RespCode{
        SUCCESS(200,"success"),
        ERROR(0,"error");

        private int code;
        private String msg;
        private RespCode(int code,String msg){
            this.code=code;
            this.msg=msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
    private int code;
    private  String msg;
    private Object data;
    private Map<String,Object> kvs=new HashMap<String, Object>();

    public RespData(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public RespData(RespCode code, String msg, Object data) {
        this.code = code.getCode();
        this.msg = msg;
        this.data = data;
    }

    public RespData(RespCode code,  Object data) {
        this.code = code.getCode();
        this.msg = code.getMsg();
        this.data = data;
    }

    public RespData add(String key,Object val){
        this.kvs.put(key,val);
        return this;
    }

    public static RespData success(Object data){
        return new RespData(RespCode.SUCCESS,data);
    }

    public static RespData error(String msg){
        return new RespData(RespCode.ERROR,msg,null);
    }
    public static RespData error(int code,String msg){
        return new RespData(code,msg,null);
    }
}
