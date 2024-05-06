package mx.sonder.scrbkend.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import mx.sonder.scrbkend.utils.Result;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public Result<Void> handleError(HttpServletRequest req) {
        Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return Result.error(HttpStatus.NOT_FOUND.value(), "Not Found");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return Result.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return Result.error(HttpStatus.FORBIDDEN.value(), "Forbidden");
            } else {
                return Result.error(HttpStatus.BAD_REQUEST.value(), "Bad Request");
            }
        }
        return Result.error(500, "Internal Server Error");
    }

}
