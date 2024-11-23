package banquemisr.challenge05.taskmanagement.dto;

public class AuthDTO {
    private  String jwt;
    private  String refreshToken;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public AuthDTO(String jwt, String refreshToken) {

        this.jwt = jwt;
        this.refreshToken = refreshToken;
    }

}
