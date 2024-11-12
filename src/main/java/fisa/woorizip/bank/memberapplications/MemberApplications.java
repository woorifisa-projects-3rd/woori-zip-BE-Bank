package fisa.woorizip.bank.memberapplications;

import fisa.woorizip.bank.application.Application;
import fisa.woorizip.bank.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberApplications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_agree", nullable = false)
    private boolean isAgree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "code", nullable = false)
    private String code;

    @Builder
    private MemberApplications(Long id, boolean isAgree, Application application, Member member, String token, String code) {
        this.id = id;
        this.isAgree = true;
        this.application = application;
        this.member = member;
        this.token = token;
        this.code = code;
    }
}
