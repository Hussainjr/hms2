package com.hms2.controller;

import com.hms2.payload.BookingDto;
import com.hms2.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    @Autowired private BookingService bookingService;

    @PostMapping("/createBooking")
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto dto){
        BookingDto bookingDto = bookingService.saveBooking(dto);
        return new ResponseEntity<>(bookingDto, HttpStatus.CREATED);
    }

    @PostMapping("/updateBooking")
    public ResponseEntity<BookingDto> updateBooking(@RequestParam long id, @RequestBody BookingDto dto){
        BookingDto bookingDto = bookingService.updateBooking(id, dto);
        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteBooking(@RequestParam long id){
        bookingService.deleteBooking(id);
        return new ResponseEntity<>("deleted Booking.",HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllBooking(){
        List<BookingDto> allBooking = bookingService.getAllBooking();
        return new ResponseEntity<>(allBooking, HttpStatus.OK);
    }

}
