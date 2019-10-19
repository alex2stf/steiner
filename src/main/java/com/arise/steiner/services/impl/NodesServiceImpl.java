package com.arise.steiner.services.impl;

import com.arise.steiner.Mapper;
import com.arise.steiner.client.UpdateNodeRequest;
import com.arise.steiner.cqrs.commands.CreateNodeCmd;
import com.arise.steiner.cqrs.commands.PatchNodeCmd;
import com.arise.steiner.dto.User;
import com.arise.steiner.entities.Node;
import com.arise.steiner.client.CreateNodeRequest;
import com.arise.steiner.dto.CustomMapper;
import com.arise.steiner.errors.EmptyNodeException;
import com.arise.steiner.errors.EntityNotFoundException;
import com.arise.steiner.errors.ErrorKey;
import com.arise.steiner.repository.NodesRepository;
import com.arise.steiner.repository.ItemRepository;
import com.arise.steiner.services.EventSourceService;
import com.arise.steiner.services.IDService;
import com.arise.steiner.services.NodesService;
import com.arise.steiner.services.FilteringService;
import com.arise.steiner.services.HistoryService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.arise.core.tools.CollectionUtil.isEmpty;
import static com.arise.steiner.Mapper.createdNodeEvent;
import static com.arise.steiner.Mapper.updateNodeEvent;


/**
 * Service Implementation for managing Node.
 */
@Service
public class NodesServiceImpl implements NodesService {

    private static final Logger log = LoggerFactory.getLogger(NodesServiceImpl.class);
    private final NodesRepository nodesRepository;
    private final ItemRepository itemRepository;
    private final FilteringService filteringService;

    private final HistoryService historyService;

    private final IDService idService;

    private final EventSourceService eventSourceService;


    public NodesServiceImpl(NodesRepository nodesRepository,
                            ItemRepository itemRepository, FilteringService filteringService, HistoryService historyService, IDService idService, EventSourceService eventSourceService) {
        this.nodesRepository = nodesRepository;
        this.itemRepository = itemRepository;
        this.filteringService = filteringService;
        this.historyService = historyService;
        this.idService = idService;
        this.eventSourceService = eventSourceService;
    }

    @Transactional
    @Override
    public Node createNode(CreateNodeRequest request, User requestor) {
        Node node = Mapper.fromRequest(request);
        node.setCreatedBy(requestor.getName());
        node.setUpdateDate(new Date());
        node.setId(idService.next());


        CompletableFuture.supplyAsync(() -> {
            nodesRepository.save(node);
            eventSourceService.send(new CreateNodeCmd(node));

            node.setTags(
                filteringService.validateNodeTags(request.getTags(), node)
            );

            node.setProperties(
                filteringService.validateNodeProperties(request.getProps(), node)
            );

            if (!isEmpty(node.getTags()) || !isEmpty(node.getProperties())){
                nodesRepository.save(node);
                eventSourceService.send(new PatchNodeCmd(node));
            }

            return true;
        });


        return node;
    }

    @Override
    public Node findOne(String id) throws EntityNotFoundException {
        Node node = nodesRepository.findById(id).get();
        if (node == null) {
            log.warn("Failed to find node with id {}", id);
            throw new EntityNotFoundException(Node.class);
        }
        return node;
    }

    @Override
    public List<Node> getByProductId(Long productId) {
        return nodesRepository.getByProductId(productId);
    }


    @Transactional
    @Override
    public void delete(String id) {
        Node node = nodesRepository.findById(id).get();

        if (node == null || node.getCurrentItem() == null || node.getCurrentItem().getId() == null) {
            log.warn("Node with id {} is null or has no current file", id);
            throw new EmptyNodeException(ErrorKey.NO_DOCUMENT_FOUND);
        }



        node.setCurrentItem(null);
        nodesRepository.save(node);
    }

    @Transactional
    @Override
    public void save(Node node) {
        nodesRepository.save(node);
    }


    @Override
    public List<Node> getMultiples(List<Long> documentsIds) {
        List<Long> args = documentsIds
            .stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        return nodesRepository.findByIdIn(args);
    }

    @Override
    public void update(Node node, UpdateNodeRequest request) {

    }

    public void update(Node node, Map<String, Object> requestMap) {
        historyService.logBeforeUpdate(node);

        new CustomMapper(requestMap)
            .forString("documentTypeDescription", value -> node.setDescription(value))
            .forString("documentType", value -> node.setType(value))
            .forString("reason", value -> node.setReason(value))
//            .forString("productName", value -> node.setCode(value))
//            .forString("productName", value -> node.setCode(value))
//            .forLong("productId", productId -> node.setProductId(productId))
//            .forString("productCode", value -> node.setProduct(value))
            .forString("phase", value -> node.setPhase(value))
            .forString("source", value -> node.setSource(value))
            .forString("status", value -> node.setStatus(value))
        ;

        node.setUpdateDate(new Date());

        nodesRepository.save(node);

    }




}
