package com.mwtutu.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mwtutu.utils.Debug;
import com.mwtutu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongodbConfig {

    private final Logger logger = LoggerFactory.getLogger(MongodbConfig.class);

    @Value("${mongodb.addresses}")
    private String addresses;
    @Value("${mongodb.username}")
    private String username;
    @Value("${mongodb.password}")
    private String password;
    @Value("${mongodb.admindb}")
    private String admindb;
    @Value("${mongodb.database}")
    private String database;

    MongoClient mongoClient;

    public MongoClient mongoClient() {
        if (StringUtil.isBlank(mongoClient)) {
            List<ServerAddress> addressList = new ArrayList<ServerAddress>();
            String[] addressArray = StringUtil.split(addresses, ",");
            for (String addressStr : addressArray) {
                String address = StringUtil.getInTextBefore(addressStr, ":");
                int port = StringUtil.getInteger(StringUtil.getInTextAfter(addressStr, ":"));
                Debug.printlnInfo("mongo:" + address + ":" + port);
                addressList.add(new ServerAddress(address, port));
            }
            MongoCredential credential = MongoCredential.createScramSha1Credential(username, admindb, password.toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);
            mongoClient = new MongoClient(addressList, credentials);
        }
        return mongoClient;
    }

    public String getDatabaseName() {
        return database;
    }
}
