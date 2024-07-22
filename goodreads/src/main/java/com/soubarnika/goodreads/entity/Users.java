package com.soubarnika.goodreads.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class Users {
    @Id //Must have: Primary key annotation
    @GeneratedValue(strategy= GenerationType.AUTO) //Makes spring auto generate your id value, no need to provide value while creating object
    private Long id;

    private String name;

    @Column(unique=true)
    private String email;

    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Review> reviews;


}
