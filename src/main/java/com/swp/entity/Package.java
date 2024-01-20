package com.swp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "package")
@Getter
@Setter
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer packageId;

    @Column(name = "packageName")
    private String packageName;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "timeSlot")
    private LocalDateTime timeSlot;

    @Column(name = "packageImage")
    private String packageImage;

    @Column(name = "venue")
    private String venue;

    @OneToMany(mappedBy = "packageId")
    private List<Booking> bookings = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "serviceId")
    private Service service;
}
