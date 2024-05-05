package mx.sonder.scrbkend.aspect;

import java.sql.Timestamp;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import mx.sonder.scrbkend.service.ApiLogService;
import mx.sonder.scrbkend.utils.Result;
import mx.sonder.scrbkend.entity.ApiLogInfo;

/**
 * 记录controller层接口日志
 */
@Aspect
@Component
public class ApiLogAspect {

    @Autowired
    private ApiLogService apiLogService;

    @Pointcut("execution(* mx.sonder.scrbkend.controller.*.*(..)) && @annotation(mx.sonder.scrbkend.annotation.ApiLog)")
    public void recordApiLogPointCut() {
    }

    @Around("recordApiLogPointCut()")
    public Object aroundApiLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long spendTime = endTime - startTime;
        ApiLogInfo apiLogInfo = new ApiLogInfo();

        // 获取请求路径
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            String apiPath = attributes.getRequest().getRequestURI();
            apiLogInfo.setApiPath(apiPath);
        } else {
            apiLogInfo.setApiPath("未知");
        }
        // 获取请求方法
        @SuppressWarnings("null")
        String httpMethod = attributes.getRequest().getMethod();
        apiLogInfo.setHttpMethod(httpMethod);

        apiLogInfo.setStartTime(new Timestamp(startTime));
        apiLogInfo.setSpendTime(spendTime);
        apiLogInfo.setStatus(getResultCode(result));
        String ip = "未知";
        if (attributes != null) {
            ip = attributes.getRequest().getRemoteAddr();
        }
        apiLogInfo.setRemoteAddr(ip);
        apiLogService.saveApiLog(apiLogInfo);

        return result;
    }

    @SuppressWarnings("rawtypes")
    private Integer getResultCode(Object result) {
        if (result instanceof Result) {
            return ((Result) result).getCode();
        } else {
            return 500;
        }
    }

}
