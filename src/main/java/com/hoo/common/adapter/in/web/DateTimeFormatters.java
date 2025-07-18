package com.hoo.common.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
@AllArgsConstructor
public enum DateTimeFormatters {
    ENGLISH_DATE(DateTimeFormatter.ofPattern("MMMM.dd. yyyy", Locale.ENGLISH)),
    DOT_DATE(DateTimeFormatter.ofPattern("yyyy.MM.dd."));

    private final DateTimeFormatter formatter;
}
