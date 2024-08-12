package com.github.supercoding.repository.users;

import com.github.supercoding.repository.passenger.Passenger;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "user_name", length = 20)
    private String userName;
    @Column(name = "like_travel_place", length = 30)
    private String likeTravelPlace;
    @Column(name = "phone_num", length = 30)
    private String phoneNum;
    // Passenger로부터 양방향 Join Passenger > UserEntity
    @OneToOne(mappedBy = "user")
    private Passenger passenger;
}
