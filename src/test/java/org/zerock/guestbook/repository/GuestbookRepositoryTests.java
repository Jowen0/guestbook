package org.zerock.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;

import java.util.Arrays;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies() {

        IntStream.rangeClosed(1,300).forEach(i -> {

            Guestbook guestbook = Guestbook.builder().title("Title..." + i)
                                            .content("Content..." + i)
                                            .writer("user" + (i % 10))
                                            .build();

            log.info(guestbookRepository.save(guestbook));
            //서버의 시간을 기준으로 regdate & moddate가 들어간다, 반면에 hibernate는 DB시간으로 들어간다.

        });

    }

    @Test
    public void testUpdate() {

        Guestbook guestbook =  guestbookRepository.findById(300L).get();

        log.info("BEFORE-----------------------------------");
        log.info(guestbook);

        guestbook.changeTitle("Update 300 Tite");
        guestbook.changeContent("Update 300 Content");

        log.info("AFTER-----------------------------------");
        log.info(guestbook);

        guestbookRepository.save(guestbook);

        //update를 하는데 가져오는데 select 1번, update 하기전 select 1번, update 1번 총 3번 쿼리를 날리는 낭비가 발생한다.
        //이를 방지하기 위한 방법은 나중에 가르쳐 준다.

    }

    @Test
    public void testQuery1() {
        //페이지
        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());

        //querydsl
        QGuestbook qGuestbook = QGuestbook.guestbook;

        //검색 조건
        String keyword = "1";

        //조건문을 감싸기 위한 겉포장지
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        //title like ?
        BooleanExpression expression = qGuestbook.title.contains(keyword); //contains = like

        //and title like ?
        booleanBuilder.and(expression);

        //검색 + 페이지
        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);

        result.get().forEach(guestbook -> log.info(guestbook));

    }

    @Test
    public void testSearch() {

        //페이지
        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());

        String keyword = "1";
        String[] arr = null; //{"t","c"};

        //QDomain 처리
        QGuestbook qGuestbook = QGuestbook.guestbook;

        //total
        BooleanBuilder total = new BooleanBuilder();

        //condition
        BooleanBuilder condition = new BooleanBuilder();

        //gno
        BooleanBuilder gno = new BooleanBuilder();

        if(arr != null && arr.length >0) {
            //Arrays.stream을 이용한 forEach
            Arrays.stream(arr).forEach(type -> {

                log.info("type" + type);
                switch (type) {

                    case "t":
                        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
                        condition.or(exTitle);
                        break;
                    case "c":
                        BooleanExpression exContent = qGuestbook.content.contains(keyword);
                        condition.or(exContent);
                        break;
                    case "w":
                        BooleanExpression exWriter = qGuestbook.writer.contains(keyword);
                        condition.or(exWriter);
                        break;

                }

                total.and(condition);

                BooleanExpression exGno = qGuestbook.gno.gt(0L); //gt - greater than
                gno.and(exGno);

                total.and(gno);

            });//end loop
        }

        guestbookRepository.findAll(total,pageable);

    }

}
