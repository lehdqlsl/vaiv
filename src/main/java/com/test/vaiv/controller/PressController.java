package com.test.vaiv.controller;

import com.test.vaiv.domain.Press;
import com.test.vaiv.dto.PressDto;
import com.test.vaiv.dto.SearchDto;
import com.test.vaiv.service.PressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class PressController {
    private final PressService pressService;

    @GetMapping("/press")
    public String getPress(@ModelAttribute @Valid SearchDto searchDto, Model model) {

        System.out.println("인자:"+searchDto);
        List<PressDto> pressList = pressService.findPressByOption(searchDto);
        System.out.println(pressList);
        model.addAttribute("press", pressList);
        return "index";
    }

}
