package kr.pe.ryudaewan.stiletto.member;

import kr.pe.ryudaewan.stiletto.StilettoException;

public class DuplicateMemberException extends StilettoException {
    private final String duplicatedEmail;

    public DuplicateMemberException(String duplicatedEmail) {
        super();
        this.duplicatedEmail = duplicatedEmail;
    }

    public DuplicateMemberException(String message, Throwable cause, String duplicatedEmail) {
        super(message, cause);
        this.duplicatedEmail = duplicatedEmail;
    }

    public DuplicateMemberException(Throwable cause, String duplicatedEmail) {
        super(cause);
        this.duplicatedEmail = duplicatedEmail;
    }

    protected DuplicateMemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String duplicatedEmail) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.duplicatedEmail = duplicatedEmail;
    }

    public String getDuplicatedEmail() {
        return duplicatedEmail;
    }
}
