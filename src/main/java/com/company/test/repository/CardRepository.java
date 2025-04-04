package com.company.test.repository;

import com.company.test.entity.Card;
import com.company.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByOwner(User owner);

    Optional<Card> findByEncryptedNumber(String encryptedNumber);
}
