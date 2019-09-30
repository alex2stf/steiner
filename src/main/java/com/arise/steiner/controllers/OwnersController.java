package com.arise.steiner.controllers;

import com.arise.steiner.client.LoginRequest;
import com.arise.steiner.entities.Owner;
import com.arise.steiner.errors.SteinerException;
import com.arise.steiner.names.Routes;
import com.arise.steiner.services.OwnerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OwnersController {

  OwnerService ownerService;

  @PostMapping(Routes.OWNERS)
  public void createOwner(Owner owner){

  }



}
