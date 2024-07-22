package com.soubarnika.goodreads.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long reviewId;

    @Column(length = 1000)
    private String reviewContent;

    private Long rating;

    @ManyToOne
    private Users user;

    @ManyToOne
    private Books book;

}
