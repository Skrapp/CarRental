package com.nilsson.carrental.control;

import com.nilsson.carrental.model.Booking;
import com.nilsson.carrental.model.Customer;
import com.nilsson.carrental.model.Vehicle;
import com.nilsson.carrental.repository.BookingRepo;
import com.nilsson.carrental.repository.CustomerRepo;
import com.nilsson.carrental.repository.VehicleRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class BookingController {
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private VehicleRepo vehicleRepo;
    @Autowired
    private CustomerRepo customerRepo;

    private static final Logger logger = Logger.getLogger(BookingController.class);

    @GetMapping("/")
    public ModelAndView getHome(){
        return new ModelAndView("home");
    }

    @GetMapping("/admin")
    public ModelAndView getHomeAdmin() {
        return new ModelAndView("home-admin");
    }

    //TODO Make it more "standard"
    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @GetMapping("/orders/customer-id")
    public ModelAndView getAllBookingsByCustomersId(){
        ModelAndView modelAndView  = new ModelAndView("list-bookings");
        List<Booking> bookings = bookingRepo.findBookingByCustomerId(customerRepo.findCustomerByUsername(getCurrentUsername()).getCustomerId());
        List<Vehicle> vehicles = vehicleRepo.findAll();
        List<Customer> customers = customerRepo.findAll();
        modelAndView.addObject("bookings", bookings);
        modelAndView.addObject("vehicles", vehicles);
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
    @GetMapping("/bookingform")
    public ModelAndView addBooking(){
        ModelAndView modelAndView = new ModelAndView("add-booking");
        Booking newBooking = new Booking();
        List<Vehicle> vehicles = vehicleRepo.findAll();
        Customer currentCustomer = customerRepo.findCustomerByUsername(getCurrentUsername());
        modelAndView.addObject("vehicles", vehicles);
        modelAndView.addObject("booking", newBooking);
        modelAndView.addObject("currentCustomer", currentCustomer);
        return modelAndView;
    }

    //TODO Be able to choose vehicle from list
   /* @GetMapping("/bookingform/car")
    public ModelAndView addBookingByVehicle(@RequestParam Long vehicleId){
        ModelAndView modelAndView = new ModelAndView("add-booking");
        Booking newBooking = new Booking();
        List<Vehicle> vehicles = vehicleRepo.findAll();
        modelAndView.addObject("vehicles", vehicles);
        modelAndView.addObject("vehicleId", vehicleId);
        modelAndView.addObject("booking", newBooking);
        return modelAndView;
    }*/

    @PostMapping("/save-booking")
    public String saveBooking(@ModelAttribute Booking booking){
        try {
            bookingRepo.save(booking);
            logger.info("User " + getCurrentUsername() + " added booking with booking id " + booking.getBookingId());
        }catch (Exception e){
            logger.fatal("Could not save booking by user " + getCurrentUsername(), e);
            //TODO add warn message for user
        }

        return "redirect:/orders/customer-id";
    }

    @GetMapping("/update/booking-id")
    public ModelAndView updateBooking(@RequestParam Long bookingId){
        //TODO if date has passed booking should not be updatable
        ModelAndView modelAndView = new ModelAndView("add-booking");
        Booking booking = bookingRepo.findById(bookingId).get();
        List<Vehicle> vehicles = vehicleRepo.findAll();
        Customer currentCustomer = customerRepo.findCustomerByUsername(getCurrentUsername());
        modelAndView.addObject("vehicles", vehicles);
        modelAndView.addObject("currentCustomer", currentCustomer);
        modelAndView.addObject(booking);
        return modelAndView;
    }

    @GetMapping("/delete-booking")
    public String deleteBooking(@RequestParam Long bookingId){
        //TODO if date has passed booking should not be deletable
        bookingRepo.deleteById(bookingId);
        logger.info("Booking with id " + bookingId + " has been deleted");
        return "redirect:/list-bookings";
    }
}
