package com.nutrymaco.jobsite.util.jwt;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.util.Utils;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class GoogleJWTUtil {
    String token;
    Payload payload;
    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(Utils.getDefaultTransport(), Utils.getDefaultJsonFactory())
            .build();

    public GoogleJWTUtil (String token) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = verifier.verify(token);
        if (idToken == null) {
            throw new IllegalStateException();
        }
        this.token = token;
        this.payload = idToken.getPayload();
    }

    public String getSub() {
        return payload.getSubject();
    }

    public String getIssuer() {
        return payload.getIssuer();
    }

    public String getSurname() {
        return (String) payload.get("family_name");
    }

    public String getName() {
        return (String) payload.get("name");
    }
}

