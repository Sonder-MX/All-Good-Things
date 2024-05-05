package mx.sonder.scrbkend.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

import mx.sonder.scrbkend.entity.AnalyzeData;

@Mapper
public interface AnalyzeDataMapper {

    public void insert(AnalyzeData analyzeData);

    public Page<AnalyzeData> pageQuery();

}
