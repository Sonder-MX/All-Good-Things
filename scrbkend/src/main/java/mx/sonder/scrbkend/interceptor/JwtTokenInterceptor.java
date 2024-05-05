package mx.sonder.scrbkend.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import mx.sonder.scrbkend.utils.JwtUtils;
import mx.sonder.scrbkend.utils.Result;

@Slf4j
@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     * 
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理器
     * @return 是否继续执行
     * @throws Exception 异常
     */
    @SuppressWarnings("null")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader("Authorization");
        log.info("Check Token");
        DecodedJWT parseToken = jwtUtils.verifyJwt(token);
        if (parseToken == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            String msg = (token == null) ? "未登录，请先登录" : "登录信息已过期，请重新登录";
            Result<Void> errInfo = Result.error(401, msg);
            response.getWriter().write(errInfo.toJson());
            return false;
        }
        return true;
    }

}
