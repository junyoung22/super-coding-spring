/*package com.github.supercoding.repository.reservations;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;

@Repository
public class ReservationJdbcTemplateDao implements ReservationRepository {

    private JdbcTemplate jdbctemplate;

    public ReservationJdbcTemplateDao(@Qualifier("jdbcTemplate2") JdbcTemplate jdbcTemplate) {
        this.jdbctemplate = jdbcTemplate;
    }

    @Override
    public Boolean saveReservation(Reservation reservation) {
        try {
            Integer rowNums = jdbctemplate.update("INSERT INTO reservation(passenger_id, airline_ticket_id, reservation_status, reserve_at) VALUES (? ,? , ?, ? )",
                    reservation.getPassengerId(), reservation.getAirlineTicketId(), reservation.getReservationStatus(),
                    new Date(Timestamp.valueOf(reservation.getReserveAt()).getTime()));
            return rowNums > 0; // 0이상이면 true
        } catch (Exception e){
            throw new RuntimeException();
        }

    }
}
*/