package com.nutrymaco.jobsite.service.city;

import com.nutrymaco.jobsite.entity.City;
import com.nutrymaco.jobsite.exception.found.CityNotFoundException;

import java.util.List;

public interface CityService {
    String getNameById(Integer id) throws CityNotFoundException;
    List<City> getAllById(List<Integer> idList);
}
