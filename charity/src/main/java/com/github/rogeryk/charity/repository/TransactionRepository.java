package com.github.rogeryk.charity.repository;

import com.github.rogeryk.charity.domain.Transaction;
import com.github.rogeryk.charity.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    int countAllByPayer_IdEqualsAndType(Long payerId, Transaction.TransactionType t);

    int countAllByPayer(User payer);

    int countAllByPayee(User payee);


    @Query(value = "select sum(money) from transaction where payer = ?1", nativeQuery = true)
    int sumDonatedMoney(Long payerId);

    Page<Transaction> findAllByPayer_IdAndType(Long userId, Transaction.TransactionType t, Pageable pageable);

}
