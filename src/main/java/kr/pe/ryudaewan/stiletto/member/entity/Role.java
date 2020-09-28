package kr.pe.ryudaewan.stiletto.member.entity;

public enum Role {
    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER");

    private final String value;

    Role(String val) {
        this.value = val;
    }

    public String getValue() {
        return value;
    }
}
