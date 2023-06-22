package com.edm.edmapi.controller;

import com.edm.api.AuthenticationApi;
import com.edm.model.dto.AuthenticationToken;
import com.edm.model.dto.UserCredentials;
import org.springframework.http.ResponseEntity;

public class AuthenticationController implements AuthenticationApi {

    @Override
    public ResponseEntity<AuthenticationToken> login(UserCredentials userCredentials) {
        return AuthenticationApi.super.login(userCredentials);
    }
}
