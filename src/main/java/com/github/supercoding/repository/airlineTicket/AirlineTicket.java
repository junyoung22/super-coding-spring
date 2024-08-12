package com.github.supercoding.repository.airlineTicket;

import com.github.supercoding.repository.flight.Flight;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "airline_ticket")
public class AirlineTicket {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Integer ticketId;
    @Column(name = "ticket_type", length = 5, columnDefinition = "CHECK")
    private String ticketType;
    @Column(name = "departure_loc", length = 20)
    private String departureLocation;
    @Column(name = "arrival_loc", length = 20)
    private String arrivalLocation;
    @Column(name = "departure_at", nullable = false)  // dateTime
    private LocalDateTime departureAt;
    @Column(name = "return_at", nullable = false)    // dateTime
    private LocalDateTime returnAt;
    @Column(name = "tax")   // double
    private Double tax;
    @Column(name = "total_price")   // double
    private Double totalPrice;

    @OneToMany(mappedBy = "airlineTicket", fetch = FetchType.LAZY)
    private List<Flight> flightList;



    public AirlineTicket(Integer ticketId, String ticketType, String departureLocation, String arrivalLocation, Date departureAt, Date returnAt, Double tax, Double total_price) {
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureAt = departureAt.toLocalDate().atStartOfDay();
        this.returnAt = returnAt.toLocalDate().atStartOfDay();
        this.tax = tax;
        this.totalPrice = total_price;
    }


}

