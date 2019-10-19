package com.arise.steiner.services.impl;

import com.arise.steiner.dto.CreateMultipleItemsRequest;
import com.arise.steiner.names.Keys;
import com.arise.steiner.entities.Item;
import com.arise.steiner.entities.Node;
import com.arise.steiner.dto.CreateItemRequest;
import com.arise.steiner.dto.CustomMapper;
import com.arise.steiner.dto.FilePage;
import com.arise.steiner.dto.FileSet;
import com.arise.steiner.dto.UploadResponse;
import com.arise.steiner.errors.ErrorKey;
import com.arise.steiner.repository.NodesRepository;
import com.arise.steiner.repository.ItemContentRepository;
import com.arise.steiner.repository.ItemRepository;
import com.arise.steiner.services.ItemsService;
import com.arise.steiner.services.FilteringService;
import com.arise.steiner.errors.EmptyNameException;
import com.arise.steiner.errors.EntityNotFoundException;
import com.arise.steiner.services.FileContentService;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


/**
 * Service Implementation for managing Item.
 */
@Service
public class ItemsServiceImpl implements ItemsService {

    private final Logger log = LoggerFactory.getLogger(ItemsServiceImpl.class);

    private final ItemRepository itemRepository;
    private final NodesRepository nodesRepository;

    private final FileContentService fileContentService;
    private final FilteringService filteringService;
    private final ItemContentRepository itemContentRepository;

    public ItemsServiceImpl(ItemRepository itemRepository, NodesRepository nodesRepository,
        FileContentService fileContentService, FilteringService filteringService,
        ItemContentRepository itemContentRepository) {
        this.itemRepository = itemRepository;
        this.nodesRepository = nodesRepository;
        this.fileContentService = fileContentService;
        this.filteringService = filteringService;
        this.itemContentRepository = itemContentRepository;
    }


    @Override
    public Item upload(Node node, String uploadUser, String uploadUserDomain, MultipartFile multipartFile, Set<String> tags)
        throws Exception {

        //validate input
        if (node == null || multipartFile == null || StringUtils.isEmpty(uploadUser)) {
            log.warn(Keys.UPLOAD_USER_REQUIRED);
            throw new IllegalArgumentException(Keys.UPLOAD_USER_REQUIRED);
        }

        String filename = multipartFile.getOriginalFilename();
        if (filename == null || filename.isEmpty()) {
            log.warn("Cannot upload file without name");
            throw new EmptyNameException(ErrorKey.FILE_WITHOUT_NAME, Item.class);
        }

        //TODO completable future
        //save file metadata details in dh_file transactional
        Item dhItem = saveDhFileDetails(node, uploadUser, uploadUserDomain, multipartFile, filename);

        //save file content in the repository and update statuses and reference to content - was asynchronously saved initially
        uploadContent(dhItem, node, multipartFile.getInputStream(), dhItem.getName());

        return dhItem;
    }

    protected Item saveDhFileDetails(Node node, String uploadUser, String uploadUserDomain, MultipartFile inputFile,
        String filename) {
        Item item = new Item();
        item.setName(filename);
        item.setNode(node);
        item.setMimeType(inputFile.getContentType());
        item.setSize(inputFile.getSize());
        item.setNotes("waiting_write");
        item.setCreationDate(new Date());
        item.setCreatedBy(uploadUser);
        item.setUpdateDate(new Date());
        itemRepository.save(item);
        return item;
    }


    private void uploadContent(final Item item, final Node node, InputStream inputStream,
        String filename) throws Exception {

        log.info("UPLOAD ::: init upload for item [ {} ]", item.getId());

        UploadResponse uploadResponse = null;
        try {
            uploadResponse = fileContentService.upload(item, node, inputStream, filename);
            log.info("UPLOAD ::: response for item {} {}", item.getId(), uploadResponse);
        } catch (MalformedURLException e) {
            log.error("Error: UPLOAD ::: nu se poate face upload in DRS datorita URL-ului incorect. ", e);
        }

        if (uploadResponse != null && uploadResponse.isSuccess()) {
            log.info("UPLOAD ::: write content complete with SUCCESS for item [ {} ]", item.getId());
            item.setNotes("write_completed");
            item.setPath(uploadResponse.getDbFilePath());
        } else {
            log.info("UPLOAD ::: write content FAILED for item [ {} ]", item.getId());
            item.setNotes("drs_upload_failed");
        }

        itemRepository.save(item);
        node.setCurrentItem(item);
        nodesRepository.save(node);
    }


