package com.qzlnode.epidemic;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzlnode.epidemic.miniprogram.dto.CommentTypeView;
import com.qzlnode.epidemic.miniprogram.dto.ResultView;
import com.qzlnode.epidemic.miniprogram.pojo.CommentType;
import com.qzlnode.epidemic.miniprogram.pojo.Province;
import com.qzlnode.epidemic.miniprogram.pojo.Result;
import com.qzlnode.epidemic.miniprogram.util.ArgsHandler;
import com.qzlnode.epidemic.miniprogram.util.In;
import com.qzlnode.epidemic.miniprogram.util.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

//@SpringBootTest
class EpidemicApplicationTests {

//	@Autowired
//	private StringRedisTemplate redis;

	@Test
	void contextLoads() {
		In in = new In("C:\\Users\\邱泽林\\Desktop\\期中\\last_day_corona_virus_of_china.json");
		String s = in.readAll();
		ObjectMapper mapper = new ObjectMapper();
		try {
			Province[] provinces = mapper.readValue(s, Province[].class);
			for (Province province : provinces) {
				System.out.println(mapper.writeValueAsString(province));
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	@Test
	void test1(){
		URL resource = this.getClass().getResource("");
		assert resource != null;
		String url = "";
		try {
			url = URLDecoder.decode(resource.getPath(),"utf-8");
			url = url.substring(1,url.indexOf("Epidemic")+"Epidemic".length());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(url);
	}

	@Test
	@DisplayName("验证toUser")
	void test2() throws JsonProcessingException {
		String userMessage = "{\"name\": \"qzlzzz\",\"profile\": \"大家好我是邱泽林\"}";
	}

	@Test
	void test3() throws JsonProcessingException {
		List<CommentType> list = new ArrayList<>();
		CommentType commentType1 = new CommentType();
		commentType1.setTypeNo(1);
		commentType1.setTypeName("你好");
		CommentType commentType = new CommentType();
		commentType.setTypeNo(2);
		commentType.setTypeName("这是测试");
		list.add(commentType1);
		list.add(commentType);
		List<Object> list1 = list.stream().map(Object.class :: cast).collect(Collectors.toList());
		Result result = new Result(Status.SUCCESSFUL.getCord(),Status.SUCCESSFUL.getReasonPhrase(),list1);
		ObjectMapper mapper = new ObjectMapper();
		String s = mapper.writeValueAsString(result);
		String replace = s.replace("\\", "");
		System.out.println(replace);

	}

	@Test
	void test4() throws JsonProcessingException {
		Map<String,String> map = new HashMap<>();
		map.put("qzl","123456");
		map.put("qzlzzz","123456");
		ObjectMapper mapper = new ObjectMapper();
		String s = mapper.writeValueAsString(map);
		System.out.println(s);
	}

	@Test
	void test5(){
		String res = Status.SUCCESSFUL.getReasonPhrase();
		String replace = res.replace("\\", "");
		System.out.println(replace);
	}
}
