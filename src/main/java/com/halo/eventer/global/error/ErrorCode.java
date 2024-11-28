package com.halo.eventer.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {


    // Common Error
    INTERNAL_SERVER_ERROR( "C001", "Server Error"),
    INVALID_INPUT_VALUE("C002", "Invalid Input Value"),
    METHOD_NOT_ALLOWED("C003", "Invalid HTTP Method"),
    ELEMENT_NOT_FOUND( "C004", "Element Not Found"),
    ELEMENT_DUPLICATED("C005", "Element Duplicated"),
    UNACCEPTABLE_EXTENSION("C007", "Unacceptable Extension"),

    // DownWidget Error
    PERMIT_THREE_ELEMENT("DW001", "Only Permit Three Element"),

    // Vote Error
    ALREADY_LIKE("C006", "Already Like Element"),;
    ;

    private final String code;
    private final String message;

    ErrorCode(final String code, final String message) {
        this.message = message;
        this.code = code;
    }


}