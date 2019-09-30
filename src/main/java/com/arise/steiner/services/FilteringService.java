package com.arise.steiner.services;

import com.arise.steiner.entities.NodeProperty;
import com.arise.steiner.entities.Node;
import com.arise.steiner.entities.NodeTag;
import com.arise.steiner.entities.Item;
import com.arise.steiner.entities.ItemProperty;
import com.arise.steiner.entities.ItemTag;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface FilteringService {

    Set<NodeTag> validateNodeTags(Collection<String> strings, Node node);

    Set<ItemTag> validateFileTags(Set<String> tags, Item f);

    Set<NodeProperty> validateNodeProperties(Map<String, String> props, Node node);

    Set<ItemProperty> validateFileProperties(Map<String, String> props, Item f);


}
