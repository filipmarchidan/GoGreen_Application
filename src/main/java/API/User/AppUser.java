package API.User;

public class AppUser {
    private Long userId;
    private String user;
    private String pass;

    public AppUser() {

    }

    public AppUser(Long userId, String user, String pass) {
        this.userId = userId;
        this.user = user;
        this.pass = pass;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return user;
    }

    public void setUserName(String userName) {
        this.user = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return this.user + "/" + this.pass;
    }
}
