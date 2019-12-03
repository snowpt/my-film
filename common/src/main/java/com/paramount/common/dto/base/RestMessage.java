package com.paramount.common.dto.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author panteng
 * @description: TODO
 * @date 2019/11/7 9:36
 */
@ApiModel(
        description = "返回响应数据"
)
public class RestMessage<T> implements Serializable {
    private static final long serialVersionUID = -1865510446859810360L;
    @ApiModelProperty("是否成功")
    private boolean success;
    @ApiModelProperty("消息对象")
    private String message;
    @ApiModelProperty("消息代码")
    private String code;
    @ApiModelProperty("返回对象")
    private T data;

    public RestMessage() {
    }

    public static <T> RestMessage<T> newInstance(boolean success, String message, T data) {
        return new RestMessage(success, message, data);
    }

    public static <T> RestMessage<T> newInstance(boolean success, String code, String message, T data) {
        return new RestMessage(success, code, message, data);
    }

    public RestMessage(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.code = "200";
    }

    public RestMessage(boolean success, String code, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String toJsonString() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static <S> RestMessage<S> parseJsonString(String jsonstr, TypeReference<RestMessage<S>> typeReference) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        RestMessage<S> rest = (RestMessage)mapper.readValue(jsonstr, typeReference);
        return rest;
    }
}
