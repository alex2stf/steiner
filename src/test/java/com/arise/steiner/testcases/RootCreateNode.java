package com.arise.steiner.testcases;

import com.arise.steiner.IntegrationTest;
import com.arise.steiner.client.NodeDTO;
import com.arise.steiner.client.SteinerAPIClient;
import com.arise.steiner.client.CreateNodeRequest;
import org.junit.Assert;

public class RootCreateNode extends AbstractTestCase {


  public RootCreateNode(IntegrationTest test) {
    super(test);
  }

  @Override
  protected Boolean executeTest() {


    SteinerAPIClient steinerAPIClient = test.getClient("root", "root");

    CreateNodeRequest createNodeRequest = new CreateNodeRequest();
    createNodeRequest.setType("document_type");
    createNodeRequest.setDescription("node_description");
    createNodeRequest.setReason("some_reason");
    createNodeRequest.setProduct("mortgage");
    createNodeRequest.setProductId(345L);
    createNodeRequest.setSource("trusted_source");
    createNodeRequest.setStatus("SOME_STATUS");
    NodeDTO nodeDTO = steinerAPIClient.createNode(createNodeRequest);

    Assert.assertEquals("document_type", nodeDTO.getType() );
    Assert.assertEquals("some_reason", nodeDTO.getReason() );
    Assert.assertEquals("some_reason", nodeDTO.getReason() );
    Assert.assertEquals("mortgage", nodeDTO.getProduct() );
    Assert.assertEquals(345L, nodeDTO.getProductId().longValue() );
    Assert.assertEquals("node_description", nodeDTO.getDescription());
    Assert.assertEquals("trusted_source", nodeDTO.getSource());
    Assert.assertEquals("SOME_STATUS", nodeDTO.getStatus());
    Assert.assertNotNull(nodeDTO.getId()); //valid id generated
    Assert.assertNull(nodeDTO.getCurrentItem()); //no items attached
    return true;
  }
}
