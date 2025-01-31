package com.hms2.service;

import com.hms2.Entity.City;
import com.hms2.Repository.CityRepository;
import com.hms2.payload.CityDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService implements CityServiceInterface{

    @Autowired private CityRepository cityRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public CityDto saveCity(CityDto dto) {
        City city = modelMapper.map(dto, City.class);

        Optional<City> existingCity  = cityRepository.findByName(city.getName());
        if(existingCity.isPresent()){
            throw new IllegalStateException("City with this name already exists "+dto.getName()) ;
        }
        City save = cityRepository.save(city);
        return modelMapper.map(save, CityDto.class);
    }

    @Override
    public void deleteCity(Long id) {
        Optional<City> byId = cityRepository.findById(id);
        if(byId.isEmpty()){
            throw new IllegalStateException("no such city with id "+id+ " exits");
        }
        cityRepository.deleteById(id);
    }

    @Override
    public CityDto updateCity(Long id, CityDto dto) {
        Optional<City> byId = cityRepository.findById(id);
        if(byId.isEmpty()){
            throw new RuntimeException("city id "+id+ "not found");
        }
        City city = byId.get();
        if(city.getName().equals(dto.getName())){
            throw new IllegalStateException("no changes has been made.");
        }
        city.setName(dto.getName());
        City saved = cityRepository.save(city);
        CityDto cityDto = modelMapper.map(saved, CityDto.class);
        return cityDto;
    }

    @Override
    public List<CityDto> getAllCity() {
        List<City> all = cityRepository.findAll();
        List<CityDto> cityDtoList = all.stream().map(i -> modelMapper.map(i, CityDto.class)).toList();
        return cityDtoList;
    }
}
