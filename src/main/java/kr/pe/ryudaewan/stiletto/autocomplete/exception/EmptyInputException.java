package kr.pe.ryudaewan.stiletto.autocomplete.exception;

import kr.pe.ryudaewan.stiletto.BusinessException;

/**
 * Created by ryudaewan on 2017-05-26.
 */
public class EmptyInputException extends BusinessException {
    public EmptyInputException(String errCd, String msg) {
        super(errCd, msg);
    }

    public EmptyInputException(String errCd, String msg, Throwable throwable) {
        super(errCd, msg, throwable);
    }
}
