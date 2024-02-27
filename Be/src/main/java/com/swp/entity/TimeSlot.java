package com.swp.entity;

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
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "start_time")
    private Date start;
    @Column(name = "end_time")
    private Date end;

    @OneToMany(mappedBy = "timeSlot", cascade = CascadeType.ALL)
    private List<PackageSlot> packageSlots = new ArrayList<>();

}
