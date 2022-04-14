package com.star.pattern.controller;

import com.star.pattern.domain.PatternVO;
import com.star.pattern.service.PatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pattern")
public class PatternController {

    @Autowired
    private PatternService patternService;

    @GetMapping("/tree/{pattern}/{number}")
    public List<String> getPatternResult(@PathVariable("pattern") Integer pattern, @PathVariable("number") Integer number) {

        return patternService.getPatternTree(pattern, number);
    }
}
