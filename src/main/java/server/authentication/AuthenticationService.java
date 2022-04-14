package server.authentication;

public interface AuthenticationService {
    String getUsernameByLoginAndPassword(String login, String password);

//    void createUser(String login, String password, String username);
//    Boolean checkLoginByFree(String login);
    void startAuthentication();
    void endAuthentication();
}
