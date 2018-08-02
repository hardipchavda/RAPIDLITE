package app.app.rapidlite.api;

public class LoginStatus {

    private String Status,username,Saasid;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSaasid() {
        return Saasid;
    }

    public void setSaasid(String saasid) {
        Saasid = saasid;
    }
}
