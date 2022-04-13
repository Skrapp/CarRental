package com.nilsson.carrental.control;

import com.nilsson.carrental.model.Customer;
import com.nilsson.carrental.repository.CustomerRepo;
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

    @GetMapping("/delete-customer")
    public String deleteCustomer(@RequestParam Long customerId){
        customerRepo.deleteById(customerId);
        return "redirect:/admin/customers";
    }
}
