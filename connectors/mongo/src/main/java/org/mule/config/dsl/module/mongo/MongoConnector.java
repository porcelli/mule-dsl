/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.module.mongo;

import org.mule.config.dsl.ExpressionEvaluatorDefinition;
import org.mule.config.dsl.module.mongo.internal.*;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.api.MongoClient;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class MongoConnector {

    private MongoCloudConnector object;

    public MongoConnector() {
        object = new MongoCloudConnector();
        object.setHost("localhost");
        object.setDatabase("test");
        object.setPort(27017);
    }

    public ExistsCollectionMessageProcessorDefinition listCollections() {
        return new ListCollectionsMessageProcessorBuilder(object);
    }

    public ExistsCollectionMessageProcessorDefinition existsCollection(String collection) {
        checkNotEmpty(collection, "collection");
        return new ExistsCollectionMessageProcessorBuilder(object, collection);
    }

    public ExistsCollectionMessageProcessorDefinition existsCollection(ExpressionEvaluatorDefinition collectionExp) {
        checkNotNull(collectionExp, "collection expression");
        return new ExistsCollectionMessageProcessorBuilder(object, collectionExp);
    }

    public DropCollectionMessageProcessorDefinition dropCollection(String collection) {
        checkNotEmpty(collection, "collection");
        return new DropCollectionMessageProcessorBuilder(object, collection);
    }

    public DropCollectionMessageProcessorDefinition dropCollection(ExpressionEvaluatorDefinition collectionExp) {
        checkNotNull(collectionExp, "collection expression");
        return new DropCollectionMessageProcessorBuilder(object, collectionExp);
    }

    public CreateCollectionMessageProcessorDefinition createCollection(String collection) {
        checkNotEmpty(collection, "collection");
        return new CreateCollectionMessageProcessorBuilder(object, collection);
    }

    public CreateCollectionMessageProcessorDefinition createCollection(ExpressionEvaluatorDefinition collectionExp) {
        checkNotNull(collectionExp, "collection expression");
        return new CreateCollectionMessageProcessorBuilder(object, collectionExp);
    }

    public InsertObjectMessageProcessorDefinition insertObject(String collection) {
        checkNotEmpty(collection, "collection");
        return new InsertObjectMessageProcessorBuilder(object, collection);
    }

    public InsertObjectMessageProcessorDefinition insertObject(ExpressionEvaluatorDefinition collectionExp) {
        checkNotNull(collectionExp, "collection expression");
        return new InsertObjectMessageProcessorBuilder(object, collectionExp);
    }

    public UpdateObjectsMessageProcessorDefinition updateObjects(String collection) {
        checkNotEmpty(collection, "collection");
        return new UpdateObjectsMessageProcessorBuilder(object, collection);
    }

    public UpdateObjectsMessageProcessorDefinition updateObjects(ExpressionEvaluatorDefinition collectionExp) {
        checkNotNull(collectionExp, "collection expression");
        return new UpdateObjectsMessageProcessorBuilder(object, collectionExp);
    }

    public SaveObjectMessageProcessorDefinition saveObject(String collection) {
        checkNotEmpty(collection, "collection");
        return new SaveObjectMessageProcessorBuilder(object, collection);
    }

    public SaveObjectMessageProcessorDefinition saveObject(ExpressionEvaluatorDefinition collectionExp) {
        checkNotNull(collectionExp, "collection expression");
        return new SaveObjectMessageProcessorBuilder(object, collectionExp);
    }

    public RemoveObjectsMessageProcessorDefinition removeObjects(String collection) {
        checkNotEmpty(collection, "collection");
        return new RemoveObjectsMessageProcessorBuilder(object, collection);
    }

    public RemoveObjectsMessageProcessorDefinition removeObjects(ExpressionEvaluatorDefinition collectionExp) {
        checkNotNull(collectionExp, "collection expression");
        return new RemoveObjectsMessageProcessorBuilder(object, collectionExp);
    }

    public MapReduceObjectsMessageProcessorDefinition mapReduceObjects(String collection, String mapFunction, String reduceFunction) {
        checkNotEmpty(collection, "collection");
        checkNotEmpty(mapFunction, "mapFunction");
        checkNotEmpty(reduceFunction, "reduceFunction");
        return new MapReduceObjectsMessageProcessorBuilder(object, collection, mapFunction, reduceFunction);
    }

    public MapReduceObjectsMessageProcessorDefinition mapReduceObjects(ExpressionEvaluatorDefinition collectionExp, String mapFunction, String reduceFunction) {
        checkNotNull(collectionExp, "collection expression");
        checkNotEmpty(mapFunction, "mapFunction");
        checkNotEmpty(reduceFunction, "reduceFunction");
        return new MapReduceObjectsMessageProcessorBuilder(object, collectionExp, mapFunction, reduceFunction);
    }

    public MapReduceObjectsMessageProcessorDefinition mapReduceObjects(String collection, ExpressionEvaluatorDefinition mapFunctionExp, String reduceFunction) {
        checkNotEmpty(collection, "collection");
        checkNotNull(mapFunctionExp, "mapFunction expression");
        checkNotEmpty(reduceFunction, "reduceFunction");
        return new MapReduceObjectsMessageProcessorBuilder(object, collection, mapFunctionExp, reduceFunction);
    }

    public MapReduceObjectsMessageProcessorDefinition mapReduceObjects(String collection, String mapFunction, ExpressionEvaluatorDefinition reduceFunctionExp) {
        checkNotEmpty(collection, "collection");
        checkNotEmpty(mapFunction, "mapFunction");
        checkNotNull(reduceFunctionExp, "reduceFunction expression");
        return new MapReduceObjectsMessageProcessorBuilder(object, collection, mapFunction, reduceFunctionExp);
    }

    public MapReduceObjectsMessageProcessorDefinition mapReduceObjects(ExpressionEvaluatorDefinition collectionExp, ExpressionEvaluatorDefinition mapFunctionExp, String reduceFunction) {
        checkNotNull(collectionExp, "collection expression");
        checkNotNull(mapFunctionExp, "mapFunction expression");
        checkNotEmpty(reduceFunction, "reduceFunction");
        return new MapReduceObjectsMessageProcessorBuilder(object, collectionExp, mapFunctionExp, reduceFunction);
    }

    public MapReduceObjectsMessageProcessorDefinition mapReduceObjects(ExpressionEvaluatorDefinition collectionExp, String mapFunction, ExpressionEvaluatorDefinition reduceFunctionExp) {
        checkNotNull(collectionExp, "collection expression");
        checkNotEmpty(mapFunction, "mapFunction");
        checkNotNull(reduceFunctionExp, "reduceFunction expression");
        return new MapReduceObjectsMessageProcessorBuilder(object, collectionExp, mapFunction, reduceFunctionExp);
    }

    public MapReduceObjectsMessageProcessorDefinition mapReduceObjects(String collection, ExpressionEvaluatorDefinition mapFunctionExp, ExpressionEvaluatorDefinition reduceFunctionExp) {
        checkNotEmpty(collection, "collection");
        checkNotNull(mapFunctionExp, "mapFunction expression");
        checkNotNull(reduceFunctionExp, "reduceFunction expression");
        return new MapReduceObjectsMessageProcessorBuilder(object, collection, mapFunctionExp, reduceFunctionExp);
    }

    public MapReduceObjectsMessageProcessorDefinition mapReduceObjects(ExpressionEvaluatorDefinition collectionExp, ExpressionEvaluatorDefinition mapFunctionExp, ExpressionEvaluatorDefinition reduceFunctionExp) {
        checkNotNull(collectionExp, "collection expression");
        checkNotNull(mapFunctionExp, "mapFunction expression");
        checkNotNull(reduceFunctionExp, "reduceFunction expression");
        return new MapReduceObjectsMessageProcessorBuilder(object, collectionExp, mapFunctionExp, reduceFunctionExp);
    }

    public CountObjectsMessageProcessorDefinition countObjects(String collection) {
        checkNotEmpty(collection, "collection");
        return new CountObjectsMessageProcessorBuilder(object, collection);
    }

    public CountObjectsMessageProcessorDefinition countObjects(ExpressionEvaluatorDefinition collectionExp) {
        checkNotNull(collectionExp, "collection expression");
        return new CountObjectsMessageProcessorBuilder(object, collectionExp);
    }

    public FindObjectsMessageProcessorDefinition findObjects(String collection) {
        checkNotEmpty(collection, "collection");
        return new FindObjectsMessageProcessorBuilder(object, collection);
    }

    public FindObjectsMessageProcessorDefinition findObjects(ExpressionEvaluatorDefinition collectionExp) {
        checkNotNull(collectionExp, "collection expression");
        return new FindObjectsMessageProcessorBuilder(object, collectionExp);
    }

    public FindOneObjectMessageProcessorDefinition findOneObject(String collection) {
        checkNotEmpty(collection, "collection");
        return new FindOneObjectMessageProcessorBuilder(object, collection);
    }

    public FindOneObjectMessageProcessorDefinition findOneObject(ExpressionEvaluatorDefinition collectionExp) {
        checkNotNull(collectionExp, "collection expression");
        return new FindOneObjectMessageProcessorBuilder(object, collectionExp);
    }

    public CreateIndexMessageProcessorDefinition createIndex(String collection, String field) {
        checkNotEmpty(collection, "collection");
        checkNotEmpty(field, "field");
        return new CreateIndexMessageProcessorBuilder(object, collection, field);
    }

    public CreateIndexMessageProcessorDefinition createIndex(ExpressionEvaluatorDefinition collectionExp, String field) {
        checkNotNull(collectionExp, "collection expression");
        checkNotEmpty(field, "field");
        return new CreateIndexMessageProcessorBuilder(object, collectionExp, field);
    }

    public CreateIndexMessageProcessorDefinition createIndex(String collection, ExpressionEvaluatorDefinition fieldExp) {
        checkNotEmpty(collection, "collection");
        checkNotNull(fieldExp, "field expression");
        return new CreateIndexMessageProcessorBuilder(object, collection, fieldExp);
    }

    public CreateIndexMessageProcessorDefinition createIndex(ExpressionEvaluatorDefinition collectionExp, ExpressionEvaluatorDefinition fieldExp) {
        checkNotNull(collectionExp, "collection expression");
        checkNotNull(fieldExp, "field expression");
        return new CreateIndexMessageProcessorBuilder(object, collectionExp, fieldExp);
    }

    public DropIndexMessageProcessorDefinition dropIndex(String collection, String index) {
        checkNotEmpty(collection, "collection");
        checkNotEmpty(index, "index");
        return new DropIndexMessageProcessorBuilder(object, collection, index);
    }

    public DropIndexMessageProcessorDefinition dropIndex(ExpressionEvaluatorDefinition collectionExp, String index) {
        checkNotNull(collectionExp, "collection expression");
        checkNotEmpty(index, "index");
        return new DropIndexMessageProcessorBuilder(object, collectionExp, index);
    }

    public DropIndexMessageProcessorDefinition dropIndex(String collection, ExpressionEvaluatorDefinition indexExp) {
        checkNotEmpty(collection, "collection");
        checkNotNull(indexExp, "index expression");
        return new DropIndexMessageProcessorBuilder(object, collection, indexExp);
    }

    public DropIndexMessageProcessorDefinition dropIndex(ExpressionEvaluatorDefinition collectionExp, ExpressionEvaluatorDefinition indexExp) {
        checkNotNull(collectionExp, "collection expression");
        checkNotNull(indexExp, "index expression");
        return new DropIndexMessageProcessorBuilder(object, collectionExp, indexExp);
    }

    public ListIndicesMessageProcessorDefinition listIndices(String collection) {
        checkNotEmpty(collection, "collection");
        return new ListIndicesMessageProcessorBuilder(object, collection);
    }

    public ListIndicesMessageProcessorDefinition listIndices(ExpressionEvaluatorDefinition collectionExp) {
        checkNotNull(collectionExp, "collection expression");
        return new ListIndicesMessageProcessorBuilder(object, collectionExp);
    }

    public MongoClient getClient() {
        return object.getClient();
    }

    public String getHost() {
        return object.getHost();
    }

    public int getPort() {
        return object.getPort();
    }

    public String getDatabase() {
        return object.getDatabase();
    }

    public String getPassword() {
        return object.getPassword();
    }

    public String getUsername() {
        return object.getUsername();
    }

    public void setClient(MongoClient client) {
        checkNotNull(client, "client");
        object.setClient(client);
    }

    public void setHost(String host) {
        checkNotEmpty(host, "host");
        object.setHost(host);
    }

    public void setPort(int port) {
        checkNotNull(port, "port");
        object.setPort(port);
    }

    public void setDatabase(String database) {
        checkNotEmpty(database, "database");
        object.setDatabase(database);
    }

    public void setPassword(String password) {
        checkNotEmpty(password, "password");
        object.setPassword(password);
    }

    public void setUsername(String username) {
        checkNotEmpty(username, "username");
        object.setUsername(username);
    }

    public void setClientAsNull() {
        object.setClient(null);
    }

    public void setHostAsNull() {
        object.setHost(null);
    }

    public void setDatabaseAsNull() {
        object.setDatabase(null);
    }

    public void setPasswordAsNull() {
        object.setPassword(null);
    }

    public void setUsernameAsNull() {
        object.setUsername(null);
    }
}
