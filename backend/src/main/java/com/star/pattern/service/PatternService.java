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
            int half = Math.round(height/2)+1;
            for (int i = 1; i < half; i++) {
                tree.add(blank.repeat(half-i) + star.repeat(i) + blank.repeat(half-i));
            }
            for (int i = half; i > 0; i--) {
                tree.add(blank.repeat(half-i) + star.repeat(i) + blank.repeat(half-i));
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

        return tree;
    }
}
