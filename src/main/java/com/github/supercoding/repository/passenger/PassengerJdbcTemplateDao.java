/*package com.github.supercoding.repository.passenger;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PassengerJdbcTemplateDao implements PassengerRepository {

    private JdbcTemplate jdbctemplate;

    public PassengerJdbcTemplateDao(@Qualifier("jdbcTemplate2") JdbcTemplate jdbctemplate) {
        this.jdbctemplate = jdbctemplate;
    }

    static RowMapper<Passenger> passengerRowMapper = (((rs, rowNum) ->
            new Passenger(
                    rs.getInt("passenger_id"),
                    rs.getInt("user_id"),
                    rs.getNString("passport_num")
            )
    ));

    @Override
    public Optional<Passenger> findPassengerByUserId(Integer userId) {
        try{
            return Optional.ofNullable(jdbctemplate.queryForObject("SELECT * FROM passenger WHERE user_id = ?", passengerRowMapper, userId));
        } catch (Exception e){
            return Optional.empty();
        }
    }
}
*/