package com.microservice.taskservice;

import com.microservice.coreservice.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EntityScan("org.soluvas.buzz.core.jpa")
class CoreServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
