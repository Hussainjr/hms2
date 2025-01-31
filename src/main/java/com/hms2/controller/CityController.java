package com.hms2.controller;

import com.hms2.payload.CityDto;
import com.hms2.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cityCont")
public class CityController {

    @Autowired private CityService cityService;

    @PostMapping("/createCity")
    public ResponseEntity<?> createCity(@RequestBody CityDto dto){
        CityDto cityDto = cityService.saveCity(dto);
        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCity(@RequestParam Long id){
        cityService.deleteCity(id);
        return new ResponseEntity<>("city deleted" , HttpStatus.OK);
    }

    @PostMapping("/updateCity")
    public ResponseEntity<CityDto> updateCity(@RequestParam Long id, @RequestBody CityDto dto){
        CityDto cityDto = cityService.updateCity(id, dto);
        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

    @GetMapping("/allCity")
    public ResponseEntity<List<CityDto>> getAllCity(){
        List<CityDto> allCity = cityService.getAllCity();
        return new ResponseEntity<>(allCity, HttpStatus.OK);
    }


}
