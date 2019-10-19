package com.arise.steiner.controllers;

import com.arise.steiner.entities.Item;
import com.arise.steiner.dto.CreateMultipleItemsRequest;
import com.arise.steiner.dto.FilePage;
import com.arise.steiner.dto.FileSet;
import com.arise.steiner.errors.LogicalError;
import com.arise.steiner.errors.CommunicationException;
import com.arise.steiner.errors.EntityNotFoundException;
import com.arise.steiner.services.FileContentService;
import com.arise.steiner.services.ItemsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing Item.
 */
@RestController
@RequestMapping("/api")
public class ItemsController {

    private final Logger log = LoggerFactory.getLogger(ItemsController.class);

    private final ItemsService itemsService;
    private final FileContentService fileContentService;


    public ItemsController(ItemsService itemsService, FileContentService fileContentService) {
        this.itemsService = itemsService;
        this.fileContentService = fileContentService;
    }


    @PostMapping("/items")
    public ResponseEntity<FileSet> createFile(@RequestBody CreateMultipleItemsRequest createMultipleItemsRequest) throws EntityNotFoundException {
        FileSet response = itemsService.createFiles(createMultipleItemsRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * @return
     */
    @ApiOperation(value = "fetch files using a pageable",
        notes = "fetch files using a pageable",
        response = FilePage.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful fetched page", response = FilePage.class)}
    )
    @GetMapping("/files")
    public ResponseEntity<FilePage> getAllFiles(Pageable pageable) {
        log.info("REST request to get a page of Files");
        FilePage page = itemsService.findAll(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    /**
     * fetch a file by {@link Item#id}
     */
    @GetMapping("/files/{id}")
    public ResponseEntity<Item> getFile(@PathVariable Long id) {
        log.info("REST request to get Item : {}", id);
        Item item = itemsService.findOne(id);
        return ResponseEntity.ok(item);
    }

    /**
     * delete a file by {@link Item#id}
     */
    @DeleteMapping("/files/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        log.debug("REST request to delete Item : {}", id);
        itemsService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * used to download/preview a file.
     *
     * @param fileId as defined by {@link Item#id}
     */
    @ApiOperation(value = "used to download/preview a file.",
        notes = "used to download/preview a file.",
        response = InputStreamResource.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful download", response = InputStreamResource.class),
            @ApiResponse(code = 404, message = "Item not found", response = EntityNotFoundException.class),
            @ApiResponse(code = 403, message = "Some business logic error occured", response = LogicalError.class),
            @ApiResponse(code = 400, message = "Broken communication with other services", response = CommunicationException.class)
        }
    )
    @RequestMapping(value = "/files/{fileId}/download", method = RequestMethod.GET)
    public void download(@PathVariable("fileId") Long fileId,
        HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws Exception {
        Item itemEntity = itemsService.findByIdUnfiltered(fileId);
        if (itemEntity == null) {
            log.warn("Item {} not found", fileId);
            throw new EntityNotFoundException(Item.class);
        }

        fileContentService.solve(httpResponse, httpRequest, itemEntity);
    }


    @PostMapping("/files/{fileId}/content")
    public ResponseEntity<Item> putContent(@PathVariable("fileId") Long fileId,
        @RequestParam("file") MultipartFile multipartFile) throws EntityNotFoundException, IOException, SQLException {
        Item itemEntity = itemsService.findByIdUnfiltered(fileId);
        itemsService.putContent(itemEntity, multipartFile);
        return new ResponseEntity<>(itemEntity, HttpStatus.OK);
    }


    @ApiOperation(value = "used to update a file.",
        notes = "used to update a file.",
        response = Item.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success update", response = Item.class)
        }
    )
    @PutMapping("files/{fileId}")
    public ResponseEntity<Item> updateFile(
        @ApiParam(value = "a valid file id", required = true) @PathVariable("fileId") Long fileId,
        @ApiParam(value = "a set of optional properties to be updated") @RequestBody Map<String, Object> requestMap ) {
        Item itemEntity = itemsService.findOne(fileId);

        itemsService.update(itemEntity, requestMap);

        return new ResponseEntity<>(itemEntity, HttpStatus.OK);
    }

}
