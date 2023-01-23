package com.test.vaiv.controller;

import com.test.vaiv.domain.Press;
import com.test.vaiv.dto.PressDto;
import com.test.vaiv.dto.SearchDto;
import com.test.vaiv.service.PressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class PressController {
    private final PressService pressService;

    @GetMapping("/press")
    public String getPress(@ModelAttribute @Valid SearchDto searchDto, Model model) {
        List<PressDto> pressList = pressService.findPressByOption(searchDto);
        model.addAttribute("press", pressList);
        model.addAttribute("title", getTitle(searchDto));
        return "index";
    }

    @GetMapping("/api/press")
    @ResponseBody
    public Map<String, Object> getPress(@ModelAttribute @Valid SearchDto searchDto) {
        HashMap<String, Object> ret = new HashMap<>();
        List<PressDto> pressList = pressService.findPressByOption(searchDto);
        ret.put("press", pressList);
        ret.put("title", getTitle(searchDto));
        return ret;
    }

    @ExceptionHandler(BindException.class)
    public String error(Model model) {
        model.addAttribute("error", true);
        return "index";
    }

    private String getTitle(SearchDto searchDto) {
        if (!searchDto.getName().isEmpty()) {
            return String.format("%s 기자회견 타임라인", searchDto.getName());
        }
        if (!searchDto.getParty().isEmpty()) {
            return String.format("%s 기자회견 타임라인", searchDto.getParty());
        }
        if (searchDto.getStartDate() != null && searchDto.getEndDate() != null) {
            return String.format("%s ~ %s 타임라인", searchDto.getStartDate(), searchDto.getEndDate());
        }
        if (searchDto.getStartDate() != null) {
            return String.format("%s 이후 타임라인", searchDto.getStartDate());
        }
        if (searchDto.getEndDate() != null) {
            return String.format("%s 이전 타임라인", searchDto.getStartDate());
        }
        return "전체 타임라인";
    }
}
