package com.hms2.config;

import com.hms2.Entity.Booking;
import com.hms2.payload.BookingDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfig {

    @Bean
    public ModelMapper modelMapper(){

        ModelMapper modelMapper = new ModelMapper();

        // Optionally, you can configure custom mappings if needed
        modelMapper.addMappings(new PropertyMap<BookingDto, Booking>() {
            @Override
            protected void configure() {
                map(source.getProperty().getId(), destination.getProperty().getId());
                map(source.getProperty().getNoOfBathroom(), destination.getProperty().getNoOfBathroom());
                // Add other mappings for other fields if necessary
            }
        });




        return new ModelMapper();
    }

}
