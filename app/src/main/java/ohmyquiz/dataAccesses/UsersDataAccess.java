package ohmyquiz.dataAccesses;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonTimestamp;
import java.util.UUID;

import ohmyquiz.models.User;

public class UsersDataAccess {
    public boolean createUser(User user) {
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("User");

        String guid = UUID.randomUUID().toString();

        Document userDocument = new Document()
                .append("guid", guid)
                .append("name", user.getName())
                .append("email", user.getEmail())
                .append("password", user.getPassword())
                .append("createdAt", new BsonTimestamp())
                .append("updatedAt", null)
                .append("role", "learner");

        try {
            collection.insertOne(userDocument);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Connection.closeConnection(connection);
        }

    }
}