package fisa.woorizip.bank.member;

import lombok.Getter;

@Getter
public enum LifeStage {

    UNI("대학생"),
    NEW_JOB("사회초년생"),
    NEW_WED("신혼"),
    CHILD_BABY("자녀 의무교육"),
    CHILD_UNI("자녀 대학생"),
    GOLLIFE("중년기타(신혼부부 이후 or 자녀없음)"),
    SECLIFE("2nd Life"),
    RETIRE("은퇴");

    private final String description;

    LifeStage(String description) {
        this.description = description;
    }
}
