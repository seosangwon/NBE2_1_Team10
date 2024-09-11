package com.example.coffeeshop.product.domain;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @PrePersist // 데이터가 생성되기 전
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }

    @PostUpdate // 데이터가 업데이트 된 후
    protected  void onUpdate(){
        updatedAt = LocalDateTime.now();
    }
}
