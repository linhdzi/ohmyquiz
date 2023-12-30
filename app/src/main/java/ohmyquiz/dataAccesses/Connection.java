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

    public static MongoClient  createConnection() {
        // Tạo kết nối tới MongoDB
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
                .build();
        MongoClient mongoClient = MongoClients.create(settings);

        // Chọn cơ sở dữ liệu
        // MongoDatabase database = mongoClient.getDatabase("OhMyQuiz");
        // MongoCollection<Document> collection = database.getCollection("Quiz");

        return mongoClient;
    } 
    public static void closeConnection(MongoClient mongoClient){
        mongoClient.close();
    }
}