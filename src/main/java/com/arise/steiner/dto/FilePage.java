package com.arise.steiner.dto;

import com.arise.steiner.entities.Item;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class FilePage extends PageImpl<Item> {

    public FilePage(List<Item> content, Pageable pageable) {
        super(content, pageable, content.size());
    }

    public FilePage(List<Item> content) {
        super(content);
    }
}
