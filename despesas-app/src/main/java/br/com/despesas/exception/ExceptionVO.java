package br.com.despesas.exception;

import lombok.Getter;

@Getter
public final class ExceptionVO {

    private final String error;

    ExceptionVO(String error) {
        this.error = error;
    }

}
