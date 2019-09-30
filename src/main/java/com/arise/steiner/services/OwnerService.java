package com.arise.steiner.services;

import com.arise.steiner.dto.User;
import com.arise.steiner.entities.Owner;
import com.arise.steiner.errors.EntityNotFoundException;
import com.arise.steiner.errors.SteinerException;

public interface OwnerService {

  Owner login(String name, String password) throws SteinerException;
  User loginUser(String name, String password) throws SteinerException;
  User findUserByName(String payload) throws SteinerException;
}
