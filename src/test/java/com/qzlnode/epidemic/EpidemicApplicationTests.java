package com.qzlnode.epidemic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzlnode.epidemic.miniprogram.pojo.Province;
import com.qzlnode.epidemic.miniprogram.util.In;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

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
				System.out.println(province.toString());
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

}
