package com.worldoffice.worldofficeapi.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author James Martinez
 */
@Getter
@AllArgsConstructor
@Builder
public class InvalidOrderException extends RuntimeException {

    private int items;
    private String customError;

}


