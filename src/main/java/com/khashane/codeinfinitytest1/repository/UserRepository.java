package com.khashane.codeinfinitytest1.repository;

import com.khashane.codeinfinitytest1.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository <User, String> {

}

