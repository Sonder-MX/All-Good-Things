package mx.sonder.scrbkend.entity.vo;

import java.io.Serializable;

public class PageVo implements Serializable {
    private Integer current;
    private Integer pageSize;
    private String keyword;

    public Integer getCurrent() {
        if (this.current == null || this.current < 1) {
            this.current = 1;
        }
        return this.current;
    }

    public Integer getPageSize() {
        if (this.pageSize == null || this.pageSize < 1 || this.pageSize > 50) {
            this.pageSize = 15;
        }
        return this.pageSize;
    }

    public String getKeyword() {
        return this.keyword;
    }
}
