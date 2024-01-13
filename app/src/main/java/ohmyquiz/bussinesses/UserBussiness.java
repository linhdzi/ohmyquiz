package ohmyquiz.bussinesses;

import org.bson.Document;

import ohmyquiz.dataAccesses.UsersDataAccess;
import ohmyquiz.models.User;

public class UserBussiness {
    UsersDataAccess usersDataAccess = new UsersDataAccess();

    public boolean createUser(User user, String Role) {
        var result = usersDataAccess.createUser(user,Role);
        return result;
    }

    public Document getByUsernamePassword(String username, String password) {
        var result = usersDataAccess.getByUsernamePassword(username, password);
        return result;
    }
}
