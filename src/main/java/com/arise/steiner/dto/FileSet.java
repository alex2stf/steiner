package com.arise.steiner.dto;

import com.arise.steiner.entities.Item;
import java.util.Collection;
import java.util.HashSet;

public class FileSet extends HashSet<Item> {

    public FileSet() {

    }

    public FileSet(Collection<Item> items) {
        this.addAll(items);
    }
}
