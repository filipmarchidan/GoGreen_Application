package API.messages;

public class LogInRequest {


    private String userName;
    private String passWord;

    public LogInRequest(String user,String pass){
        userName = user;
        passWord = pass;
    }


    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

}
