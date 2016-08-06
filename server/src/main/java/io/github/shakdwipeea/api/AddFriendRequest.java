package io.github.shakdwipeea.api;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * Created by akash on 6/15/16.
 */
public class AddFriendRequest {
    @NotNull
    private String name;

    private ArrayList<String> friends;

    public AddFriendRequest(String name, ArrayList<String> friends) {
        this.name = name;
        this.friends = friends;
    }

    public AddFriendRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }
}
