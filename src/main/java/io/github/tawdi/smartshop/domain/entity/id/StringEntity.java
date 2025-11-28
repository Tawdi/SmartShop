package io.github.tawdi.smartshop.domain.entity.id;

import io.github.tawdi.smartshop.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class StringEntity extends BaseEntity<String> {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "CHAR(36)")
    private String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
