package mx.sonder.scrbkend.utils;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PageResult<T> implements Serializable {
    private Integer current;
    private Integer pageSize;
    private Long total;
    private List<T> result;
}