    @Override
    public Item findOne(Long id) {
//        return fileRepository.findOne(id);
        return null;
    }

    @Override
    @Transactional
    public Item findByIdUnfiltered(Long id) throws EntityNotFoundException {
        Item item = itemRepository.getNativeById(id);
        if (item == null) {
            throw new EntityNotFoundException(Item.class);
        }
        return item;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
//
//        Item item = fileRepository.findOne(id);
//        if (item == null) {
//            return false;
//        }
//
//        item.getNode().setCurrentItem(null);
//        fileRepository.save(item);
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public FilePage findAll(Pageable pageable) {
        return new FilePage(itemRepository.findAll(pageable).getContent(), pageable);
    }

    @Override
    public Set<Item> getByNode(Node node) {
        return itemRepository.getByNode(node);
    }

    @Transactional
    @Override
    public void deleteItem(Item item) {
        if (item != null) {
            itemRepository.save(item);
        }
    }

    @Override
    public void save(Item item) {  //TODO AK return Item type
        itemRepository.save(item);
    }

    @Override
    public FileSet createFiles(CreateMultipleItemsRequest createMultipleItemsRequest) throws EntityNotFoundException {
        Node node = null;
        //nodesRepository.findById(String.valueOf(createMultipleItemsRequest.getDocumentId()));
        if (node == null) {
            throw new EntityNotFoundException(Node.class);
        }

        Set<Item> docItems = node.getItems();

        if (docItems == null) {
            docItems = new HashSet<>();
        }

        List<Item> items = new ArrayList<>();

        for (CreateItemRequest request : createMultipleItemsRequest.getFiles()) {
            Item f = new Item();
            f.setNode(node);
            f.setName(request.getName());
            f.setSize(request.getSize());
            f.setMimeType(request.getMimeType());
            f.setPath(request.getPath());
            f.setServiceId(request.getServiceId());
            f.setNotes(request.getNotes());
            f.setCreatedBy(request.getUserId());
            f.setCreationDate(new Date());
            f.setUpdateDate(new Date());
            items.add(f);
            docItems.add(f);

            if (request.isCurrent()) {
                node.setCurrentItem(f);
            }
            itemRepository.save(f);

            f.setProperties(
                filteringService.validateFileProperties(request.getProps(), f)
            );

            f.setTags(
                filteringService.validateFileTags(request.getTags(), f)
            );

            itemRepository.save(f);

        }

        node.setItems(docItems);

        nodesRepository.save(node);

        return new FileSet(items);
    }

    @Override
    public void putContent(Item itemEntity, MultipartFile multipartFile) throws IOException, SQLException {
//        ItemBlobContent itemBlobContent = fileContentRepository.findOne(itemEntity.getId());
//        if (itemBlobContent == null) {
//            itemBlobContent = new ItemBlobContent();
//        }
//
//        itemBlobContent.setId(itemEntity.getId());
//        String content = org.apache.commons.io.IOUtils.toString(multipartFile.getInputStream(), "UTF-8");
//
//        itemBlobContent.setContent(
//            new javax.sql.rowset.serial.SerialClob(content.toCharArray())
//        );
//
//        fileContentRepository.save(itemBlobContent);
    }

    @Override
    public Item update(Item item, Map<String, Object> requestMap) {
        new CustomMapper(requestMap)
            .forString("path", value -> item.setPath(value))
            .forString("serviceId", value -> item.setServiceId(value))
            .forString("name", value -> item.setName(value))
            .forString("status", value -> item.setStatus(value))
        ;
        itemRepository.save(item);
        return item;
    }


}
