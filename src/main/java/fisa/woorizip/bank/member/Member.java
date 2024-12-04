package fisa.woorizip.bank.member;

import fisa.woorizip.bank.spendinghistory.SpendingHistory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "gender", nullable = false)
    private int gender;

    @Column(name = "member_rank", nullable = false)
    private int memberShip;

    @Enumerated(EnumType.STRING)
    @Column(name = "life_stage", nullable = false)
    private LifeStage lifeStage;

    @Column(name = "available_assets", nullable = false)
    private long availableAssets;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spending_history_id", nullable = false)
    private SpendingHistory spendingHistory;

}
