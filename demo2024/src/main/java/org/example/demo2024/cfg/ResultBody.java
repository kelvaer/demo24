package org.example.demo2024.cfg;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: demo2024
 * @description: 统一返回体
 * @author: 作者名
 * @create: 2024/02/05
 */
@Data
public class ResultBody<T> implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(
            value = "响应状态码",
            name = "code",
            example = "200"
    )
    private Integer code;

    /**
     * 响应消息
     */
    @ApiModelProperty(
            value = "对message的状态文字描述",
            name = "message",
            example = "成功！"
    )
    private String message;

    private boolean success = true;

    private long timestamp = System.currentTimeMillis();


    /**
     * 响应结果
     */
    @ApiModelProperty(
            value = "返回对象",
            name = "result"
    )
    private T result;

    public ResultBody() {
    }


    public static <Void> ResultBody<Void> success() {
        ResultBody<Void> rb = new ResultBody<>();
        rb.setCode(200);
        rb.setMessage("成功");
        rb.setResult(null);
        return rb;
    }


    public static <T> ResultBody<T> success(T data) {
        ResultBody<T> rb = new ResultBody<>();
        rb.setCode(200);
        rb.setMessage("成功");
        rb.setResult(data);
        return rb;
    }


    public static <T> ResultBody<T> success(String message) {
        ResultBody<T> rb = new ResultBody<>();
        rb.setCode(200);
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }

    public static<T> ResultBody<T> error(String code, String message) {
        ResultBody<T> rb = new ResultBody<>();
        rb.setCode(Integer.valueOf(code));
        rb.setMessage(message);
        rb.setResult(null);
        rb.setSuccess(false);
        return rb;
    }


    public static<T> ResultBody<T> error(String message) {
        ResultBody<T> rb = new ResultBody<>();
        rb.setCode(500);
        rb.setMessage(message);
        rb.setResult(null);
        rb.setSuccess(false);
        return rb;
    }

}
