package com.qzlnode.epidemic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class EpidemicApplicationTests {

	@Autowired
	private StringRedisTemplate redis;

	@Test
	void contextLoads() {

	}

}
