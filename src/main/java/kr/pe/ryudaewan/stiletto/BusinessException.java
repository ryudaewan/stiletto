package kr.pe.ryudaewan.stiletto;

import java.util.Objects;

/**
 * Created by ryudaewan on 2017-05-26.
 */
public class BusinessException extends RuntimeException {
    private String errCd;

    public BusinessException(String errCd) {
        super();
        this.errCd = errCd;
    }

    public BusinessException(String errCd, String msg) {
        super(msg);
        this.errCd = errCd;
    }

    public BusinessException(String errCd, String msg, Throwable cause) {
        super(msg, cause);
        this.errCd = errCd;
    }

    public String getErrCd() {
        return errCd;
    }

    public void setErrCd(String errCd) {
        this.errCd = errCd;
    }

    @Override
    public String toString() {
        return "{\"errCd\": \"" + errCd + "\", \"message\": \"" + this.getLocalizedMessage() + "\"}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessException that = (BusinessException) o;
        return Objects.equals(errCd, that.errCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errCd);
    }
}
