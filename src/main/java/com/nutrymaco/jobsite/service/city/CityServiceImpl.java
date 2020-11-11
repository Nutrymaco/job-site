package com.nutrymaco.jobsite.service.city;

import com.nutrymaco.jobsite.entity.City;
import com.nutrymaco.jobsite.exception.found.CityNotFoundException;
import com.nutrymaco.jobsite.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Override
    public String getNameById(Integer id) throws CityNotFoundException {
        return cityRepository.findById(id)
                .map(City::getName)
                .orElseThrow(CityNotFoundException::new);
    }

    @Override
    public List<City> getAllById(List<Integer> idList) {
        return cityRepository.findAllByIdIn(idList);
    }
}
