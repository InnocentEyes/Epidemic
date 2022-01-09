package com.qzlnode.epidemic.miniprogram.dao.redis.Impl;

import com.qzlnode.epidemic.miniprogram.dao.redis.CommonRedis;
import com.qzlnode.epidemic.miniprogram.dao.redis.Operations;
import com.qzlnode.epidemic.miniprogram.pojo.CommentDetail;
import org.springframework.data.redis.core.ZSetOperations;

public class RedisForCommentDetail implements CommonRedis<CommentDetail>,Operations<ZSetOperations>{
    @Override
    public String[] get(CommentDetail object) {
        return new String[0];
    }

    @Override
    public boolean set(CommentDetail object) {
        return false;
    }



    @Override
    public ZSetOperations getOperation() {
        return null;
    }
}
