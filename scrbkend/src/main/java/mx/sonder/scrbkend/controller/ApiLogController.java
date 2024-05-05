package mx.sonder.scrbkend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.sonder.scrbkend.entity.ApiLogInfo;
import mx.sonder.scrbkend.entity.vo.PageVo;
import mx.sonder.scrbkend.service.ApiLogService;
import mx.sonder.scrbkend.utils.PageResult;
import mx.sonder.scrbkend.utils.Result;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/log")
public class ApiLogController {

    @Autowired
    private ApiLogService apiLogService;

    @PostMapping("/list")
    public Result<PageResult<ApiLogInfo>> apiLogList(@RequestBody PageVo pageVo) {
        return Result.ok(apiLogService.pageQuery(pageVo));
    }

}
