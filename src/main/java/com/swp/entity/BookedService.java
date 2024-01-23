package com.swp.entity;

import com.swp.entity.enums.ServiceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookedService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(nullable = false)
    private PService service;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceType serviceType;

    @Override
    public String toString() {
        return "BookedService{" +
                "id=" + id +
                ", booking=" + booking +
                ", type=" + serviceType +
                ", count=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookedService that = (BookedService) o;
        return Objects.equals(id, that.id) && Objects.equals(booking, that.booking);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, booking);
    }
}
