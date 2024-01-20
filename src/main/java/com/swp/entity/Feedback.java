package com.swp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Getter
@Setter
public class Feedback {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId;

    @Column(name = "feedback_detail")
    private String feedbackDetail;

    @Column(name = "created_date")
    private LocalDateTime createdTime;

    @Column(name = "status")
    private String status;

    @OneToOne
    @JoinColumn(name = "bookingId")
    private Booking booking;
}
