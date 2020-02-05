package com.example.mockito.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;

@Data
@Builder
@NoArgsConstructor
public class AuthInfo {
    private String grant_type;
    private String client_id;
    private String client_secret;
    private String username;
    private String password;

    public AuthInfo(String grant_type, String client_id, String client_secret, String username, String password) {
        this.grant_type = grant_type;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.username = username;
        this.password = password;
    }

//    public AuthInfo retrieveAuthInfo(){
//        AuthInfo authInfo = new AuthInfo("password", "3MVG9G9pzCUSkzZvucD1Q_.bNfx7RyJE1iKS8eWQbw4oHElPb24n79wJ_M8W2M3ByZdme2jWun.VM7O6n2jCt",
//                "6DB41AF9E52DEF798C7AF8422B9840DDEA7DAE06A84067FC3EB8E99EE6D1A118", "parul.shree@sacumen.com", "Sacumen@1315");
//        return authInfo;
//    }

    public AuthInfo retrieveAuthInfoExceptClientId(){
        AuthInfo authInfo = new AuthInfo();
        return authInfo;
    }
}
