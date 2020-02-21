package com.github.rogeryk.charity.server.db.repository;

import com.github.rogeryk.charity.server.db.domain.Transaction;
import com.github.rogeryk.charity.server.db.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    int countAllByPayer_IdEqualsAndType(Long payerId, Transaction.TransactionType t);

    int countAllByPayer(User payer);

    int countAllByPayee(User payee);

    Optional<Transaction> findByUniqueId(Long uniqueId);


    @Query(value = "select sum(money) from transaction where payer = ?1", nativeQuery = true)
    int sumDonatedMoney(Long payerId);

    @Query(value = "select date_format(created_time, '%Y-%m-%d') as `date`, count(*) as `count` from `transaction` where created_time >= :startTime and created_time < :endTime group by date_format(created_time, '%Y-%m-%d')", nativeQuery = true)
    List<Map<String, Object>> scanCountData(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    Page<Transaction> findAllByPayer_IdAndType(Long userId, Transaction.TransactionType t, Pageable pageable);

}
