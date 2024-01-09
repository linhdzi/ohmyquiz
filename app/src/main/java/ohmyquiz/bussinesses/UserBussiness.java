package ohmyquiz.bussinesses;

import ohmyquiz.dataAccesses.UsersDataAccess;
import ohmyquiz.models.User;

public class UserBussiness {
    UsersDataAccess usersDataAccess = new UsersDataAccess();

    public boolean createUser(User user, String Role) {
        var result = usersDataAccess.createUser(user,Role);
        return result;
    }

    public boolean getByUsernamePassword(String username, String password) {
        var result = usersDataAccess.getByUsernamePassword(username, password);
        return result;
    }
}
