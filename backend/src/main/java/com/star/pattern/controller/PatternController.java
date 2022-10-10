package com.star.pattern.controller;

import com.star.pattern.domain.Pattern;
import com.star.pattern.service.PatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pattern")
public class PatternController {

    @Autowired
    private PatternService patternService;

    @GetMapping("/tree/{pattern}/{height}")
    public Pattern getPatternResult(@PathVariable("pattern") Long pattern, @PathVariable("height") Integer height) {

        return patternService.getPatternTree(pattern, height);
    }
}
