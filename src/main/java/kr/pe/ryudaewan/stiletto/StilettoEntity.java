package kr.pe.ryudaewan.stiletto;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class StilettoEntity {

    @Column(name = "created_at")
    private Timestamp createdAt;

    public StilettoEntity() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Long getCreatedAt() {
        return this.createdAt.getTime();
    }
}
