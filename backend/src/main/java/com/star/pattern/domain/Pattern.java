package com.star.pattern.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Pattern {

    private Long id;
    private Long pattern;
    private Object tree;

    public Pattern(Long pattern, Object tree) {
        this.pattern = pattern;
        this.tree = tree;
    }
}
