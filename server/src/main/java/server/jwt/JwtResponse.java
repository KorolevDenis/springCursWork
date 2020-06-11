package server.jwt;

import java.io.Serializable;
public class JwtResponse implements Serializable {

    private String token;

    public JwtResponse() {
    }

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}

