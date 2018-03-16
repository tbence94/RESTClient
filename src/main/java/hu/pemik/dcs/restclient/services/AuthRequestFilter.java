package hu.pemik.dcs.restclient.services;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Arrays;

public class AuthRequestFilter implements ClientRequestFilter {

    private final String userName;

    public AuthRequestFilter(String userName) {
        this.userName = userName;
    }

    @Override
    public void filter(ClientRequestContext crc) throws IOException {
        crc.getHeaders().put(HttpHeaders.AUTHORIZATION, Arrays.asList("token " + userName));
        crc.getHeaders().put(HttpHeaders.ACCEPT, Arrays.asList("application/json", "text/plain"));
    }
}