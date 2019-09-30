package com.arise.steiner.services;

import com.arise.steiner.config.ApplicationProperties;
import com.arise.steiner.config.AuthInterceptor;
import com.arise.steiner.entities.Owner;
import com.arise.steiner.entities.model.Permissions;
import com.arise.steiner.repository.OwnerRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AppStartup {

  private final OwnerRepository ownerRepository;

  private final ApplicationProperties applicationProperties;

  private static final Logger log = LoggerFactory.getLogger(AppStartup.class);

  public AppStartup(OwnerRepository ownerRepository, ApplicationProperties applicationProperties) {
    this.ownerRepository = ownerRepository;
    this.applicationProperties = applicationProperties;
    checkRootUser();
  }

  private void checkRootUser() {
    Optional<Owner> optionalOwner = ownerRepository.findById(applicationProperties.getUser());
    Owner root;
    if (!optionalOwner.isPresent()){
      root = new Owner();
      root.setPassword(applicationProperties.getPassword());
      root.setName(applicationProperties.getUser());

      Permissions p = new Permissions();
      p.setCreate(true);
      p.setUpdate(true);
      p.setDrop(true);
      p.setRead(true);

      root.setOwnersPermissions(p);
      log.info("creating root user " + root.getName());
    } else {
      root = optionalOwner.get();
      log.info("root user already exists");
    }

    //grant all privileges
    ownerRepository.save(root);
  }
}
