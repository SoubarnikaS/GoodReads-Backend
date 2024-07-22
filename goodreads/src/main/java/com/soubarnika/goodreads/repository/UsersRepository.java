package com.soubarnika.goodreads.repository;

import com.soubarnika.goodreads.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UsersRepository extends JpaRepository<Users, Long> {
    public Users findByEmail(String email);

}
