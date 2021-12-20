package com.qzlnode.epidemic.miniprogram.msgconverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzlnode.epidemic.miniprogram.pojo.City;
import com.qzlnode.epidemic.miniprogram.pojo.CommentType;
import com.qzlnode.epidemic.miniprogram.pojo.Province;
import com.qzlnode.epidemic.miniprogram.pojo.Result;
import com.qzlnode.epidemic.miniprogram.util.JsonUtil;
import com.qzlnode.epidemic.miniprogram.util.Status;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author qzlzzz
 */
public class ResultConverter implements HttpMessageConverter<Result> {

    static ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return clazz.isAssignableFrom(Result.class);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return MediaType.parseMediaTypes(MediaType.APPLICATION_JSON_VALUE);
    }

    @Override
    public Result read(Class<? extends Result> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public void write(Result result, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        OutputStream body = outputMessage.getBody();
        if(result.getResult() == null || result.getResult().size() == 0){
            body.write(Status.UNSUCCESSFUL.getReasonPhrase().getBytes(StandardCharsets.UTF_8));
            return;
        }
        Object inner = result.getResult().get(0);
        String jsonData = result.getStatus().getReasonPhrase();
        if(inner instanceof CommentType){
            jsonData = jsonData + "," +JsonUtil.TypeToJson((List<CommentType>) result.getResult());
        }else if(inner instanceof City){
            jsonData = jsonData + "," + JsonUtil.CityToJson((List<City>) result.getResult());
        }else if(inner instanceof Province){
            jsonData = jsonData + "," + JsonUtil.ProvinceToJson((List<Province>) result.getResult());
        }
        body.write(jsonData.getBytes(StandardCharsets.UTF_8));
    }
}
