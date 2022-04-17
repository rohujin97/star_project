package com.star.pattern.service;

import com.star.pattern.domain.PatternVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatternService {

    public List<String> getPatternTree(int pattern, int height) {

        List<String> tree = new ArrayList<>();
        String star = "*";
        String blank = "\u00A0";

        if (pattern == 1) {
            for (int i = 1; i <= height; i++) {
                tree.add(star.repeat(i));
            }
        } else if (pattern == 2) {
            for (int i = height; i > 0; i--) {
                tree.add(star.repeat(i));
            }
        } else if (pattern == 3) {
            int half = Math.round(height/2)+1;
            for (int i = 1; i < half; i++) {
                tree.add(star.repeat(i));
            }
            for (int i = half; i > 0; i--) {
                tree.add(star.repeat(i));
            }
        } else if (pattern == 4) {
            for (int i = height-1; i >= 0; i--) {
                tree.add(blank.repeat(i) + star.repeat(height));
            }
        } else if (pattern == 5) {
            for (int i = height; i > 0; i--) {
                tree.add(blank.repeat(i-1) + star.repeat(i));
            }
        }

        return tree;
    }
}
