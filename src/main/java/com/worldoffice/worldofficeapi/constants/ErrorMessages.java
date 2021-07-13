package com.worldoffice.worldofficeapi.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessages {

    public static final String INSUFFICIENT_PRODUCTS = "There aren't enough unit of:";
    public static final String SAVE_ERROR = "Error, could not save order";
    public static final String SQL_TRANSACTION = "Error when executing the SQL transaction";
    public static final String NOT_CONTROLLED_ERROR = "Error no controlled";


}
