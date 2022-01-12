package com.qzlnode.epidemic.miniprogram.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qzlnode.epidemic.miniprogram.dto.ResultView;
import com.qzlnode.epidemic.miniprogram.dto.serializer.ResultSerializer;

import java.util.List;

/**
 * @author qzlzzz
 */
public class Result {

    @JsonView(ResultView.class)
    private Integer code;

    @JsonView(ResultView.class)
    private String reason;

    @JsonView(ResultView.Detail.class)
    @JsonSerialize(using = ResultSerializer.class)
    private List<Object> result;

    public Result(String reason, List<Object> result) {
        this.reason = reason;
        this.result = result;
    }

    public Result(Integer code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public Result(Integer code, String reason, List<Object> result) {
        this.code = code;
        this.reason = reason;
        this.result = result;
    }

    public Result(String reason) {
        this.reason = reason;
    }

    public Result(Integer status) {
        this.code = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Object> getResult() {
        return result;
    }

    public void setResult(List<Object> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
