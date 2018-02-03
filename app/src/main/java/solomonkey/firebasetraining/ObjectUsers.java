package solomonkey.firebasetraining;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by arvin on 12/6/2017.
 */

public class ObjectUsers {

    public String email;
    public String age;
    public String name;

    public Map<String,String> map = new HashMap<>();

    public ObjectUsers() {//empty constructor
    }

    public ObjectUsers(String email, String age, String name) {
        this.email = email;
        this.age = age;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();

        result.put("Email",email);
        result.put("Age",age);
        result.put("Name",name);

        return result;
    }
}
