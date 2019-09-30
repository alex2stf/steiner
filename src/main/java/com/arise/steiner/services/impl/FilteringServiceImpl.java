package com.arise.steiner.services.impl;


import com.arise.steiner.entities.Node;
import com.arise.steiner.entities.Word;
import com.arise.steiner.services.FilteringService;
import com.arise.steiner.entities.NodeProperty;
import com.arise.steiner.entities.NodeTag;
import com.arise.steiner.entities.Item;
import com.arise.steiner.entities.ItemProperty;
import com.arise.steiner.entities.ItemTag;
import com.arise.steiner.entities.Property;
import com.arise.steiner.entities.model.InfoID;
import com.arise.steiner.repository.NodePropertyRepository;
import com.arise.steiner.repository.NodeTagRepository;
import com.arise.steiner.repository.ItemPropertyRepository;
import com.arise.steiner.repository.ItemTagRepository;
import com.arise.steiner.repository.PropertyRepository;
import com.arise.steiner.repository.TagRepository;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class FilteringServiceImpl implements FilteringService {

    private final TagRepository tagRepository;
    private final NodePropertyRepository nodePropertyRepository;
    private final NodeTagRepository nodeTagRepository;
    private final PropertyRepository propertyRepository;
    private final ItemTagRepository itemTagRepository;
    private final ItemPropertyRepository itemPropertyRepository;

    public FilteringServiceImpl(TagRepository tagRepository, NodePropertyRepository nodePropertyRepository,
        NodeTagRepository nodeTagRepository, PropertyRepository propertyRepository,
        ItemTagRepository itemTagRepository, ItemPropertyRepository itemPropertyRepository) {
        this.tagRepository = tagRepository;
        this.nodePropertyRepository = nodePropertyRepository;
        this.nodeTagRepository = nodeTagRepository;
        this.propertyRepository = propertyRepository;
        this.itemTagRepository = itemTagRepository;
        this.itemPropertyRepository = itemPropertyRepository;
    }


    @Override
    public Set<NodeTag> validateNodeTags(Collection<String> strings, Node node) {

        Set<NodeTag> rs = new HashSet<>();
        if (strings == null || strings.isEmpty()) {
            return rs;
        }

        for (String s : strings) {
            Word tag = getTag(s);
//            NodeTag nodeTag = documentTagRepository.findByTagAndNodeId(tag, node.getId());
//            if (nodeTag == null) {
//                nodeTag = new NodeTag();
//                nodeTag.setId(new InfoID());
//                nodeTag.setNode(node);
//                nodeTag.setTag(tag);
//                nodeTag.setCreatedOn(new Date());
//                documentTagRepository.save(nodeTag);
//            }
//            rs.add(nodeTag);
        }

        return rs;
    }

    private Word getTag(String s) {
        Word tag = tagRepository.findByValue(s);
        if (tag == null) {
            tag = new Word();
            tag.setValue(s);
            tagRepository.save(tag);
        }
        return tag;
    }

    @Override
    public Set<ItemTag> validateFileTags(Set<String> strings, Item f) {

        Set<ItemTag> rs = new HashSet<>();
        if (strings == null || strings.isEmpty()) {
            return rs;
        }

        for (String s : strings) {
            Word tag = getTag(s);
//            ItemTag itemTag = fileTagRepository.findByTagAndItemId(tag, f.getId());
//
//            if (itemTag == null) {
//                itemTag = new ItemTag();
//                itemTag.setId(new InfoID());
//                itemTag.setItem(f);
//                itemTag.setTag(tag);
//                itemTag.setCreatedOn(new Date());
//                fileTagRepository.save(itemTag);
//            }
//            rs.add(itemTag);
        }
        return rs;
    }

    private Property getProperty(String key, String value) {
        Property property = propertyRepository.findByKeyAndValue(key, value);
        if (property == null) {
            property = new Property();
            property.setKey(key);
            property.setValue(value);
            propertyRepository.save(property);
        }
        return property;
    }

    @Override
    public Set<NodeProperty> validateNodeProperties(Map<String, String> props, Node node) {
        Set<NodeProperty> r = new HashSet<>();
        if (props == null || props.isEmpty()) {
            return r;
        }

        for (Map.Entry<String, String> entry : props.entrySet()) {
            Property property = getProperty(entry.getKey(), entry.getValue());

            NodeProperty nodeProperty;
            nodeProperty = new NodeProperty();
            nodeProperty.setId(new InfoID());
            nodeProperty.getId().setInfoId(property.getId());
            nodeProperty.setProperty(property);
            nodeProperty.setNode(node);
            nodePropertyRepository.save(nodeProperty);
            r.add(nodeProperty);
        }
        return r;
    }

    @Override
    public Set<ItemProperty> validateFileProperties(Map<String, String> props, Item f) {

        Set<ItemProperty> r = new HashSet<>();
        if (props == null || props.isEmpty()) {
            return r;
        }
        for (Map.Entry<String, String> entry : props.entrySet()) {
            Property property = getProperty(entry.getKey(), entry.getValue());

            ItemProperty itemProperty;
            itemProperty = new ItemProperty();
            itemProperty.setId(new InfoID());
            itemProperty.setItem(f);
            itemProperty.setProperty(property);
            itemPropertyRepository.save(itemProperty);
            r.add(itemProperty);
        }



        return r;
    }


    boolean collectionHasContent(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

}
