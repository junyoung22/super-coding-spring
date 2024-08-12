package com.github.supercoding.repository.passenger;

import java.util.Optional;

public interface PassengerRepository {

    Optional<Passenger> findPassengerByUserUserId(Integer userId);

}
