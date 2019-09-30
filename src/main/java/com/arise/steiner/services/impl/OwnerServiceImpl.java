package com.arise.steiner.services.impl;

import com.arise.steiner.dto.User;
import com.arise.steiner.entities.Owner;
import com.arise.steiner.errors.EntityNotFoundException;
import com.arise.steiner.errors.InvalidCredentials;
import com.arise.steiner.errors.SteinerException;
import com.arise.steiner.repository.OwnerRepository;
import com.arise.steiner.services.OwnerService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceImpl implements OwnerService {

  final OwnerRepository repository;

  public OwnerServiceImpl(OwnerRepository repository) {
    this.repository = repository;
  }

  @Override
  public Owner login(String name, String password) throws SteinerException {
    Optional<Owner> optionalOwner = repository.findById(name);
    if (!optionalOwner.isPresent()){
      throw new EntityNotFoundException(Owner.class);
    }
    if (password.equals(optionalOwner.get().getPassword())){
      return optionalOwner.get();
    }

    throw new InvalidCredentials(Owner.class);
  }

  @Override
  public User loginUser(String name, String password) throws SteinerException {
    return new User(login(name, password));
  }

  @Override
  public User findUserByName(String name) throws SteinerException {
    Optional<Owner> owner = repository.findById(name);
    if (!owner.isPresent()){
      throw new EntityNotFoundException(Owner.class);
    }
    return new User(owner.get());
  }
}
