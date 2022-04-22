package com.nilsson.carrental.control;

import com.nilsson.carrental.model.Booking;
import com.nilsson.carrental.model.Vehicle;
import com.nilsson.carrental.repository.BookingRepo;
import com.nilsson.carrental.repository.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BookingController {
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private VehicleRepo vehicleRepo;

    @GetMapping("/")
    public ModelAndView home(){
        return new ModelAndView("home");
    }

    @GetMapping("/orders/customer-id")
    public ModelAndView getAllBookings(){
        ModelAndView modelAndView  = new ModelAndView("list-bookings");
        List<Booking> bookings = bookingRepo.findAll();
        modelAndView.addObject("bookings", bookings);
        return modelAndView;
    }
    @GetMapping("/bookingform")
    public ModelAndView addBooking(){
        ModelAndView modelAndView = new ModelAndView("add-booking");
        Booking newBooking = new Booking();
        List<Vehicle> vehicles = vehicleRepo.findAll();
        modelAndView.addObject("vehicles", vehicles);
        modelAndView.addObject("booking", newBooking);
        return modelAndView;
    }

    @PostMapping("/save-booking")
    public String saveBooking(@ModelAttribute Booking booking){
        bookingRepo.save(booking);
        return "redirect:/orders/customer-id";
    }

    @GetMapping("/update/booking-id")
    public ModelAndView updateBooking(@RequestParam Long bookingId){
        ModelAndView modelAndView = new ModelAndView("add-booking");
        Booking booking = bookingRepo.findById(bookingId).get();
        List<Vehicle> vehicles = vehicleRepo.findAll();
        modelAndView.addObject("vehicles", vehicles);
        modelAndView.addObject(booking);
        return modelAndView;
    }

    @GetMapping("/delete-booking")
    public String deleteBooking(@RequestParam Long bookingId){
        bookingRepo.deleteById(bookingId);
        return "redirect:/list-bookings";
    }
}
