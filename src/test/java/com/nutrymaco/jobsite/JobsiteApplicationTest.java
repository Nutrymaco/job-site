package com.nutrymaco.jobsite;

import com.nutrymaco.jobsite.repository.vacancy.VacancyRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JobsiteApplicationTest {

	@Autowired
	VacancyRepository vacancyRepository;

	@Test
	void testTest() {
		assertNotNull(vacancyRepository);
	}

}
