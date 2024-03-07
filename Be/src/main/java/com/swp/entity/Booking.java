package com.swp.entity;

import com.swp.entity.enums.EBookingStatus;
import com.swp.entity.enums.EPaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private Package packages;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @Column(name = "booking_date")
    private Date bookingDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "party_size")
    private int partySize;
    @Column(nullable = true)
    private Double totalCost;

    @Column(nullable = true)
    private Double deposited;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "status")
//    private EBookingStatus bookingStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status")
    private EBookingStatus bookingStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private EPaymentStatus paymentStatus;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<BookingPackageService> bookingPackageService = new ArrayList<>();

}
