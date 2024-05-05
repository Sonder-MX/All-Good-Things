package mx.sonder.scrbkend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import mx.sonder.scrbkend.interceptor.JwtTokenInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private JwtTokenInterceptor jtInterceptor;

    @SuppressWarnings("null")
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(jtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/auth/**");
    }

}
