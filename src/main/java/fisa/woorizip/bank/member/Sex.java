package fisa.woorizip.bank.member;

import lombok.Getter;

@Getter
public enum Sex {

    MALE("1"), FEMALE("2");

    private final String value;

    Sex(String value) {
        this.value = value;
    }
}
