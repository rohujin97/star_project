package com.star.pattern.service;

import com.star.pattern.domain.Pattern;
import com.star.pattern.domain.PatternRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatternService {

    public Pattern getPatternTree(long pattern, int height) {

        PatternRepository patternRepository = PatternRepository.getInstance();
        List<String> tree = new ArrayList<>();
        String star = "\uD83D\uDC7B";
        String blank = "\u00A0".repeat(4);
        int width;

        if (pattern == 1) {
            for (int i = 1; i <= height; i++) {
                tree.add(star.repeat(i) + blank.repeat(height-i));
            }
        } else if (pattern == 2) {
            for (int i = height; i > 0; i--) {
                tree.add(blank.repeat(height-i) + star.repeat(i));
            }
        } else if (pattern == 3) {
            int half = (int) Math.floor(height/2);
            for (int i = 0; i < height; i++) {
                int v1 = Math.abs(half - i);
                int v2 = half - v1;
                tree.add(blank.repeat(v1) + star.repeat(v2 * 2 + 1) + blank.repeat(v1));
            }
        } else if (pattern == 4) {
            for (int i = height-1; i >= 0; i--) {
                tree.add(blank.repeat(i) + star.repeat(height));
            }
        } else if (pattern == 5) {
            width = 2 * height - 1;
            for (int i = height; i > 0; i--) {
                tree.add(blank.repeat(i-1) + star.repeat(i) + blank.repeat(width-2*i+1));
            }
        } else if (pattern == 6) {
            for (int i = 1; i <= height; i++) {
                for (int j = 1; j <= i; j++) {
                    tree.add(star.repeat(j) + blank.repeat(height-j));
                }
                tree.add(blank);
            }
        }

        Pattern patternVO = new Pattern(pattern, tree);

        return patternRepository.save(patternVO);
    }
}
