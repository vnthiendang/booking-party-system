package com.swp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service")
@Getter
@Setter
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceId;

    @Column(name = "serviceName")
    private String serviceName;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "serviceImage")
    private String serviceImage;

    @OneToMany(mappedBy = "service")
    private List<Package> packages = new ArrayList<>();

    @OneToMany(mappedBy = "service")
    private List<BookingService> bookingServices = new ArrayList<>();
}
