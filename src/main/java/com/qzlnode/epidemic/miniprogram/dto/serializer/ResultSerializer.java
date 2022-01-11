package com.qzlnode.epidemic.miniprogram.dto.serializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.qzlnode.epidemic.miniprogram.dto.CommentView;
import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author qzlzzz
 */
public class ResultSerializer extends JsonSerializer<List<Object>> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void serialize(List<Object> objects, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(objects == null || objects.size() == 0){
            jsonGenerator.writeObject(null);
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION,false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jsonGenerator.writeStartArray();
        if(objects.get(0) instanceof Comment){
            Comment[] comments = objects.stream()
                    .filter(Comment.class :: isInstance)
                    .map(Comment.class :: cast)
                    .toArray(Comment[]::new);
            boolean target = (comments[0].getComment() == null) ? Boolean.TRUE : Boolean.FALSE;
            if(!target){
                mapper.setConfig(mapper.getSerializationConfig().withView(CommentView.Detail.class));
            }else {
                mapper.setConfig(mapper.getSerializationConfig().withView(CommentView.class));
            }
            jsonGenerator.setCodec(mapper);
            for (Comment comment : comments) {
                jsonGenerator.writeObject(comment);
            }
        }else {
            for (Object object : objects) {
                jsonGenerator.writeObject(object);
            }
        }
        jsonGenerator.writeEndArray();
    }
}
