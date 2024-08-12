package com.github.supercoding.web.controller;

import com.github.supercoding.repository.userDetails.CustomUserDetails;
import com.github.supercoding.service.AirReservationService;
import com.github.supercoding.service.exceptions.InvalidValueException;
import com.github.supercoding.service.exceptions.NotAcceptException;
import com.github.supercoding.service.exceptions.NotFoundException;
import com.github.supercoding.web.dto.airline.ReservationRequest;
import com.github.supercoding.web.dto.airline.ReservationResult;
import com.github.supercoding.web.dto.airline.Ticket;
import com.github.supercoding.web.dto.airline.TicketResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/air-reservation")
@RequiredArgsConstructor
@Slf4j
public class AirReservationController {
    @Autowired
    private AirReservationService airReservationService;

    @ApiOperation("선호하는 ticket 탐색") // TicketResponse 여러사항 예외처리위해-> ResponseEntity
    @GetMapping("/tickets")     // tickets?user-id=1&airline-ticket-type=왕복
    public TicketResponse findAirlineTickets(
           @AuthenticationPrincipal CustomUserDetails customUserDetails,    // (토큰)유저아이디 있는것 두번 받을필요가 없어짐?
           @ApiParam(name = "airline-ticket-type", value = "항공권 타입", example = "왕복|편도")
           // @RequestParam("user-id")Integer userId,
           @RequestParam("airline-ticket-type") String ticketType)
    {
        Integer userId = customUserDetails.getUserId();
        List<Ticket> tickets = airReservationService.findUserFavoritePlaceTickets(userId, ticketType);
            return new TicketResponse(tickets);
    }
    @ApiOperation("User와 Ticket Id로 예약 진행")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reservations")
    public ReservationResult makeReservation(@RequestBody ReservationRequest reservationRequest) {
        ReservationResult reservationResult = airReservationService.makeReservation(reservationRequest);
        return reservationResult;
    }
    @ApiOperation("userId의 예약한 항공편과 수수료 총합")
    @GetMapping("/users-sum-price")
    public Double findUserFlightSumPrice(
            @ApiParam(name = "user-Id", value = "유저 ID", example = "1")@RequestParam("user-id") Integer userId
    )
    {
    Double sum = airReservationService.findUserFlightSumPrice(userId);
    return sum;
    }
}
