package com.vcube.vsmapp.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.vcube.vsmapp.model.ServiceBooking;
import com.vcube.vsmapp.service.BookingService;

@Controller
public class ServiceBookingController {

	@Autowired
	private BookingService service;

	// Home Page with Pagination + Search + Filter
	@GetMapping("/")
	public String viewHomePage(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "") String vehicleNumber, @RequestParam(defaultValue = "") String status) {

		int pageSize = 5;
		Page<ServiceBooking> bookingPage;

		if (!vehicleNumber.isEmpty() && !status.isEmpty()) {
			bookingPage = service.searchAndFilter(vehicleNumber, status, page, pageSize);
		} else if (!vehicleNumber.isEmpty()) {
			bookingPage = service.searchBookings(vehicleNumber, page, pageSize);
		} else if (!status.isEmpty()) {
			bookingPage = service.filterBookings(status, page, pageSize);
		} else {
			bookingPage = service.getBookingsPaginated(page, pageSize);
		}

		model.addAttribute("listBookings", bookingPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", bookingPage.getTotalPages());
		model.addAttribute("vehicleNumber", vehicleNumber);
		model.addAttribute("status", status);

		return "index";
	}

	// Show New Booking Form
	@GetMapping("/showNewBookingForm")
	public String showNewBookingForm(Model model) {

		ServiceBooking booking = new ServiceBooking();
		booking.setBookingDate(LocalDate.now());
		booking.setStatus("Pending");

		model.addAttribute("booking", booking);
		return "new_booking";
	}

	// Save Booking
	@PostMapping("/saveBooking")
	public String saveBooking(@ModelAttribute("booking") ServiceBooking booking) {

		service.saveBooking(booking);
		return "redirect:/";
	}

	// Show Update Form
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable int id, Model model) {

		ServiceBooking booking = service.getBookingById(id);
		model.addAttribute("booking", booking);

		return "update_booking";
	}

	// Update Booking
	@PostMapping("/updateBooking")
	public String updateBooking(@ModelAttribute("booking") ServiceBooking booking) {

		service.saveBooking(booking);
		return "redirect:/";
	}

	// Delete Booking
	@GetMapping("/deleteBooking/{id}")
	public String deleteBooking(@PathVariable int id) {

		service.deleteBooking(id);
		return "redirect:/";
	}
}