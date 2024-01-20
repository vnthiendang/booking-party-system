package com.swp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "booking_service")
@Getter
@Setter
public class BookingService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "bookingId")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "serviceId")
    private Service service;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "size")
    private String size;
}
