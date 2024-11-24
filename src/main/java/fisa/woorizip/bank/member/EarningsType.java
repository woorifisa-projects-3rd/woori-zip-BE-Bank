package fisa.woorizip.bank.member;

import fisa.woorizip.bank.common.exception.WooriBankException;
import lombok.Getter;

import java.util.Arrays;

import static fisa.woorizip.bank.member.MemberErrorCode.EARNINGS_TYPE_NOT_FOUND;

@Getter
public enum EarningsType {
    EARNED("근로 소득"),
    BUSINESS("사업 소득"),
    OTHER("기타 소득"),
    NO("소득 없음");

    private final String name;

    EarningsType(String name) {
        this.name = name;
    }

    public static EarningsType from(final String name) {
        return Arrays.stream(EarningsType.values())
                .filter(type -> type.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new WooriBankException(EARNINGS_TYPE_NOT_FOUND));
    }
}
