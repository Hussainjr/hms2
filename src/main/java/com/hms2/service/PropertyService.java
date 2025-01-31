package com.hms2.service;

import com.hms2.Entity.City;
import com.hms2.Entity.Country;
import com.hms2.Entity.Property;
import com.hms2.ExceptionConfig.DuplicatePropertyException;
import com.hms2.ExceptionConfig.ResourceNotFoundException;
import com.hms2.Repository.CityRepository;
import com.hms2.Repository.CountryRepository;
import com.hms2.Repository.PropertyRepository;
import com.hms2.payload.PropertyDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService implements PropertyServiceInterface{

    @Autowired private PropertyRepository propertyRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private CityRepository cityRepository;
    @Autowired private CountryRepository countryRepository;

    @Override
    public PropertyDto saveProperty(PropertyDto dto) {
        if (dto.getName() != null && dto.getCity() != null && dto.getCountry() != null) {
            // Check if the property already exists in the database
            Optional<Property> existingProperty = propertyRepository.findByNameAndCityIdAndCountryId(
                    dto.getName(), dto.getCity().getId(), dto.getCountry().getId());
            if(existingProperty.isPresent()){
                throw new DuplicatePropertyException("Property with the same name, city, and country already exists.");
            }else {
                Property property = modelMapper.map(dto, Property.class);

                property.setCity(cityRepository.findById(dto.getCity().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("City not found")));

                property.setCountry(countryRepository.findById(dto.getCountry().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Country not found")));
                Property saved = propertyRepository.save(property);
                PropertyDto propertyDto = modelMapper.map(saved, PropertyDto.class);
                return propertyDto;
            }
        }else {
            throw new IllegalStateException("Name, city, and country are required fields.");
        }
    }

    @Override
    public PropertyDto updateProperty(long id, PropertyDto dto) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("No property found with id: " + id));

        Country country = countryRepository.findById(dto.getCountry().getId())
                .orElseThrow(() -> new IllegalStateException("no country found with id: " + dto.getCountry().getId()));

        City city = cityRepository.findById(dto.getCity().getId())
                .orElseThrow(() -> new IllegalStateException("no city found with id: " + dto.getCity().getId()));

        property.setCity(city);
        property.setCountry(country);
        property.setName(dto.getName());
        property.setNoOfGuest(dto.getNoOfGuest());
        property.setNoOfBathroom(dto.getNoOfBathroom());
        property.setNoOfBedroom(dto.getNoOfBedroom());
        Property saved = propertyRepository.save(property);
        return modelMapper.map(saved, PropertyDto.class);
    }

    @Override
    public List<PropertyDto> getAllProperty() {
        List<Property> all = propertyRepository.findAll();
        List<PropertyDto> propertyDtos = all.stream().map((element) -> modelMapper.map(element, PropertyDto.class)).toList();
        return propertyDtos;
    }

    @Override
    public void deleteProperty(long id) {
        propertyRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException("no such property with id: "+id));
        propertyRepository.deleteById(id);
    }

}
