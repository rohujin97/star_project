package com.star.pattern.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class PatternRepository {

    private static Map<Long, Pattern> store = new HashMap<>();
    private static long count = 0L;
    private final static PatternRepository instance = new PatternRepository();

    public static PatternRepository getInstance() {
        return instance;
    }

    public PatternRepository() {
    }

    public Pattern save(Pattern pattern) {
        pattern.setId(++count);
        store.put(pattern.getId(), pattern);
        return pattern;
    }

    public Pattern findById(Long id) {
        return store.get(id);
    }

    public List<Pattern> findAll() {
        return new ArrayList<>(store.values());
    }

}
