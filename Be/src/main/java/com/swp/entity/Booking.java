package com.swp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
    @JoinColumn(nullable = true)
    private Package packageId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    @CreationTimestamp
    private LocalDateTime bookingDate;

    @Column(unique = true, nullable = false)
    private String confirmNumber;


    @Column(nullable = false)
    private Double totalCost;

    @OneToMany(mappedBy = "booking")
    private List<BookedService> bookingServices = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.confirmNumber = UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + bookingId +
                ", confirmationNumber='" + confirmNumber + '\'' +
                ", bookingDate=" + bookingDate +
                ", customer=" + customer +
                ", package=" + packageId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(packageId, booking.bookingId) && Objects.equals(confirmNumber, booking.confirmNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, confirmNumber);
    }
}
