package com.poit.hibiscus.repository;

import com.poit.hibiscus.entity.Card;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> getAllByUserId(Long id);

    @Query(value = """
                    SELECT id FROM cards WHERE number = :number
                    """, nativeQuery = true)
    Long findCardIdByCardNumber(@Param("number") String number);
}
