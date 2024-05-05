package mx.sonder.scrbkend.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class AnalyzeData {
    private Long id;
    private String type;
    private String data;
    private Timestamp created;

    public AnalyzeData(String type, String data) {
        this.type = type;
        this.data = data;
    }
}
