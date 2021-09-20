package com.valeryk.valueobjects.request;

public class Authentication {

    private String username;
    private String password;
    private String token;

    private Authentication(){}

    private Authentication(AuthenticationBuilder builder){
        this.username = builder.username;
        this.password = builder.password;
    }

    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }

    public static class AuthenticationBuilder {
        private String username;
        private String password;


        public AuthenticationBuilder username(String username) {
            this.username = username;
            return this;
        }

        public AuthenticationBuilder password(String password) {
            this.password = password;
            return this;
        }

        public Authentication build() {
            Authentication authentication = new Authentication(this);
            return authentication;
        }

    }
}
