package com.arise.steiner.services;

import com.arise.steiner.dto.CreateMultipleItemsRequest;
import com.arise.steiner.entities.Item;
import com.arise.steiner.entities.Node;
import com.arise.steiner.dto.FilePage;
import com.arise.steiner.dto.FileSet;
import com.arise.steiner.errors.EntityNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for managing Item.
 */
public interface ItemsService {

    Item upload(Node node, String uploadUser, String uploadUserDomain, MultipartFile multipartFile, Set<String> tags) throws Exception;

    Item findOne(Long id);

    Item findByIdUnfiltered(Long id) throws EntityNotFoundException;

    boolean delete(Long id);

    FilePage findAll(Pageable pageable);

    Set<Item> getByNode(Node node);

    void deleteItem(Item currentItem);

    void save(Item item);

    FileSet createFiles(CreateMultipleItemsRequest createMultipleItemsRequest) throws EntityNotFoundException;

    void putContent(Item itemEntity, MultipartFile multipartFile) throws IOException, SQLException;

    Item update(Item itemEntity, Map<String,Object> requestMap);
}
