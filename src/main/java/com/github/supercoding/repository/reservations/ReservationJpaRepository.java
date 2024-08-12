package com.github.supercoding.repository.reservations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Integer> {

    @Query("SELECT new com.github.supercoding.repository.reservations.FlightPriceAndCharge(f.flightPrice, f.charge)" +
            " FROM Reservation r" +
            " JOIN r.passenger p" +
            " JOIN r.airlineTicket a" +
            " JOIN a.flightList f" +
            " WHERE p.user.userId = :userId ")  // 파라미터 userId와 매핑
    List<FlightPriceAndCharge> findFlightPriceAndCharge(Integer userId);
}
