package mx.sonder.scrbkend.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiLogInfo implements Serializable {
    private String apiPath; // 请求路径
    private String httpMethod; // 请求方法
    private Timestamp startTime; // 请求时间
    private Long spendTime; // 请求耗时
    private Integer status; // 请求状态
    private String remoteAddr; // 用户IP
}
