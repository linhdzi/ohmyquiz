package ohmyquiz.bussinesses;

import java.time.LocalDateTime;

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

    public Boolean checkEmail(String email){
        var result = usersDataAccess.checkEmail(email);
        return result;
    }

    public void setVerificationCodeAndExpirationTime(String email,String token, LocalDateTime expirationTime){
        usersDataAccess.setVerificationCodeAndExpirationTime(email,token,expirationTime);
    }

    public Document checkToken(String token) {
        var result = usersDataAccess.checkToken(token);
        return result;
    }

    public Boolean resetPassword(String password, String email){
        var result= usersDataAccess.ResetPassword(password, email);
        return result;
    }
}
