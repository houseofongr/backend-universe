package com.hoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class UniverseCategory {
    private final Long id;
    private final String eng;
    private final String kor;

    public UniverseCategory(Long id, String eng,  String kor) {
        this.id = id;
        this.eng = eng;
        this.kor = kor;
    }

}
