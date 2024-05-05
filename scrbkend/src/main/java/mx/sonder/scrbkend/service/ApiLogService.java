package mx.sonder.scrbkend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import mx.sonder.scrbkend.entity.ApiLogInfo;
import mx.sonder.scrbkend.entity.vo.PageVo;
import mx.sonder.scrbkend.mapper.ApiLogMapper;
import mx.sonder.scrbkend.utils.PageResult;

@Service
public class ApiLogService {

    @Autowired
    private ApiLogMapper apiLogMapper;

    public void saveApiLog(ApiLogInfo apiLogInfo) {
        apiLogMapper.insertApiLog(apiLogInfo);
    }

    public PageResult<ApiLogInfo> pageQuery(PageVo pageVo) {
        PageHelper.startPage(pageVo.getCurrent(), pageVo.getPageSize());
        Page<ApiLogInfo> pq = apiLogMapper.pageQuery(pageVo);
        PageResult<ApiLogInfo> data = new PageResult<ApiLogInfo>();
        data.setCurrent(pq.getPageNum());
        data.setPageSize(pq.getPageSize());
        data.setTotal(pq.getTotal());
        data.setResult(pq.getResult());
        return data;
    }

}
