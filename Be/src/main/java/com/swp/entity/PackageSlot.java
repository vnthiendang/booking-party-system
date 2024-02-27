package com.swp.entity;

import com.swp.entity.enums.ESlotStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "package_slot")
public class PackageSlot {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    private Package packages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timeSlot_id")
    private TimeSlot timeSlot;

    @Column(name = "booking_time")
    private Date bookingTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "slot_status")
    private ESlotStatus status;
}
