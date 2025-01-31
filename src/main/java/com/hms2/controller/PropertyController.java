package com.hms2.controller;

// import com.hms2.Repository.CityRepository;
import com.hms2.payload.PropertyDto;
import com.hms2.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    @Autowired private PropertyService propertyService;
    // @Autowired private CityRepository cityRepository;

    @PostMapping("/createProperty")
    public ResponseEntity<PropertyDto> createProperty(@RequestBody PropertyDto dto){
        PropertyDto propertyDto = propertyService.saveProperty(dto);
        return new ResponseEntity<>(propertyDto, HttpStatus.OK);
    }

    @PostMapping("/updateProperty")
    public ResponseEntity<?> updateProperty(@RequestParam long id, @RequestBody PropertyDto dto){
        PropertyDto propertyDto = propertyService.updateProperty(id, dto);
        return new ResponseEntity<>(propertyDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PropertyDto>> getAllProperty(){
        List<PropertyDto> allProperty = propertyService.getAllProperty();
        return new ResponseEntity<>(allProperty, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProperty(@PathVariable long id){
        propertyService.deleteProperty(id);
        return new ResponseEntity<>("Property with ID"  + id + "deleted successfully.", HttpStatus.OK);
    }


}
