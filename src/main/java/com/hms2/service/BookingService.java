package com.hms2.service;

import com.hms2.Entity.Booking;
import com.hms2.Entity.Property;
import com.hms2.ExceptionConfig.ResourceNotFoundException;
import com.hms2.Repository.BookingRepository;
import com.hms2.Repository.PropertyRepository;
import com.hms2.payload.BookingDto;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookingService implements BookingServiceInterface {

    @Autowired private ModelMapper modelMapper;
    @Autowired private BookingRepository bookingRepository;
    @Autowired private PropertyRepository propertyRepository;

    @Transactional
    @Override
    public BookingDto saveBooking(BookingDto dto) {
        // 1. Validate the BookingDto object
        if (dto == null) {
            throw new IllegalArgumentException("BookingDto cannot be null");
        }

        if (dto.getGuestName() == null || dto.getGuestName().isEmpty()) {
            throw new IllegalArgumentException("Guest name cannot be null or empty");
        }

        if (dto.getNoOfGuest() == null || dto.getNoOfGuest() <= 0) {
            throw new IllegalArgumentException("Number of guests must be greater than 0");
        }

        if (dto.getMobile() == null || dto.getMobile().isEmpty()) {
            throw new IllegalArgumentException("Mobile number cannot be null or empty");
        }

        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        // 2. Map DTO to Entity
        Booking booking = modelMapper.map(dto, Booking.class);

        // 3. Handle the property association logic
        if (dto.getProperty() != null) {
            if (dto.getProperty().getId() != null) {
                // Case 1: Existing property - fetch from DB
                Property property = propertyRepository.findById(dto.getProperty().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Property not found with id " + dto.getProperty().getId()));
                booking.setProperty(property);
            } else {
                // Case 2: New property - save to DB
                Property property = modelMapper.map(dto.getProperty(), Property.class);
                property = propertyRepository.save(property);
                booking.setProperty(property);
            }
        }
        // 4. Save the booking
        Booking savedBooking = bookingRepository.save(booking);

        // 5. Map the saved entity back to DTO
        return modelMapper.map(savedBooking, BookingDto.class);
    }


    @Override
    public BookingDto updateBooking(Long id, BookingDto dto) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException("No such booking id " + id));
        modelMapper.map(dto, booking);
        Booking saved = bookingRepository.save(booking);
        BookingDto bookingDto = modelMapper.map(saved, BookingDto.class);
        return bookingDto;
    }

    @Override
    public List<BookingDto> getAllBooking() {
        List<Booking> allBookings = bookingRepository.findAll();
        if(allBookings == null || allBookings.isEmpty()){
            return Collections.emptyList();
        }

        List<BookingDto> bookingDtoList = allBookings.stream().map(i -> modelMapper.map(i, BookingDto.class)).toList();
        return bookingDtoList;
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Booking with the id: " + id +" not found"));
        bookingRepository.deleteById(id);
    }

}
