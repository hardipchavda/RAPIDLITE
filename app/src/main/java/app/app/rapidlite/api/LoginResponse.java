package app.app.rapidlite.api;

public class LoginResponse {

  private LoginStatus LoginStatus;

    public app.app.rapidlite.api.LoginStatus getLoginStatus() {
        return LoginStatus;
    }

    public void setLoginStatus(app.app.rapidlite.api.LoginStatus loginStatus) {
        LoginStatus = loginStatus;
    }
}
