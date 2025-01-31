package com.hms2.service;

import com.hms2.payload.CountryDto;

import java.util.List;

public interface CountryServiceInterface {

    public CountryDto saveCountry(CountryDto dto);

    public void deleteCountry(Long id);

    public CountryDto updateCountry(long id, CountryDto dto);

    public List<CountryDto> getAllCountry();

}
