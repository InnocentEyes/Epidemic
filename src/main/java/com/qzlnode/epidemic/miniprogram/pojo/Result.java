package com.qzlnode.epidemic.miniprogram.pojo;

import com.qzlnode.epidemic.miniprogram.util.Status;

import java.util.List;

/**
 * @author qzlzzz
 */
public class Result<T> {

    private Status status;

    private List<T> result;

    public Result(Status status, List<T> result){
        this.status = status;
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResultSet{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }
}
