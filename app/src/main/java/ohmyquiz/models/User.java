package ohmyquiz.models;

import java.util.Map;
import java.util.HashMap;

public class User {
    private String guid;
    private String name;
    private String password;
    private String email;
    private String createdAt;
    private String updatedAt;
    private String country;

    public static Map<String, String> role = new HashMap<>();
    static {
        role.put("Learner", "learner");
        role.put("Trainer", "trainer");
    }

    public static Map<String, String> gender = new HashMap<>();
    static {
        gender.put("Male", "male");
        gender.put("Female", "female");
        gender.put("Other", "prefer not to disclose");
    }

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
