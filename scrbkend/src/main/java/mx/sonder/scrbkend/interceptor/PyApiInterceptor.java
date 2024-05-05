package mx.sonder.scrbkend.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import mx.sonder.scrbkend.utils.PyApiAuth;
import mx.sonder.scrbkend.utils.Result;

@Slf4j
@Component
public class PyApiInterceptor implements HandlerInterceptor {

    @Autowired
    private PyApiAuth pyApiAuth;

    @SuppressWarnings("null")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("Python API Interceptor");
        String userAgent = request.getHeader("User-Agent");
        String token = request.getHeader("token");
        boolean checkRes = pyApiAuth.check(userAgent, token);
        if (!checkRes) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            Result<Void> errInfo = Result.error(401, "Unauthorized");
            response.getWriter().write(errInfo.toJson());
        }
        return checkRes;
    }

}
