package mx.sonder.scrbkend.entity.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class RegOrLoginVO implements Serializable {
    private String email;
    private String username;
    private String password;
    private String code;

    public boolean isValid() {
        if (this.username == null || this.username.trim().isEmpty()) {
            this.username = this.email;
        }
        return email != null && email.contains("@") && password != null && !password.trim().isEmpty();
    }
}
