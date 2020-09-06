package kr.pe.ryudaewan.stiletto;

public class StilettoException extends RuntimeException {


    public StilettoException() {
        super();
    }

    public StilettoException(String message) {
        super(message);
    }

    public StilettoException(String message, Throwable cause) {
        super(message, cause);
    }

    public StilettoException(Throwable cause) {
        super(cause);
    }

    protected StilettoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
