package mx.sonder.scrbkend.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

import mx.sonder.scrbkend.entity.ApiLogInfo;
import mx.sonder.scrbkend.entity.vo.PageVo;

@Mapper
public interface ApiLogMapper {

    public void insertApiLog(ApiLogInfo apiLogInfo);

    public Page<ApiLogInfo> pageQuery(PageVo pageVo);

}
