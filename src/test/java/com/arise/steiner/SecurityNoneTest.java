package com.arise.steiner;

import com.arise.steiner.client.SecurityMode;
import com.arise.steiner.client.SteinerAPIClient;
import com.arise.steiner.client.NodeDTO;
import com.arise.steiner.client.ItemDTO;
import com.arise.steiner.config.ApplicationProperties;
import com.arise.steiner.dto.CreateNodeRequest;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;


public class SecurityNoneTest extends IntegrationTest {



    public void testUpload(SteinerAPIClient client, Long documentId){
        ItemDTO uploadResponse = client.uploadDocument(new byte[]{1, 2, 3}, documentId, "testfile.txt", "test_user", "test_domain");

        Assert.assertNotNull(uploadResponse.getPath());
        Assert.assertNotNull(uploadResponse.getUserDomain());
        Assert.assertNotNull(uploadResponse.getUserId());

        Assert.assertEquals("test_user", uploadResponse.getUserId());
        Assert.assertEquals("test_domain", uploadResponse.getUserDomain());
        Assert.assertNotNull(uploadResponse.getId());
        Assert.assertEquals("text/plain", uploadResponse.getMimeType());
    }





    @Override
    public SecurityMode getSecurityMode() {
        return SecurityMode.NONE;
    }
}
