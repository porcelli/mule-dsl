/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.module.mongo;

import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.module.mongo.internal.*;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.api.MongoClient;

public class MongoConnector {

    private MongoCloudConnector object;

    private MongoClient client = null;
    private String host = "localhost";
    private int port = 27017;
    private String database = "test";
    private String password = null;
    private String username = null;

    public MongoConnector() {
        object = new MongoCloudConnector();
    }

    public ExistsCollectionMessageProcessorDefinition listCollections() {
        return new ListCollectionsMessageProcessorBuilder(object);
    }

    public ExistsCollectionMessageProcessorDefinition existsCollection(String collection) {
        return new ExistsCollectionMessageProcessorBuilder(object, collection);
    }

    public ExistsCollectionMessageProcessorDefinition existsCollection(ExpressionEvaluatorBuilder collectionExp) {
        return new ExistsCollectionMessageProcessorBuilder(object, collectionExp);
    }

    public DropCollectionMessageProcessorDefinition dropCollection(String collection) {
        return new DropCollectionMessageProcessorBuilder(object, collection);
    }

    public DropCollectionMessageProcessorDefinition dropCollection(ExpressionEvaluatorBuilder collectionExp) {
        return new DropCollectionMessageProcessorBuilder(object, collectionExp);
    }

    public CreateCollectionMessageProcessorDefinition createCollection(String collection) {
        return new CreateCollectionMessageProcessorBuilder(object, collection);
    }

    public CreateCollectionMessageProcessorDefinition createCollection(ExpressionEvaluatorBuilder collectionExp) {
        return new CreateCollectionMessageProcessorBuilder(object, collectionExp);
    }

    public InsertObjectMessageProcessorDefinition insertObject(String collection) {
        return new InsertObjectMessageProcessorBuilder(object, collection);
    }

    public InsertObjectMessageProcessorDefinition insertObject(ExpressionEvaluatorBuilder collectionExp) {
        return new InsertObjectMessageProcessorBuilder(object, collectionExp);
    }

    public UpdateObjectsMessageProcessorDefinition updateObjects(String collection) {
        return new UpdateObjectsMessageProcessorBuilder(object, collection);
    }

    public UpdateObjectsMessageProcessorDefinition updateObjects(ExpressionEvaluatorBuilder collectionExp) {
        return new UpdateObjectsMessageProcessorBuilder(object, collectionExp);
    }

    public SaveObjectMessageProcessorDefinition saveObject(String collection) {
        return new SaveObjectMessageProcessorBuilder(object, collection);
    }

    public SaveObjectMessageProcessorDefinition saveObject(ExpressionEvaluatorBuilder collectionExp) {
        return new SaveObjectMessageProcessorBuilder(object, collectionExp);
    }

    public RemoveObjectsMessageProcessorDefinition removeObjects(String collection) {
        return new RemoveObjectsMessageProcessorBuilder(object, collection);
    }

    public RemoveObjectsMessageProcessorDefinition removeObjects(ExpressionEvaluatorBuilder collectionExp) {
        return new RemoveObjectsMessageProcessorBuilder(object, collectionExp);
    }

    public MapReduceObjectsMessageProcessorDefinition mapReduceObjects(String collection, String mapFunction, String reduceFunction) {
        return new MapReduceObjectsMessageProcessorBuilder(object, collection, mapFunction, reduceFunction);
    }

    public MapReduceObjectsMessageProcessorDefinition mapReduceObjects(ExpressionEvaluatorBuilder collectionExp, String mapFunction, String reduceFunction) {
        return new MapReduceObjectsMessageProcessorBuilder(object, collectionExp, mapFunction, reduceFunction);
    }

    public MapReduceObjectsMessageProcessorDefinition mapReduceObjects(String collection, ExpressionEvaluatorBuilder mapFunctionExp, String reduceFunction) {
        return new MapReduceObjectsMessageProcessorBuilder(object, collection, mapFunctionExp, reduceFunction);
    }

    public MapReduceObjectsMessageProcessorDefinition mapReduceObjects(String collection, String mapFunction, ExpressionEvaluatorBuilder reduceFunctionExp) {
        return new MapReduceObjectsMessageProcessorBuilder(object, collection, mapFunction, reduceFunctionExp);
    }

    public MapReduceObjectsMessageProcessorDefinition mapReduceObjects(ExpressionEvaluatorBuilder collectionExp, ExpressionEvaluatorBuilder mapFunctionExp, String reduceFunction) {
        return new MapReduceObjectsMessageProcessorBuilder(object, collectionExp, mapFunctionExp, reduceFunction);
    }

    public MapReduceObjectsMessageProcessorDefinition mapReduceObjects(ExpressionEvaluatorBuilder collectionExp, String mapFunction, ExpressionEvaluatorBuilder reduceFunctionExp) {
        return new MapReduceObjectsMessageProcessorBuilder(object, collectionExp, mapFunction, reduceFunctionExp);
    }

    public MapReduceObjectsMessageProcessorDefinition mapReduceObjects(String collection, ExpressionEvaluatorBuilder mapFunctionExp, ExpressionEvaluatorBuilder reduceFunctionExp) {
        return new MapReduceObjectsMessageProcessorBuilder(object, collection, mapFunctionExp, reduceFunctionExp);
    }

    public MapReduceObjectsMessageProcessorDefinition mapReduceObjects(ExpressionEvaluatorBuilder collectionExp, ExpressionEvaluatorBuilder mapFunctionExp, ExpressionEvaluatorBuilder reduceFunctionExp) {
        return new MapReduceObjectsMessageProcessorBuilder(object, collectionExp, mapFunctionExp, reduceFunctionExp);
    }

    public MongoClient getClient() {
        return client;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setClient(MongoClient client) {
        this.client = client;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
