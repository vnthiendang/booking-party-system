package com.swp.entity;

import com.swp.entity.enums.ServiceType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceId;

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    private Integer serviceAmount;

    @Column(nullable = true)
    private String serviceImage;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private Package packageId;

    @OneToMany(mappedBy = "service")
    private List<BookedService> bookingServices = new ArrayList<>();
}
