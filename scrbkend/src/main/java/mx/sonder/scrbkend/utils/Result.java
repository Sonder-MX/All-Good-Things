package mx.sonder.scrbkend.utils;

import java.io.Serializable;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;

import lombok.Data;

@Data
public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private T data;
    private String detail;

    public static <T> Result<T> ok() {
        Result<T> result = new Result<>();
        result.setCode(200);
        return result;
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode(400);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> error(int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> error(int code, String msg, String detail) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setDetail(detail);
        return result;
    }

    public String toJson() {
        return JSONObject.toJSONString(this, JSONWriter.Feature.WriteNulls);
    }
}
