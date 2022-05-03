package com.nilsson.carrental.control;

import com.nilsson.carrental.model.Vehicle;
import com.nilsson.carrental.repository.VehicleRepo;
import org.apache.log4j.Logger;
import org.aspectj.weaver.patterns.BindingAnnotationFieldTypePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class VehicleController {
    @Autowired
    private VehicleRepo vehicleRepo;

    private static final Logger logger = Logger.getLogger(VehicleController.class);

    @GetMapping("/admin/vehicles")
    public ModelAndView getAllVehiclesAdmin(){
        ModelAndView modelAndView  = new ModelAndView("list-vehicles-admin");
        List<Vehicle> vehicles = vehicleRepo.findAll();
        modelAndView.addObject("vehicles", vehicles);
        return modelAndView;
    }

    @GetMapping("/cars")
    public ModelAndView getAllVehiclesUser(){
        ModelAndView modelAndView  = new ModelAndView("list-vehicles");
        List<Vehicle> vehicles = vehicleRepo.findAll();
        modelAndView.addObject("vehicles", vehicles);
        return modelAndView;
    }

    @GetMapping("/admin/add-vehicle")
    public ModelAndView addVehicle(){
        ModelAndView modelAndView = new ModelAndView("add-vehicle");
        Vehicle newVehicle = new Vehicle();
        modelAndView.addObject("vehicle", newVehicle);
        return modelAndView;
    }

    @PostMapping("/save-vehicle")
    public String saveVehicle(@ModelAttribute Vehicle vehicle){
        //TODO add check so all values are correct
        try{
            vehicleRepo.save(vehicle);
            logger.info("Vehicle saved: " + vehicle.getName() + ", " + vehicle.getVehicleId());
        }catch (Exception e){
            logger.fatal("Vehicle could not be added");
        }
        return "redirect:/admin/vehicles";
    }

    @GetMapping("/admin/vehicle/vehicle-id")
    public ModelAndView updateVehicle(@RequestParam Long vehicleId){
        ModelAndView modelAndView = new ModelAndView("add-vehicle");
        Vehicle vehicle = vehicleRepo.findById(vehicleId).get();
        modelAndView.addObject(vehicle);
        return modelAndView;
    }

    @GetMapping("/delete-vehicle")
    public String deleteCar(@RequestParam Long vehicleId){
        vehicleRepo.deleteById(vehicleId);
        logger.info("Vehicle deleted: " + vehicleId);
        return "redirect:/admin/vehicles";
    }
}
