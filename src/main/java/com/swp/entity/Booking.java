package com.swp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "booking")
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId;

    @ManyToOne
    @JoinColumn(name = "packageId")
    private Package packageId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "bookingDate")
    private LocalDateTime bookingDate;

    @Column(name = "reservationStatus")
    private String reservationStatus;

    @Column(name = "paymentStatus")
    private String paymentStatus;

    @Column(name = "totalCost")
    private BigDecimal totalCost;

    @OneToOne(mappedBy = "booking")
    private Feedback feedback;

    @OneToMany(mappedBy = "booking")
    private List<BookingService> bookingServices = new ArrayList<>();

}
