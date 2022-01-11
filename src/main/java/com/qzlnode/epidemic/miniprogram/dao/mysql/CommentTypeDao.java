package com.qzlnode.epidemic.miniprogram.dao.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import com.qzlnode.epidemic.miniprogram.pojo.CommentType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author qzlzzz
 */
@Mapper
public interface CommentTypeDao extends BaseMapper<CommentType> {

}
