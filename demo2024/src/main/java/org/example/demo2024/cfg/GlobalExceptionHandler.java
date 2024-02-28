package org.example.demo2024.cfg;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: demo2024
 * @description: 全局异常处理
 * @author: 作者名
 * @create: 2024/02/05
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public ResultBody<Void> handleServiceException(ServiceException e, HttpServletRequest request) {
        log.error(e.getMessage());
        Integer code = e.getCode();
        return ObjectUtil.isNotNull(code) ? ResultBody.error(String.valueOf(code), e.getMessage()) : ResultBody.error(e.getMessage());
    }


    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultBody<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                       HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return ResultBody.error(e.getMessage());
    }


    /**
     * 请求路径中缺少必需的路径变量
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public ResultBody<Void> handleMissingPathVariableException(MissingPathVariableException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求路径中缺少必需的路径变量'{}',发生系统异常.", requestURI);
        return ResultBody.error(String.format("请求路径中缺少必需的路径变量[%s]", e.getVariableName()));
    }

    /**
     * 请求参数类型不匹配
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResultBody<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求参数类型不匹配'{}',发生系统异常.", requestURI);
        return ResultBody.error(String.format("请求参数类型不匹配，参数[%s]要求类型为：'%s'，但输入值为：'%s'", e.getName(), e.getRequiredType().getName(), e.getValue()));
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultBody<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return ResultBody.error(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public ResultBody<Void> handleBindException(BindException e) {
        log.error(e.getMessage());
        List<ObjectError> allErrors = e.getAllErrors();
        if (CollUtil.isEmpty(allErrors)){
            return ResultBody.error("");
        }
        List<String> collect = allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        String message = Joiner.on(",").join(collect);
        return ResultBody.error(message);
    }






    /**
     * Validated全局自动校验,要@Validated加在Controller类名上,同时手动使用BindingResult
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResultBody<Void> constraintViolationExceptionHandler(ConstraintViolationException e) {
        log.error("Validated全局自动校验出的异常",e);
        //校验框架会自动返回多条约束提示信息的拼接
        //格式如 test1234.req.company.name: 长度需要在4-30之间,test1234.req.account: 长度需要在2-30之间
        //(逗号拼接的字符串  接口方法名.参数名.子参数对象.子参数属性:描述)
//        return new ResultVO<>(ResultCodeEnum.VALIDATE_FAILED,
//                e.getMessage());
        return ResultBody.error(e.getMessage());

    }
//————————————————
//    版权声明：本文为CSDN博主「ThinkPet」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/ThinkPet/article/details/131025891


    /**
     * 处理 数组越界 异常
     */
    @ExceptionHandler({ArrayIndexOutOfBoundsException.class})
    public ResultBody<Void> arrayIndexOutOfBoundsExceptionHandler(ArrayIndexOutOfBoundsException e,HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生数组越界异常.", requestURI);
        return ResultBody.error(e.getMessage());

    }


    /**
     * 处理 ArithmeticException 类的异常 （如除0异常）
     */
    @ExceptionHandler({ArithmeticException.class})
    public ResultBody<Void> arithmeticExceptionHandler(ArithmeticException e,HttpServletRequest request) {
//        return new ResultVO(ResultCodeEnum.ARITHMETIC_FAILED, e.getMessage());
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生算术异常.", requestURI);
        return ResultBody.error(e.getMessage());
    }

    /**
     * 手动抛出的或 Assert断言的 非法参数异常
     *
     * @param e IllegalArgumentException
     * @return ResultVO
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public ResultBody<Void> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("人工抛出异常的IllegalArgumentException",e);
//        return new ResultVO<>(ResultCodeEnum.IllEGAL_ARG_EXCEPTION,
//                e.getMessage());
        return ResultBody.error(e.getMessage());

    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultBody<Void> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return ResultBody.error(e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public ResultBody<Void> handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return ResultBody.error(e.getMessage());
    }


}
