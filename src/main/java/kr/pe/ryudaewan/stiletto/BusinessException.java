package kr.pe.ryudaewan.stiletto;

/**
 * Created by ryudaewan on 2017-05-26.
 */
public class BusinessException extends RuntimeException {
    private String errCd;

    public BusinessException(String errCd, String msg) {
        super("[" + errCd + "]: " + msg);
        this.errCd = errCd;
    }

    public BusinessException(String errCd, String msg, Throwable cause) {
        super("[" + errCd + "]: " + msg, cause);
        this.errCd = errCd;
    }
}
