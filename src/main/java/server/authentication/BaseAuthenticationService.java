package server.authentication;


import server.models.User;

import java.util.ArrayList;
import java.util.List;

public class BaseAuthenticationService implements AuthenticationService {

    private static final List<User> clients = List.of(
            new User("Martin", "11111", "Martin_cat"),
            new User("Batman", "22222", "Bruce_wain"),
            new User("Gena", "33333", "Gray_Gandalf")
    );

    @Override
    public String getUsernameByLoginAndPassword(String login, String password) {
        for (User client : clients) {
            if(client.getLogin().equals(login) && client.getPassword().equals(password)){
                return client.getUsername();
            }
        }
        return null;
    }

    @Override
    public void createUser(String login, String password, String username) {

    }

    @Override
    public Boolean checkLoginByFree(String login) {
        return null;
    }

    @Override
    public void startAuthentication() {
        System.out.println("Start Authentication");
    }

    @Override
    public void endAuthentication() {
        System.out.println("End Authentication");
    }
}
