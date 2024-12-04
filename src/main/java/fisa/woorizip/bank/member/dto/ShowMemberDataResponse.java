package fisa.woorizip.bank.member.dto;

import fisa.woorizip.bank.member.LifeStage;
import fisa.woorizip.bank.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class ShowMemberDataResponse {

    private final String customerId;
    private final String name;
    private final int gender;
    private final LocalDate birthday;
    private final Integer creditScore;
    private final int membership;
    private final LifeStage lifeStage;
    private final long availableAssets;
    private final String email;
    private final SpendingHistoryResponse spendingHistory;

    public static ShowMemberDataResponse from(Member member) {
        return ShowMemberDataResponse.builder()
                .customerId(member.getCustomerId())
                .name(member.getName())
                .email(member.getEmail())
                .gender(member.getGender())
                .birthday(member.getBirthday())
                .membership(member.getMemberShip())
                .lifeStage(member.getLifeStage())
                .availableAssets(member.getAvailableAssets())
                .spendingHistory(SpendingHistoryResponse.from(member.getSpendingHistory()))
                .build();
    }
}
