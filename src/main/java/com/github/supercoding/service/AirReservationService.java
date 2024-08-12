package com.github.supercoding.service;

import com.github.supercoding.repository.airlineTicket.AirlineTicket;
import com.github.supercoding.repository.airlineTicket.AirlineTicketJpaRepository;
import com.github.supercoding.repository.flight.Flight;
import com.github.supercoding.repository.passenger.Passenger;
import com.github.supercoding.repository.passenger.PassengerJpaRepository;
import com.github.supercoding.repository.reservations.FlightPriceAndCharge;
import com.github.supercoding.repository.reservations.Reservation;
import com.github.supercoding.repository.reservations.ReservationJpaRepository;
import com.github.supercoding.repository.users.UserEntity;
import com.github.supercoding.repository.users.UserJpaRepository;
import com.github.supercoding.service.exceptions.InvalidValueException;
import com.github.supercoding.service.exceptions.NotAcceptException;
import com.github.supercoding.service.exceptions.NotFoundException;
import com.github.supercoding.service.mapper.TicketMapper;
import com.github.supercoding.web.dto.airline.ReservationRequest;
import com.github.supercoding.web.dto.airline.ReservationResult;
import com.github.supercoding.web.dto.airline.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirReservationService {

    private final AirlineTicketJpaRepository airlineTicketJpaRepository;
    private final ReservationJpaRepository reservationJpaRepository;
    // private final AirlineTicketRepository airlineTicketRepository;
    private final PassengerJpaRepository passengerJpaRepository;
    private final UserJpaRepository userJpaRepository;


    public List<Ticket> findUserFavoritePlaceTickets(Integer userId, String ticketType) {
        // 필요한 Repository: UserRepository, airlineTicket Repository
        // 1. 유저를 userId로 가져와서, 선호하는 여행지도출
        // 2, 선호하는 여행지와 ticketType으로 AirlineTicket table 질의해서 필요한 AirlineTicket 들고오기
        // 3. 이 둘의 정보를 조합해서 Ticket DTO를 만든다.

        // 왕복,편도
        Set<String> ticketTypeSet = new HashSet<>(Arrays.asList("편도", "왕복"));

        if (!ticketTypeSet.contains(ticketType))
            throw new InvalidValueException("해당 TicketType " + ticketType + "은 지원하지 않습니다.");
        UserEntity userEntity = userJpaRepository.findById(userId).orElseThrow(() -> new NotFoundException("해당 ID: " + userId + "유저를 찾을수 없습니다.")); // 유저를가져와서
        String likePlace = userEntity.getLikeTravelPlace(); // 선호하는 여행지 도출
        // 선호하는 여행지와 ticketType으로 AirlineTicket table로 필요한 AirlineTicket 들고오기
        List<AirlineTicket> airlineTickets
                = airlineTicketJpaRepository.findAirlineTicketsByArrivalLocationAndTicketType(likePlace, ticketType);

        if (airlineTickets.isEmpty())
            throw new NotFoundException("해당 likePlace: " + likePlace + "와 TicketType" + ticketType + "에 해당하는 항공권을 찾을수 없습니다.");
        // 최종값은 TicketDto를 만들어야한다.
        List<Ticket> tickets = airlineTickets.stream().map(TicketMapper.INSTANCE::airlineTicketToTicket).collect(Collectors.toList());
        return tickets;
    }

    @Transactional(transactionManager = "tmJpa2")  // save가 있기에 있으면 좋을거같다.
    public ReservationResult makeReservation(ReservationRequest reservationRequest) {
        // - 어떤 Repository
        // Reservation Repository필요(예약위해 생성), JOIN(filght/< airline_ticket) AirlineTicket에서 진행, passenger Repository(예약자ID위해 생성)

        // 1.reservation = (reservation_id, Passenger_id, airline_ticket_id)
        // 1-1.ReservationRequest에서 user_id, airlone_ticket_id 얻을수있음
        Integer userId = reservationRequest.getUserId();
        Integer airlineTicketId = reservationRequest.getAirlineTicketId();

        AirlineTicket airlineTicket = airlineTicketJpaRepository.findById(airlineTicketId).orElseThrow(() -> new NotFoundException("airLineTicket 찾을수 없습니다."));
        // 1-2.Passenger_id 얻기(Passenger = 테이블컬럼 그대로 생성하자/)
        Passenger passenger = passengerJpaRepository.findPassengerByUserUserId(userId)
                .orElseThrow(() -> new NotFoundException("요청하신 userId " + userId + "에 해당하는 Passenger를 찾을수 없습니다."));

        // 2.price 등의 정보 airlineTicket으로 불러오기 ( // ticket_id를 통해 가격관련 여러정보 select )
        // ** AirlineTicket에서 Flight를 호출하면 JOIN문이다 **
        List<Flight> flightList = airlineTicket.getFlightList();

        if (flightList.isEmpty())
            throw new NotFoundException("AirlineTicket id " + airlineTicketId + "에 해당하는 항공편과 항공권을 찾을수 없습니다.");

        Boolean isSuccess = false;

        // 3. reservation 생성 , passenger,airline으로 reservation만들고 INSERT해본뒤 성공여부(Boolean)파악후 dto만들기 ↓
        Reservation reservation = new Reservation(passenger, airlineTicket);
        try {
            reservationJpaRepository.save(reservation);
            isSuccess = true;
        } catch (RuntimeException e){
            throw new NotAcceptException("Reservation이 등록되는 과정이 거부되었습니다.");
        }

        // TODO: ReservationResult DTO 만들기(isSuccess/AirlineTicketAndFlightInfo)
        //        (Infos)가격관련 여려정보를 리스트를 스트림으로 변환 / Info 객체에서 getPrice 메서드를 호출하여 price 값을 추출 / price 값들을 리스트로 수집합니다.
        List<Integer> prices = flightList.stream().map(Flight::getFlightPrice).map(Double::intValue).collect(Collectors.toList());
        List<Integer> charges = flightList.stream().map(Flight::getCharge).map(Double::intValue).collect(Collectors.toList());
        //  TODO:       리스트를 스트림으로 변환   /   getTax 메서드를 호출하여 tax 값을 추출 / 스트림에서 첫 번째 항목을 찾은후 / 값을 추출
        Integer tax = airlineTicket.getTax().intValue();
        Integer totalPrice = airlineTicket.getTotalPrice().intValue();

        return new ReservationResult(prices, charges, tax, totalPrice, isSuccess);
    }

    public Double findUserFlightSumPrice(Integer userId) {
        // 1. flight_price, Charge 구하기 ( 되게멈 - JPQL 사용하자 ) / 임시로 사용하는 dto같은 entity만들기
        List<FlightPriceAndCharge> flightPriceAndCharges = reservationJpaRepository.findFlightPriceAndCharge(userId);

        // 2. 모든 Flight_price와 charge 각각 합을 구하고
        Double flightSum = flightPriceAndCharges.stream().mapToDouble(FlightPriceAndCharge::getFlightPrice).sum();
        Double chargeSum = flightPriceAndCharges.stream().mapToDouble(FlightPriceAndCharge::getCharge).sum();

        // 3. 두개의 합을 다시 더하고 Return
        return flightSum + chargeSum;
    }
}
