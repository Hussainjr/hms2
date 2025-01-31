package com.hms2.service;

import com.hms2.payload.BookingDto;
import java.util.List;

public interface BookingServiceInterface {

    public BookingDto saveBooking(BookingDto dto);

    public BookingDto updateBooking(Long id, BookingDto dto);

    public List<BookingDto> getAllBooking();

    public void deleteBooking(Long id);
}
