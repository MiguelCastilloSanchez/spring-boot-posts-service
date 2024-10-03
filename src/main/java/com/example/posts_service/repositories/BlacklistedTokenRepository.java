package com.example.posts_service.repositories;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.posts_service.entities.blacklistedtokens.BlacklistedToken;

  
@Repository
public interface BlacklistedTokenRepository extends CrudRepository<BlacklistedToken,String> { 
  Optional<BlacklistedToken> findByToken(String token);
} 