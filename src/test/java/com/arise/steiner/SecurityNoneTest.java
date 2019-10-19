package com.arise.steiner;

import com.arise.steiner.client.SecurityMode;
import com.arise.steiner.client.SteinerAPIClient;
import com.arise.steiner.client.ItemDTO;
import org.junit.Assert;
//import org.springframework.boot.context.embedded.LocalServerPort;


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
