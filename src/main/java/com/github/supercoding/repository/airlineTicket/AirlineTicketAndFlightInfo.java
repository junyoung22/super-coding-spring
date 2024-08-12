package com.github.supercoding.repository.airlineTicket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AirlineTicketAndFlightInfo {
    private Integer ticketId;   // 티켓기준으로 해야하기때문에 필요
    private Integer price;
    private Integer charge;
    private Integer tax;
    private Integer totalPrice;

    public AirlineTicketAndFlightInfo(Integer ticketId, Double price, Double charge, Double tax, Double totalPrice) {
        this.ticketId = ticketId;
        this.price = price.intValue();
        this.charge = charge.intValue();
        this.tax = tax.intValue();
        this.totalPrice = totalPrice.intValue();
    }


}
