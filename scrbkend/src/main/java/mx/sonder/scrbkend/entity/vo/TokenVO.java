package mx.sonder.scrbkend.entity.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class TokenVO implements Serializable {
    private String username;
    private String token;
    private Date expire;
}
