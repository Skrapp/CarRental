package com.nilsson.carrental.control;

import com.nilsson.carrental.model.Booking;
import com.nilsson.carrental.model.Customer;
import com.nilsson.carrental.model.Vehicle;
import com.nilsson.carrental.repository.BookingRepo;
import com.nilsson.carrental.repository.CustomerRepo;
import com.nilsson.carrental.repository.VehicleRepo;
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

    @GetMapping("/")
    public ModelAndView home(){
        return new ModelAndView("home");
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }

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
        //TODO add dynamic for every user
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

    @GetMapping("/bookingform/car")
    public ModelAndView addBookingByVehicle(@RequestParam Long vehicleId){
        ModelAndView modelAndView = new ModelAndView("add-booking");
        Booking newBooking = new Booking();
        List<Vehicle> vehicles = vehicleRepo.findAll();
        //TODO add error check if CustomerId does not exist
        modelAndView.addObject("vehicles", vehicles);
        modelAndView.addObject("vehicleId", vehicleId);
        modelAndView.addObject("booking", newBooking);
        return modelAndView;
    }

    @GetMapping("/bookingform/customer-id")
    public ModelAndView addBookingByCustomer(@RequestParam Long customerId){
        ModelAndView modelAndView = new ModelAndView("add-booking");
        Booking newBooking = new Booking();
        List<Vehicle> vehicles = vehicleRepo.findAll();
        Customer customer = customerRepo.getById(customerId);
        //TODO add error check if CustomerId does not exist
        modelAndView.addObject("vehicles", vehicles);
        modelAndView.addObject("customers", customer);
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
        //modelAndView.addObject("customerRepo", customerRepo);
        modelAndView.addObject(booking);
        return modelAndView;
    }

    @GetMapping("/delete-booking")
    public String deleteBooking(@RequestParam Long bookingId){
        bookingRepo.deleteById(bookingId);
        return "redirect:/list-bookings";
    }
}
