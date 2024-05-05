package mx.sonder.scrbkend.entity.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class UserInfoVO implements Serializable {
    private Long id;
    private String username;
    private String email;
    private Boolean isActive;
    private Timestamp created;
}
