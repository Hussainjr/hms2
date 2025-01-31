package com.hms2.service;

import com.hms2.Entity.Country;
import com.hms2.Repository.CountryRepository;
import com.hms2.payload.CountryDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService implements CountryServiceInterface{

    @Autowired private ModelMapper modelMapper;
    @Autowired private CountryRepository countryRepository;


    @Override
    public CountryDto saveCountry(CountryDto dto) {
        Country country = modelMapper.map(dto, Country.class);

        Optional<Country> byName = countryRepository.findByName(dto.getName());
        if(byName.isPresent()){
            throw new IllegalStateException("country with the name "+dto.getName()+" already exits");
        }

        Country saved = countryRepository.save(country);
        CountryDto countryDto = modelMapper.map(saved, CountryDto.class);
        return countryDto;
    }

    @Override
    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
    }

    @Override
    public CountryDto updateCountry(long id, CountryDto dto) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("no such country found by id: " + id));
        if(country.getName().equals(dto.getName())){
            throw new IllegalStateException("nothing to update");
        }
        country.setName(dto.getName());

        Country saved = countryRepository.save(country);
        return modelMapper.map(saved, CountryDto.class);
    }

    @Override
    public List<CountryDto> getAllCountry() {
        List<Country> all = countryRepository.findAll();
        List<CountryDto> countryDtoList = all.stream().map(i -> modelMapper.map(i, CountryDto.class)).toList();
        return countryDtoList;
    }
}
