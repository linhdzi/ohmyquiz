package ohmyquiz.dataAccesses;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;

public class Connection {

    public static MongoClient createConnection() {

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient;
    }

    public static void closeConnection(MongoClient mongoClient) {
        mongoClient.close();
    }

    public static MongoCollection<Document> collection(String collectionName) {
        MongoDatabase database = createConnection().getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection(collectionName);
        closeConnection(createConnection());
        return collection;
    }

}
