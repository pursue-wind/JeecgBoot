package org.jeecg.config.security.social;

import org.jeecg.config.security.LoginType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Map;

/**
 * @author EightMonth
 * @date 2024/1/1
 */
public class SocialGrantAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    public SocialGrantAuthenticationToken(Authentication clientPrincipal, Map<String, Object> additionalParameters) {
        super(new AuthorizationGrantType(LoginType.SOCIAL), clientPrincipal, additionalParameters);
    }

}