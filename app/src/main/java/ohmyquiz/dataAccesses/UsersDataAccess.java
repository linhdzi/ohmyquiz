package ohmyquiz.dataAccesses;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.mindrot.jbcrypt.BCrypt;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.BsonTimestamp;

import ohmyquiz.models.User;

public class UsersDataAccess {
    public boolean createUser(User user) {
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("User");

        // check exist of name, email
        long usernameCount = collection.countDocuments(Filters.eq("name", user.getName()));
        long emailCount = collection.countDocuments(Filters.eq("email", user.getEmail()));

        if (usernameCount > 0 || emailCount > 0) {
            Connection.closeConnection(connection);
            return false;
        } else {
            Document userDocument = new Document()
                    .append("guid", user.getGuid())
                    .append("name", user.getName())
                    .append("email", user.getEmail())
                    .append("password", user.getPassword())
                    .append("createdAt", new BsonTimestamp())
                    .append("role", user.getRole());
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

    public boolean getByUsernamePassword(String username, String password) {
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("User");

        Bson filter = Filters.or(
                Filters.eq("name", username),
                Filters.eq("email", username));

        Document result = collection.find(filter).first();

        if (result != null) {
            String passwordFromDB = result.getString("password");
            boolean checkPassword = BCrypt.checkpw(password, passwordFromDB);
            if (checkPassword) {
                Connection.closeConnection(connection);
                return true;
            } else {
                Connection.closeConnection(connection);
                return false;
            }
        } else {
            Connection.closeConnection(connection);
            return false;
        }

    }
}