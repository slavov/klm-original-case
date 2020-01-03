package com.afkl.cases.df.service;

import com.afkl.cases.df.model.dto.OauthTokenResponse;

public interface OauthService {

    OauthTokenResponse requestOauthToken(String clientId, String secret);
}
