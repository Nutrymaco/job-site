package com.nutrymaco.jobsite;

import com.nutrymaco.jobsite.repository.vacancy.VacancyRepository;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JobsiteApplicationTest {

	@Autowired
	VacancyRepository vacancyRepository;

	@Test
	public void testTest() {
		assertNotNull(vacancyRepository);
	}

}
