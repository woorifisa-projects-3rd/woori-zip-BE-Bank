package fisa.woorizip.bank.member.dto;

import fisa.woorizip.bank.spendinghistory.SpendingHistory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SpendingHistoryResponse {

    private double culture;
    private double book;
    private double grocery;
    private double cloth;
    private double food;
    private double car;

    public static SpendingHistoryResponse from(SpendingHistory spendingHistory) {
        return SpendingHistoryResponse.builder()
                .culture(spendingHistory.getCULTURE_AM())
                .book(spendingHistory.getBOOK_AM())
                .grocery(spendingHistory.getGROCERY_AM())
                .cloth(spendingHistory.getCLOTH_AM())
                .food(spendingHistory.getRESTRNT_AM())
                .car(spendingHistory.getAUTO_AM())
                .build();
    }

}
