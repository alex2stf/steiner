package com.arise.steiner.services.impl;

import com.arise.steiner.names.Keys;
import com.arise.steiner.entities.Item;
import com.arise.steiner.entities.Node;
import com.arise.steiner.dto.UploadResponse;
import com.arise.steiner.errors.LogicalError;
import com.arise.steiner.errors.ErrorKey;
import com.arise.steiner.repository.ItemContentRepository;
import com.arise.steiner.services.FileContentService;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class FileSystemFileContentService extends FileContentService {

    private final Logger log = LoggerFactory.getLogger(FileSystemFileContentService.class);
    private final ItemContentRepository itemContentRepository;

    public FileSystemFileContentService(ItemContentRepository itemContentRepository) {
        this.itemContentRepository = itemContentRepository;
    }

    public static String getUserDirectory() {
        return System.getProperty("user.home");
    }

    /**
     * uploads a item on disk, located under a path composed from {@link FileSystemFileContentService#getUserDirectory()}, {@link
     * Keys#DISK_DIRECTORY} and other informationd related to {@link Node} and associated {@link Item} If the root directory does not exists,
     * it will be created {@inheritDoc}
     */
    @Override
    public UploadResponse performUpload(Item item, Node node, InputStream inputStream, String filename) {
        Path directoryPath = Paths.get(
            FileSystemFileContentService.getUserDirectory(),
            Keys.DISK_DIRECTORY,
            node.getProduct() != null ? node.getProduct() : "unknown_products", //AS loan... whatever
            node.getProductId() != null ? String.valueOf(node.getProductId()) : "unknown_products",
            node.getPhase() != null ? node.getPhase() : "unknown_stage",
            node.getType() != null ? node.getType() : "unknown_type",
            String.valueOf("doc_id_" + node.getId()),
            "files",
            String.valueOf(item.getId())
        );

        java.io.File directory = directoryPath.toAbsolutePath().toFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        java.io.File destination = new java.io.File(directory.getAbsolutePath() + java.io.File.separator + filename);

        try (FileOutputStream fos = new FileOutputStream(destination)) {
            IOUtils.copy(inputStream, fos);
        } catch (Exception e) {
            log.error("failed to write item {} ", destination.toURI());
        }

        UploadResponse response = new UploadResponse();
        response.setSuccess(destination.exists());
        response.setDbFilePath(destination.toURI().toString());
        return response;
    }


    @Override
    protected String getServiceId() {
        return "file-system-local";
    }

    @Override
    public void solve(HttpServletResponse httpResponse, HttpServletRequest request, Item itemEntity) throws Exception {
//        if (fileContentRepository.exists(itemEntity.getId())) {
//            ItemBlobContent itemBlobContent = fileContentRepository.findOne(itemEntity.getId());
//            if (itemBlobContent.getContent() != null) {
//
//                Clob clob = itemBlobContent.getContent();
//
//                solveWithStream(
//                    httpResponse,
//                    clob.getAsciiStream(),
//                    itemEntity.getMimeType()
//                );
//                return;
//            }
//        }
//        if (pathIsHttp(itemEntity.getPath())) {
//            solveFromRemote(httpResponse, request, itemEntity);
//        } else {
//            solveFromLocalSystem(httpResponse, itemEntity);
//        }
    }


    private boolean pathIsHttp(String path) {
        return path.startsWith("http");
    }


    void solveFromRemote(HttpServletResponse httpResponse, HttpServletRequest request, Item itemEntity) {
        HttpURLConnection con = null;
        try {
            URL connectionURL = new URL(itemEntity.getPath());

            con = (HttpURLConnection) connectionURL.openConnection();
            con.setRequestMethod(request.getMethod());

            //chain headers from request
            Enumeration headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = (String) headerNames.nextElement();
                String value = request.getHeader(key);
                con.setRequestProperty(key, value);
            }

            con.setConnectTimeout(20000);
            con.setReadTimeout(20000);
            con.connect();

            int responseStatus = con.getResponseCode();
            log.info("retrived status code {}", responseStatus);
            httpResponse.setStatus(responseStatus);

            //copy response headers
            Map<String, List<String>> map = con.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                String name = entry.getKey();
                if (name != null && !"null".equals(name) && !httpResponse.containsHeader(name)) {
                    httpResponse.addHeader(name, entry.getValue().get(0));
                }
            }

            IOUtils.copy(con.getInputStream(), httpResponse.getOutputStream());
        } catch (Exception e) {
            log.error("Failed to fetch " + itemEntity.getPath(), e);
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                    log.info("closed connection to " + itemEntity.getPath());
                } catch (Exception e) {
                    log.info("connection already closed");
                }
            }
        }
    }


    void solveFromLocalSystem(HttpServletResponse httpResponse, Item itemEntity) throws LogicalError, IOException {
        log.info("Fetching bytes from file system {}", itemEntity.getPath());
        java.io.File file = null;
        try {
            file = new java.io.File(new URI(itemEntity.getPath()));
        } catch (Exception ex) {
            file = new java.io.File(itemEntity.getPath());
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new LogicalError(ErrorKey.FILE_NOT_FOUND_LOCALLY, Keys.FILE_NOT_FOUND_LOCALLY);
        }

        String mimeType = itemEntity.getMimeType();

        if (mimeType == null) {
            mimeType = Files.probeContentType(Paths.get(itemEntity.getPath()));
        }
        if (mimeType == null) {
            mimeType = MediaType.APPLICATION_PDF.getType();
        }

        solveWithStream(httpResponse, inputStream, mimeType);
    }

    private void solveWithStream(HttpServletResponse httpResponse, InputStream inputStream, String mimeType) throws IOException {
        if (!httpResponse.containsHeader("Content-Type")) {
            httpResponse.addHeader("Content-Type", mimeType);
        }

        IOUtils.copy(inputStream, httpResponse.getOutputStream());

        try {
            inputStream.close();
        } catch (Exception e) {
            log.error("failed to close input stream", e);
        }
    }


}