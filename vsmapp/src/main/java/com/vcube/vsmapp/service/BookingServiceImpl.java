package com.vcube.vsmapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vcube.vsmapp.model.ServiceBooking;
import com.vcube.vsmapp.repo.ServiceBookingRepository;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private ServiceBookingRepository repo;

	@Override
	public Page<ServiceBooking> getBookingsPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("bookingId").descending());
		return repo.findAll(pageable);
	}

	@Override
	public Page<ServiceBooking> searchBookings(String vehicleNumber, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("bookingId").descending());
		return repo.findByVehicleNumberContaining(vehicleNumber, pageable);
	}

	@Override
	public Page<ServiceBooking> filterBookings(String status, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("bookingId").descending());
		return repo.findByStatus(status, pageable);
	}

	@Override
	public Page<ServiceBooking> searchAndFilter(String vehicleNumber, String status, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("bookingId").descending());
		return repo.findByVehicleNumberContainingAndStatus(vehicleNumber, status, pageable);
	}

	@Override
	public List<ServiceBooking> getAllBookings() {
		return repo.findAll();
	}

	@Override
	public ServiceBooking saveBooking(ServiceBooking booking) {
		return repo.save(booking);
	}

	@Override
	public ServiceBooking getBookingById(int id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public void deleteBooking(int id) {
		repo.deleteById(id);
	}

}