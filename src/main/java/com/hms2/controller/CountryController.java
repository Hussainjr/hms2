package com.hms2.controller;

import com.hms2.payload.CountryDto;
import com.hms2.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countryCont")
public class CountryController {

    @Autowired private CountryService countryService;

    @PostMapping("/createCountry")
    public ResponseEntity<String> createCountry(@RequestBody @Valid CountryDto dto){
        countryService.saveCountry(dto);
        return new ResponseEntity<>("country is saved", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCountry(@RequestParam Long id){
        countryService.deleteCountry(id);
        return new ResponseEntity<>("country is deleted", HttpStatus.OK);
    }

    @PostMapping("/update-country")
    public ResponseEntity<CountryDto> updateCountry(@RequestParam Long id,@RequestBody CountryDto dto){
        CountryDto countryDto = countryService.updateCountry(id, dto);
        return new ResponseEntity<>(countryDto, HttpStatus.OK);
    }

    @GetMapping("/allCountry")
    public ResponseEntity<List<CountryDto>> getAllCountry(){
        List<CountryDto> allCountry = countryService.getAllCountry();
        return new ResponseEntity<>(allCountry, HttpStatus.OK);
    }

}
