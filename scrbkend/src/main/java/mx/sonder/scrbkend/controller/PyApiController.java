package mx.sonder.scrbkend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.sonder.scrbkend.annotation.ApiLog;
import mx.sonder.scrbkend.service.PyApiService;
import mx.sonder.scrbkend.utils.Result;

@RestController
@RequestMapping("/py_api")
public class PyApiController {

    @Autowired
    private PyApiService pyApiService;

    @ApiLog
    @PostMapping
    public Result<Void> handlePyData(@RequestParam String topic) {
        pyApiService.handlePyData(topic);
        return Result.ok();
    }

}
