package com.nilsson.carrental.control;

import com.nilsson.carrental.model.Customer;
import com.nilsson.carrental.repository.CustomerRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private CustomerRepo customerRepo;

    private static final Logger logger = Logger.getLogger(CustomerController.class);

    @GetMapping("/admin/customers")
    public ModelAndView getAllCustomers(){
        ModelAndView modelAndView  = new ModelAndView("list-customers");
        List<Customer> customers = customerRepo.findAll();
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/admin/customers/customer-id")
    public ModelAndView updateCustomer(@RequestParam Long customerId){
        ModelAndView modelAndView = new ModelAndView("update-customer");
        Customer customer = customerRepo.findById(customerId).get();
        modelAndView.addObject(customer);
        return modelAndView;
    }

    @PostMapping("/save-customer")
    public String saveBooking(@ModelAttribute Customer customer){
        try {
            customerRepo.save(customer);
            logger.info("Customer updated: " + customer.getName() + ", " + customer.getCustomerId());
        }catch (Exception e){
            logger.fatal("Could not update customer", e);
            //TODO add warn message for user
        }

        return "redirect:/admin/customers";
    }

    @GetMapping("/delete-customer")
    public String deleteCustomer(@RequestParam Long customerId){
        customerRepo.deleteById(customerId);
        return "redirect:/admin/customers";
    }
}
