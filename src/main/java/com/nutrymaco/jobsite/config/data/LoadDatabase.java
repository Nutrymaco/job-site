package com.nutrymaco.jobsite.config.data;

import com.nutrymaco.jobsite.dto.VacancyFilter;
import com.nutrymaco.jobsite.entity.City;
import com.nutrymaco.jobsite.entity.Country;
import com.nutrymaco.jobsite.entity.User;
import com.nutrymaco.jobsite.entity.WorkSchedule;
import com.nutrymaco.jobsite.repository.CityRepository;
import com.nutrymaco.jobsite.repository.CountryRepository;
import com.nutrymaco.jobsite.repository.UserRepository;
import com.nutrymaco.jobsite.repository.WorkScheduleRepository;
import com.nutrymaco.jobsite.service.autosearch.AutosearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class LoadDatabase {
}
