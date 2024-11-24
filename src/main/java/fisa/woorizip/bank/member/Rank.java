package fisa.woorizip.bank.member;

import lombok.Getter;

@Getter
public enum Rank {

    VVIP("21"), VIP("22"), PLATINUM("23"), GOLD("24"), NONE("25");

    private final String value;

    Rank(String value) {
        this.value = value;
    }
}
