package com.cwm.repository;

import com.cwm.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {
    @Modifying
    @Query("DELETE FROM Subscription s WHERE s.id = :id")
    int deleteByIdAndReturnCount(@Param("id") long id);

    @Query("SELECT s FROM Subscription s " +
            "JOIN FETCH s.member m " +          // ✅ avoid N+1 — fetch member in same query
            "WHERE s.date >= :fromDate " +
            "AND s.date <= :toDate " +
            "ORDER BY s.date ASC")
    List<Subscription> findByDateBetween(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate")   LocalDate toDate
    );

    // ─── Filter 2 — Month + Year ──────────────────────────────────────

    // ✅ Fetch subscriptions by month + year
    @Query("SELECT s FROM Subscription s " +
            "JOIN FETCH s.member m " +
            "WHERE s.month = :month " +
            "AND s.year = :year " +
            "ORDER BY s.date ASC")
    List<Subscription> findByMonthAndYear(
            @Param("month") Month month,
            @Param("year")  int   year
    );

    // ─── Filter 3 — Year Only ─────────────────────────────────────────

    // ✅ Fetch all subscriptions for a given year
    @Query("SELECT s FROM Subscription s " +
            "JOIN FETCH s.member m " +
            "WHERE s.year = :year " +
            "ORDER BY s.month ASC, s.date ASC")
    List<Subscription> findByYear(
            @Param("year") int year
    );

    // ─── Aggregate Queries ────────────────────────────────────────────

    // ✅ Total amount for date range
    @Query("SELECT COALESCE(SUM(s.amount), 0) FROM Subscription s " +
            "WHERE s.date >= :fromDate " +
            "AND s.date <= :toDate")
    BigDecimal sumAmountByDateBetween(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate")   LocalDate toDate
    );

    // ✅ Total amount for month + year
    @Query("SELECT COALESCE(SUM(s.amount), 0) FROM Subscription s " +
            "WHERE s.month = :month " +
            "AND s.year = :year")
    BigDecimal sumAmountByMonthAndYear(
            @Param("month") Month month,
            @Param("year")  int   year
    );

    // ✅ Total amount for year
    @Query("SELECT COALESCE(SUM(s.amount), 0) FROM Subscription s " +
            "WHERE s.year = :year")
    BigDecimal sumAmountByYear(
            @Param("year") int year
    );

    // ─── Count Queries ────────────────────────────────────────────────

    // ✅ Count subscriptions for date range
    @Query("SELECT COUNT(s) FROM Subscription s " +
            "WHERE s.date >= :fromDate " +
            "AND s.date <= :toDate")
    long countByDateBetween(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate")   LocalDate toDate
    );

    // ✅ Count subscriptions for month + year
    @Query("SELECT COUNT(s) FROM Subscription s " +
            "WHERE s.month = :month " +
            "AND s.year = :year")
    long countByMonthAndYear(
            @Param("month") Month month,
            @Param("year")  int   year
    );

    // ✅ Count subscriptions for year
    @Query("SELECT COUNT(s) FROM Subscription s " +
            "WHERE s.year = :year")
    long countByYear(
            @Param("year") int year
    );

    // ─── Status-based Queries — for summary breakdown ─────────────────

    // ✅ Count by payment status for date range
    @Query("SELECT s.paymentStatus, COUNT(s) FROM Subscription s " +
            "WHERE s.date >= :fromDate " +
            "AND s.date <= :toDate " +
            "GROUP BY s.paymentStatus")
    List<Object[]> countByStatusAndDateBetween(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );

    // ✅ Count by payment status for month + year
    @Query("SELECT s.paymentStatus, COUNT(s) FROM Subscription s " +
            "WHERE s.month = :month " +
            "AND s.year = :year " +
            "GROUP BY s.paymentStatus")
    List<Object[]> countByStatusAndMonthAndYear(
            @Param("month") Month month,
            @Param("year")  int   year
    );

    // ✅ Count by payment status for year
    @Query("SELECT s.paymentStatus, COUNT(s) FROM Subscription s " +
            "WHERE s.year = :year " +
            "GROUP BY s.paymentStatus")
    List<Object[]> countByStatusAndYear(
            @Param("year") int year
    );
}
