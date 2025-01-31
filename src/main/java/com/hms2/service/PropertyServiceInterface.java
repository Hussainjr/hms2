package com.hms2.service;

import com.hms2.payload.PropertyDto;

import java.util.List;

public interface PropertyServiceInterface {

    public PropertyDto saveProperty(PropertyDto dto);

    public PropertyDto updateProperty(long id, PropertyDto dto);

    public List<PropertyDto> getAllProperty();

    public void deleteProperty(long id);
}
