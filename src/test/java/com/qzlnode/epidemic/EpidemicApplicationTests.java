package com.qzlnode.epidemic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzlnode.epidemic.miniprogram.pojo.Province;
import com.qzlnode.epidemic.miniprogram.util.In;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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


}
