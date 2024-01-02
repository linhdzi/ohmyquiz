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
    {
        role.put("learner", "learner");
        role.put("trainer", "trainer");
    }
    public static Map<String, String> gender = new HashMap<>();
    {
        gender.put("male", "male");
        gender.put("female", "female");
        gender.put("other", "prefer not to disclose");
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
