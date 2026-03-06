package com.vcube.vsmapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import com.vcube.vsmapp.model.ServiceBooking;

public interface BookingService {

	Page<ServiceBooking> getBookingsPaginated(int pageNo, int pageSize);

	Page<ServiceBooking> searchBookings(String vehicleNumber, int pageNo, int pageSize);

	Page<ServiceBooking> filterBookings(String status, int pageNo, int pageSize);

	Page<ServiceBooking> searchAndFilter(String vehicleNumber, String status, int pageNo, int pageSize);

	List<ServiceBooking> getAllBookings();

	ServiceBooking saveBooking(ServiceBooking booking);

	ServiceBooking getBookingById(int id);

	void deleteBooking(int id);

}