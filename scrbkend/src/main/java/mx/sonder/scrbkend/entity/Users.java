package mx.sonder.scrbkend.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Boolean isActive;
    private Timestamp created;
}
