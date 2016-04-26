/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.sergei.replication.replication;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import static java.util.Arrays.asList;

/**
 * connect to primary node
 * e.g. >mongo --port 27019
 * run >rs.stepDown()
 * see how driver will connect to new elected primary node
 */
public class ReplicaSetFailoverTest {
    public static void main(String[] args) throws InterruptedException {
        MongoClient client = new MongoClient(asList(new ServerAddress("localhost", 27017),
                                                    new ServerAddress("localhost", 27018),
                                                    new ServerAddress("localhost", 27019)));

        MongoCollection<Document> collection = client.getDatabase("course").getCollection("replication");
        collection.drop();

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            try {
                collection.insertOne(new Document("_id", new ObjectId()).append("i", i));
                System.out.println("Inserted document: " + i);
            } catch (MongoException e) {
                System.out.println("Exception inserting document " + i + ": " + e.getMessage());
            }
            Thread.sleep(500);
        }
    }
}
