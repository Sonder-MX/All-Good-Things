package mx.sonder.scrbkend.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import mx.sonder.scrbkend.utils.Result;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
public class GlobalException {

    /**
     * 捕获业务异常
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result<Void> exceptionHandler(RuntimeException ex) {
        return Result.error(ex.getMessage());
    }

    /**
     * 捕获SQL唯一约束异常
     * 
     * @param ex SQL唯一约束异常
     * @return 响应结果
     */
    @ExceptionHandler
    public Result<Void> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String errorMsg = ex.getMessage();
        if (errorMsg.contains("Duplicate entry")) {
            String[] errorList = errorMsg.split(" ");
            return Result.error(errorList[2] + "账号已存在");
        } else {
            return Result.error(500, "服务器异常，请稍后再试");
        }
    }

}
