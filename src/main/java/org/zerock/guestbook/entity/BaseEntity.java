package org.zerock.guestbook.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass //Entity를 만들지 않고, 상속으로 쓸 것이다.
@EntityListeners(value = {AuditingEntityListener.class}) //Entity 객체가 만들어지면 자동으로 감지한다.
@Getter //Entity니까 Getter
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "regdate", updatable = false) //컬럼명, 업데이트 여부
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate;

}
