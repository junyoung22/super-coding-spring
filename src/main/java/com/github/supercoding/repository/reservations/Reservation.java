package com.github.supercoding.repository.reservations;

import com.github.supercoding.repository.airlineTicket.AirlineTicket;
import com.github.supercoding.repository.passenger.Passenger;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Integer reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline_ticket_id")
    private AirlineTicket airlineTicket;

    @Column(name = "reservation_status", length = 10)
    private String reservationStatus;
    @Column(name = "reserve_at")    // datetime + foreignkey + primarykey + auto increment
    private LocalDateTime reserveAt;



    public Reservation(Passenger passenger, AirlineTicket airlineTicket) {
        this.passenger = passenger;
        this.airlineTicket = airlineTicket;
        this.reservationStatus = "대기";  // 기본값 - 대기로 설정
        this.reserveAt = LocalDateTime.now();   // 만든시점: 지금
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Reservation reservation = (Reservation) o;
        return reservationId != null && Objects.equals(reservationId, reservation.reservationId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
