package ohmyquiz.dataAccesses;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.mindrot.jbcrypt.BCrypt;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.time.LocalDateTime;
import org.bson.BsonTimestamp;
import ohmyquiz.models.User;

public class UsersDataAccess {
    public boolean createUser(User user, String Role) {
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("User");

        // check exist of name, email
        long usernameCount = collection.countDocuments(Filters.eq("name", user.getName()));
        long emailCount = collection.countDocuments(Filters.eq("email", user.getEmail()));

        if (usernameCount > 0 || emailCount > 0) {
            connection.close();
            return false;
        } else {

            Document userDocument = new Document()
                    .append("guid", user.getGuid())
                    .append("name", user.getName())
                    .append("email", user.getEmail())
                    .append("password", user.getPassword())
                    .append("createdAt", new BsonTimestamp())
                    .append("role", Role);

            try {
                collection.insertOne(userDocument);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                connection.close();
            }
        }
    }

    public Document getByUsernamePassword(String username, String password) {
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
                connection.close();
                return result;
            } else {
                connection.close();
                return null;
            }
        } else {
            connection.close();
            ;
            return null;
        }
    }

    public boolean checkEmail(String email) {
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("User");

        Bson filter = Filters.eq("email", email);
        Document result = collection.find(filter).first();

        if (result != null) {
            connection.close();
            return true;
        } else {
            connection.close();
            return false;
        }

    }

    public void setVerificationCodeAndExpirationTime(String email, String token, LocalDateTime expirationTime) {
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("User");

        Bson filter = Filters.eq("email", email);

        Bson update = Updates.combine(
                Updates.set("expirationTime", expirationTime),
                Updates.set("verificationCode", token));

        collection.updateOne(filter, update);
        connection.close();
    }

    public Document checkToken(String token) {
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("User");

        Bson filter = Filters.and(
                Filters.eq("verificationCode", token),
                Filters.gt("expirationTime", new java.util.Date()));

        Document result = collection.find(filter).first();

        if (result != null) {
            connection.close();
            return result;
        } else {
            connection.close();
            return null;
        }
    }

    public boolean ResetPassword(String password, String email) {
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("User");

        Bson filter = Filters.eq("email", email);
        
        Bson update = Updates.combine(
                Updates.set("password", password),
                Updates.set("verificationCode",null),
                Updates.set("updatedAt", new BsonTimestamp()));
        try {
            collection.updateOne(filter, update);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            connection.close();
        }

    }
}
