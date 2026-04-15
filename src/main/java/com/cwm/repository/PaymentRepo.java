package com.cwm.repository;

import com.cwm.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p " +
            "WHERE p.date >= :fromDate " +
            "AND p.date <= :toDate " +
            "ORDER BY p.date ASC")
    List<Payment> findByDateBetween(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate")   LocalDate toDate
    );

    // ─── Filter 2 — Month + Year ──────────────────────────────────────

    // ✅ Fetch payments by month enum + year int
    @Query("SELECT p FROM Payment p " +
            "WHERE p.month = :month " +
            "AND p.year = :year " +
            "ORDER BY p.date ASC")
    List<Payment> findByMonthAndYear(
            @Param("month") Month month,
            @Param("year")  int   year
    );

    // ─── Filter 3 — Year Only ─────────────────────────────────────────

    // ✅ Fetch all payments for a given year
    @Query("SELECT p FROM Payment p " +
            "WHERE p.year = :year " +
            "ORDER BY p.month ASC, p.date ASC")
    List<Payment> findByYear(
            @Param("year") int year
    );

    // ─── Aggregate Queries — used in summary sheet ────────────────────

    // ✅ Total amount for date range
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p " +
            "WHERE p.date >= :fromDate " +
            "AND p.date <= :toDate")
    BigDecimal sumAmountByDateBetween(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate")   LocalDate toDate
    );

    // ✅ Total amount for month + year
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p " +
            "WHERE p.month = :month " +
            "AND p.year = :year")
    BigDecimal sumAmountByMonthAndYear(
            @Param("month") Month month,
            @Param("year")  int   year
    );

    // ✅ Total amount for year
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p " +
            "WHERE p.year = :year")
    BigDecimal sumAmountByYear(
            @Param("year") int year
    );

    // ─── Count Queries — used in summary sheet ────────────────────────

    // ✅ Count payments for date range
    @Query("SELECT COUNT(p) FROM Payment p " +
            "WHERE p.date >= :fromDate " +
            "AND p.date <= :toDate")
    long countByDateBetween(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate")   LocalDate toDate
    );

    // ✅ Count payments for month + year
    @Query("SELECT COUNT(p) FROM Payment p " +
            "WHERE p.month = :month " +
            "AND p.year = :year")
    long countByMonthAndYear(
            @Param("month") Month month,
            @Param("year")  int   year
    );

    // ✅ Count payments for year
    @Query("SELECT COUNT(p) FROM Payment p " +
            "WHERE p.year = :year")
    long countByYear(
            @Param("year") int year
    );
}
