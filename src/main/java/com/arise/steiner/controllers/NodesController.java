package com.arise.steiner.controllers;


import com.arise.steiner.client.UpdateNodeRequest;
import com.arise.steiner.config.Author;
import com.arise.steiner.dto.User;
import com.arise.steiner.entities.model.CSVDataStr;
import com.arise.steiner.entities.Item;
import com.arise.steiner.entities.Node;
import com.arise.steiner.client.CreateNodeRequest;
import com.arise.steiner.dto.NodeListDTO;
import com.arise.steiner.dto.NodeMapResponse;
import com.arise.steiner.dto.FileSet;
import com.arise.steiner.dto.IDSList;
import com.arise.steiner.errors.LogicalError;
import com.arise.steiner.errors.CommunicationException;
import com.arise.steiner.errors.EmptyNameException;
import com.arise.steiner.errors.EntityNotFoundException;
import com.arise.steiner.errors.ErrorKey;
import com.arise.steiner.names.Routes;
import com.arise.steiner.services.EventSourceService;
import com.arise.steiner.services.NodesService;
import com.arise.steiner.services.ItemsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing Node.
 */
@RestController
@RequestMapping("/api")
public class NodesController {

    private final Logger log = LoggerFactory.getLogger(NodesController.class);

    private final NodesService nodesService;

    private final ItemsService itemsService;



    public NodesController(NodesService nodesService, ItemsService itemsService) {
        this.nodesService = nodesService;
        this.itemsService = itemsService;
    }

    /**
     * create a single {@link Node}
     *
     * @param createNodeRequest document request json body
     * @return a new document
     */
    @PostMapping(Routes.NODES)
    public ResponseEntity<Node> createNode(@RequestBody CreateNodeRequest createNodeRequest, @Author User requestor) {
        log.debug("request to create node : {}", createNodeRequest);
        Node node = nodesService.createNode(createNodeRequest, requestor);
        log.debug("created node {}", node.getId());
        return new ResponseEntity<>(node, HttpStatus.CREATED);
    }


    @PutMapping(Routes.NODES + "/{id}")
    public ResponseEntity<Node> updateNode(@PathVariable String id, @RequestBody UpdateNodeRequest request) throws EntityNotFoundException {
        log.debug("REST request to update Node : {}", id);
        Node node = nodesService.findOne(id);
        nodesService.update(node, request);
        return ResponseEntity.ok(node);
    }

    /**
     * returns a document by {@link Node#id}
     */
    @ApiOperation(value = "used to fetch data about a given node",
            notes = "used to fetch data about a given node",
            response = Node.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = InputStreamResource.class),
            @ApiResponse(code = 404, message = "Node not found", response = EntityNotFoundException.class)
    }
    )
    @GetMapping(Routes.NODES + "/{id}")
    public ResponseEntity<Node> getNode(@PathVariable String id) throws EntityNotFoundException {
        log.debug("REST request to get Node : {}", id);
        Node node = nodesService.findOne(id);
        log.debug("REST request to get Node logic complete {}", id);
        return ResponseEntity.ok(node);
    }

    /**
     * POST  /documents/{id}/upload : Uploads a new binary file.
     *
     * @param multipartFile the documentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new documentDTO, or with status 400 (Bad Request) if the document has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */

    @ApiOperation(value = "used to upload a file.",
        notes = "used to upload a file.",
        response = InputStreamResource.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful upload", response = InputStreamResource.class),
        @ApiResponse(code = 404, message = "Item not found", response = EntityNotFoundException.class),
        @ApiResponse(code = 403, message = "Some business logic error occured", response = LogicalError.class),
        @ApiResponse(code = 400, message = "Broken communication with other services", response = CommunicationException.class)
    }
    )
    @PostMapping(value = "/nodes/{id}/upload", headers = "content-type=multipart/*")
    public ResponseEntity<Item> upload(@PathVariable String id,
        @RequestParam("file") MultipartFile multipartFile,
        @RequestHeader(value = "UPLOAD_USER_IDENTIFIER", required = false) String uploadUser,
        @RequestHeader(value = "UPLOAD_USER_DOMAIN", required = false) String uploadUserDomain,
        @RequestHeader(value = "FILE_TAGS", required = false) String tagsString)
        throws Exception {

        if (multipartFile.isEmpty()) {
            log.warn("Cannot upload item without bytes");
            throw new EmptyNameException(ErrorKey.EMTPY_BYTES, MultipartFile.class);
        }

//        itemsService.upload(id, u)

        Node node = nodesService.findOne(id);
        Set<String> tags = CSVDataStr.fromCSV(tagsString, data -> data);
        Item item = itemsService.upload(node, uploadUser, uploadUserDomain, multipartFile, tags);

        return new ResponseEntity<>(item, HttpStatus.CREATED);

    }





