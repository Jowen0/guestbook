package org.zerock.guestbook.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestbookDTO {

    private Long gno;

    private String title;

    private String content;

    private String writer;

    private LocalDateTime regDate, modDate;

}
