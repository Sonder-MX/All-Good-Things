package mx.sonder.scrbkend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import mx.sonder.scrbkend.entity.AnalyzeData;
import mx.sonder.scrbkend.entity.vo.PageVo;
import mx.sonder.scrbkend.mapper.AnalyzeDataMapper;
import mx.sonder.scrbkend.utils.PageResult;

@Service
public class AnalyzeDataService {

    @Autowired
    private AnalyzeDataMapper analyzeDataMapper;

    public void insert(AnalyzeData analyzeData) {
        analyzeDataMapper.insert(analyzeData);
    }

    public PageResult<AnalyzeData> pageQuery(PageVo pageVo) {
        PageHelper.startPage(pageVo.getCurrent(), pageVo.getPageSize());
        Page<AnalyzeData> pq = analyzeDataMapper.pageQuery();
        PageResult<AnalyzeData> data = new PageResult<AnalyzeData>();
        data.setCurrent(pq.getPageNum());
        data.setPageSize(pq.getPageSize());
        data.setTotal(pq.getTotal());
        data.setResult(pq.getResult());
        return data;
    }

}
