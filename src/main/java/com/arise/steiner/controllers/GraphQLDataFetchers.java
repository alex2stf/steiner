package com.arise.steiner.controllers;


import com.arise.steiner.entities.Item;
import com.arise.steiner.entities.ItemTag;
import com.arise.steiner.entities.Node;
import com.arise.steiner.entities.Word;
import com.arise.steiner.dto.CustomMapper;
import com.arise.steiner.errors.ErrorKey;
import com.arise.steiner.errors.GraphQLDocException;
import com.arise.steiner.repository.NodesRepository;
import com.arise.steiner.repository.ItemRepository;
import com.arise.steiner.repository.TagRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class GraphQLDataFetchers {

    private final ItemRepository itemRepository;
    private final TagRepository tagRepository;
    private final NodesRepository nodesRepository;


    public GraphQLDataFetchers(ItemRepository itemRepository, TagRepository tagRepository,
        NodesRepository nodesRepository) {
        this.itemRepository = itemRepository;
        this.tagRepository = tagRepository;
        this.nodesRepository = nodesRepository;
    }


    public DataFetcher<Set<Item>> getFileByTagDataFetcher() {
        return dataFetchingEnvironment -> {
            List<String> args = getStringArgs(dataFetchingEnvironment, "tags");
            Set<Word> tags = tagRepository.findByValueIn(args);
            Set<Item> response = new HashSet<>();
            for (Word tag : tags) {
                for (ItemTag itemTag : tag.getItemTags()) {
                    response.add(itemTag.getItem());
                }
            }
            return response;
        };
    }


    List<String> getStringArgs(DataFetchingEnvironment dataFetchingEnvironment, String key) {
        Map<String, Object> args = dataFetchingEnvironment.getArguments();
        if (!args.containsKey(key)) {
            throw new GraphQLDocException(ErrorKey.MISSING_GRAPHQL_KEY, "missing.key." + key);
        }
        Object value = args.get(key);
        List<String> response = new ArrayList<>();
        if (value instanceof Iterable) {
            Iterable t = (Iterable) value;
            for (Object s : t) {
                response.add(String.valueOf(s));
            }
        } else {
            throw new GraphQLDocException(ErrorKey.INVALID_TYPE_CAST, "failed.to.cast.graphql.object");
        }
        return response;

    }

    public DataFetcher<Set<Node>> getDocumentsByProdutIdAndStatus() {
        return new DataFetcher<Set<Node>>() {
            @Override
            public Set<Node> get(DataFetchingEnvironment dataFetchingEnvironment) throws Exception {

                Map<String, Object> args = dataFetchingEnvironment.getArguments();
                Long productId = CustomMapper.getLong(args, "productId");
                String status = CustomMapper.getString(args, "status");

                if (productId != null && status != null){
                    return nodesRepository.findByProductIdAndStatus(productId, status);
                }

                return null;
            }
        };
    }

    public DataFetcher<Set<Node>> documentsByProductIdAndStatusAndPhase() {
        return new DataFetcher<Set<Node>>() {
            @Override
            public Set<Node> get(DataFetchingEnvironment dataFetchingEnvironment) throws Exception {

                Map<String, Object> args = dataFetchingEnvironment.getArguments();
                Long productId = CustomMapper.getLong(args, "productId");
                String status = CustomMapper.getString(args, "status");
                String phase = CustomMapper.getString(args, "phase");
                if (productId != null && status != null && phase != null){
                    return nodesRepository.findByProductIdAndStatusAndPhase(productId, status, phase);
                }

                return null;
            }
        };
    }
}


