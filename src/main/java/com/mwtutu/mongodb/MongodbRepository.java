package com.mwtutu.mongodb;
import com.mongodb.client.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
@Repository
public class MongodbRepository extends MongodbConfig{

    private final Logger logger = LoggerFactory.getLogger(MongodbRepository.class);


    public MongoCollection getCollection(String collection) {

        return this.mongoClient().getDatabase(this.getDatabaseName()).getCollection(collection);
    }
}
