package fisa.woorizip.bank.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    @Query("select m from Member m join fetch m.spendingHistory where m.id = :memberId")
    Optional<Member> findByIdWithHistory(Long memberId);

    @Query("select m from Member m join MemberApplications ma on ma.member.id = m.id")
    Optional<Member> findByCode(String code);
}
