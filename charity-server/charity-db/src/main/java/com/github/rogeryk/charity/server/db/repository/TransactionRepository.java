package com.github.rogeryk.charity.server.db.repository;

import com.github.rogeryk.charity.server.db.domain.Transaction;
import com.github.rogeryk.charity.server.db.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    int countAllByPayer_IdEqualsAndType(Long payerId, Transaction.TransactionType t);

    int countAllByPayer(User payer);

    int countAllByPayee(User payee);

    Optional<Transaction> findByUniqueId(Long uniqueId);


    @Query(value = "select sum(money) from transaction where payer = ?1", nativeQuery = true)
    int sumDonatedMoney(Long payerId);

    Page<Transaction> findAllByPayer_IdAndType(Long userId, Transaction.TransactionType t, Pageable pageable);

}
