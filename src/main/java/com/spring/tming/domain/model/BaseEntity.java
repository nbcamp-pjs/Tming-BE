package com.spring.tming.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp createTimestamp;

    @Column @UpdateTimestamp private Timestamp updateTimestamp;
}
