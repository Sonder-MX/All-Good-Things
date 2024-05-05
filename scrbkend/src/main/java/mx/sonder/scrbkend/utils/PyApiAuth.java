package mx.sonder.scrbkend.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PyApiAuth {

    @Value("${py-api.user-agent}")
    private String userAgent;

    @Value("${py-api.token}")
    private String token;

    public boolean check(String ua, String token) {
        if (ua == null || token == null || ua.isEmpty() || token.isEmpty()) {
            return false;
        }
        return ua.equals(userAgent) && token.equals(this.token);
    }

}
