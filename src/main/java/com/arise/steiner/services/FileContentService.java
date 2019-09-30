package com.arise.steiner.services;

import com.arise.steiner.entities.Item;
import com.arise.steiner.entities.Node;
import com.arise.steiner.dto.UploadResponse;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class FileContentService {



    @Autowired
    protected HistoryService historyService;


    /**
     *
     * @param item
     * @param node
     * @param inputStream
     * @param filename
     * @return
     * @throws Exception
     */
    protected abstract UploadResponse performUpload(Item item, Node node, InputStream inputStream, String filename)
        throws Exception;


    public final UploadResponse upload(Item item, Node node, InputStream inputStream, String filename)
        throws Exception {
        item.setServiceId(this.getServiceId());
        UploadResponse responseDTO = performUpload(item, node, inputStream, filename);

        historyService.logUpload(node);
        return responseDTO;
    }


    protected abstract String getServiceId();

    public abstract void solve(HttpServletResponse httpResponse, HttpServletRequest httpRequest, Item itemEntity) throws Exception;
}
