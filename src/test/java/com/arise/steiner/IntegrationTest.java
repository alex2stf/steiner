package com.arise.steiner;

import com.arise.steiner.client.SecurityMode;
import com.arise.steiner.client.SteinerAPIClient;
import com.arise.steiner.config.ApplicationProperties;
import com.arise.steiner.entities.Owner;
import com.arise.steiner.repository.OwnerRepository;
import com.arise.steiner.testcases.RootCreateNode;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
    "application.user=root",
    "application.password=root",
})
public abstract class IntegrationTest {


  @LocalServerPort
  private int port;


  @Autowired
  ApplicationProperties applicationProperties;




  public int getPort() {
    return port;
  }


  public abstract SecurityMode getSecurityMode();

  @Autowired
  OwnerRepository ownerRepository;

  @Before
  public void setupUsers(){
    Owner owner = new Owner();
    owner.setName("TEST1");
    owner.setPassword("TEST1_PASS");
    ownerRepository.save(owner);

  }

  public SteinerAPIClient getClient(String user, String password){

//    if (SecurityMode.NONE.equals(getSecurityMode()) || SecurityMode.BASIC_AUTH.equals(getSecurityMode()) ){
      return new SteinerAPIClient("http://localhost:" + getPort() + "/api")
          .security(getSecurityMode())
          .auth(user, password);
//    }


  }


  public Supplier<Boolean>[] tests(){
    return new Supplier[]{
        new RootCreateNode(this)
    };
  }


  @Test
  public void startIntegrationTests(){
    Supplier [] suppliers = tests();

    CompletableFuture items[] = new CompletableFuture[suppliers.length];
    for (int i = 0; i < items.length; i++){
      items[i] = CompletableFuture.supplyAsync(suppliers[i]);
    }
    try {
      CompletableFuture.allOf(items).get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }
}
