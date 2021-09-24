package com.valeryk.methodchaining;

import java.net.URL;

public class ClientBuilder {

    public ClientBuilder(String url) {
    }

    public static ClientBuilder create(String url) {
        return new ClientBuilder(url);
    }

   // private final URL url;

   // public Client build() {

}
