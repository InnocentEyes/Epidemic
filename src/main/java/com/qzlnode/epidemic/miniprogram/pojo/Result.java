package com.qzlnode.epidemic.miniprogram.pojo;

import com.qzlnode.epidemic.miniprogram.util.Status;

import java.util.List;
import java.util.Set;

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

    public Result(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
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
