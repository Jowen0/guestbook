package org.zerock.guestbook.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Guestbook extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동으로 만들어지는 값이다. strategy - 어떻게 만들것이냐
    private Long gno;                                   //MySQL, MariaDB는 IDENTITY / Oracle은 추가적인 어노테이션 필요

    private String title;

    private String content;

    private String writer;

    public void changeTitle(String title) {

        this.title = title;

    }

    public void changeContent(String content) {

        this.content = content;

    }

}
