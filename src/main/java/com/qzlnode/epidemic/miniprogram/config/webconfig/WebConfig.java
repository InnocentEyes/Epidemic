package com.qzlnode.epidemic.miniprogram.config.webconfig;

import com.qzlnode.epidemic.miniprogram.interceptor.LoginInterceptor;
import com.qzlnode.epidemic.miniprogram.msgconverter.ResultConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(Arrays.asList("/","/all","/login","/register","/css/**","/js/**","/font/**","/images/**"));
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new ResultConverter());
    }
}
