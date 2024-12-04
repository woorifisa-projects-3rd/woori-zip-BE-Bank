package fisa.woorizip.bank.memberapplications;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberApplicationsRepository extends JpaRepository<MemberApplications, Long> {

    @Query("select ma from MemberApplications ma where ma.application.clientId = :clientId and ma.member.id = :memberId")
    Optional<MemberApplications> findByMemberIdAndClientId(Long memberId, String clientId);
}
