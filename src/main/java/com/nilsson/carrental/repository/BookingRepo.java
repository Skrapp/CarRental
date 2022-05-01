package com.nilsson.carrental.repository;

import com.nilsson.carrental.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {
    public List<Booking> findBookingByCustomerId(Long customerId);
}
