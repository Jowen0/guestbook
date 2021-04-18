package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.service.GuestbookService;

@Log4j2
@RequestMapping("/guestbook")
@Controller
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping({"/", "/list"})
    public String list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list................................." + pageRequestDTO);
        log.info(service.getList(pageRequestDTO));

        model.addAttribute("result", service.getList(pageRequestDTO));

        return "/guestbook/list";
    }

    @GetMapping("/register")
    public void register() {

        log.info("register...............");

    }

}