//    @PutMapping(Routes.NODES + "/{id}")
//    public ResponseEntity<Node> updateNode(@PathVariable String id, @RequestBody UpdateNodeRequest request ) throws EntityNotFoundException {
//        log.debug("REST request to update Node : {}", id);
//        Node node = nodesService.findOne(id);
//        nodesService.update(node, request);
//        return ResponseEntity.ok(node);
//    }

    /**
     * returns all the files of the documents
     */
    @ApiOperation(value = " returns all the files of the document",
        notes = "returns all the files of the document",
        response = FileSet.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FileSet.class),
            @ApiResponse(code = 404, message = "Item not found", response = EntityNotFoundException.class)
        }
    )
    @GetMapping("/documents/{id}/files")
    public ResponseEntity<Set<Item>> getFilesForDocument(@PathVariable String id) throws EntityNotFoundException {
        log.debug("REST request to get Node Files : {}", id);
        Node node = nodesService.findOne(id);
        Set<Item> itemSet = itemsService.getByNode(node);
        return new ResponseEntity<>(itemSet, HttpStatus.OK);
    }

    /**
     * deletes a single document by {@link Node#id}
     */
    @DeleteMapping("/documents/{id}")
    public ResponseEntity<Node> deleteDocument(@PathVariable String id) throws EntityNotFoundException {
        log.debug("REST request to delete Node : {}", id);
        Node node = nodesService.findOne(id);
        itemsService.deleteItem(node.getCurrentItem());
        node.setCurrentItem(null);
        nodesService.save(node);
        return new ResponseEntity<>(node, HttpStatus.OK);
    }


    /**
     * create multiple documents using a map of {@link CreateNodeRequest} for each unique id from the map a new {@link Node} will be created
     */
    @ApiOperation(value = "create multiple documents using a map of DocumentRequests",
        notes = "Create multiple documents using a map of DocumentRequests",
        response = NodeMapResponse.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful creation done", response = NodeMapResponse.class)}
    )
    @PostMapping(path = "/documents/bulkCreate")
    public ResponseEntity<NodeMapResponse> createMultiples(@RequestBody Map<String, CreateNodeRequest> request,
        @RequestHeader(value = "UPLOAD_USER_IDENTIFIER", required = false) String uploadUser,
        @RequestHeader(value = "UPLOAD_USER_DOMAIN", required = false) String uploadUserDomain) {
        NodeMapResponse response = new NodeMapResponse();
        response.setMap(new HashMap<>());
        for (Map.Entry<String, CreateNodeRequest> entry : request.entrySet()) {
            Node node = nodesService.createNode(entry.getValue(),null);
            response.getMap().put(entry.getKey(), node);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @param documentsIds
     * @return
     */
    @ApiOperation(value = "used to fetch multiple documents based on multiple ids",
        notes = "Used to fetch multiple documents based on list of ids (type long)",
        response = NodeListDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful fetch done", response = NodeListDTO.class)}
    )
    @PostMapping(path = "/documents/bulkFetch")
    public ResponseEntity<NodeListDTO> getMultiples(@RequestBody IDSList documentsIds) {
        List<Node> items = nodesService.getMultiples(documentsIds);
        return new ResponseEntity<>(NodeListDTO.from(items), HttpStatus.OK);
    }


    /**
     * Given a document, a new file will be created by cloning the given file and it will become the current file of the document
     *
     * @param documentId given document
     * @param fileId given file
     */
    @ApiOperation(value = "Creates a link between a given file and a document",
        notes = "Given a document D and a file F, a new file will be created by cloning F and it will become the current file for D",
        response = Node.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful linkage done", response = Node.class)}
    )
    @PutMapping(path = "/documents/{documentId}/file/{fileId}/link")
    public ResponseEntity<Node> createLink(@PathVariable("documentId") String documentId, @PathVariable("fileId") Long fileId)
        throws EntityNotFoundException {

        Node node = nodesService.findOne(documentId);
        Item base = itemsService.findOne(fileId);
        if (base == null) {
            throw new EntityNotFoundException(Item.class);
        }

        Item clone = new Item(base);
        itemsService.save(clone);

        if (base.getChildIds() == null) {
            base.setChildIds(new HashSet<>());
        }
        base.getChildIds().add(clone.getId());

        itemsService.save(base);
        node.setCurrentItem(clone);
        nodesService.save(node);

        return new ResponseEntity<>(node, HttpStatus.OK);
    }





}
