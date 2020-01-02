package com.paramount.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.paramount.common.dto.base.RestMessage;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panteng
 * @description:
 * @date 2019/11/7 9:28
 */
@RestController
@Api(value="基础模块",description = "基础模块")
@RequestMapping("/api/base")
public class GjBaseController {

    @ApiOperation(value="登录",notes="登录")
    @PostMapping("/login")
    public RestMessage<String> pageQryMovie(@RequestBody String json) throws Exception{
        JSONObject jsonObject = JSON.parseObject(json);
        String userName = jsonObject.getString("userName");
        String passWord = jsonObject.getString("passWord");
        return RestMessage.newInstance(true, "登录成功","登录成功:"+ json);
    }

}
