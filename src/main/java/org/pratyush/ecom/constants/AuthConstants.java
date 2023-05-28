package org.pratyush.ecom.constants;

public interface AuthConstants {
    String ROLE = "role";
    String BEARER = "Bearer ";
    String[] OPEN_PATHS = new String[] {
            UrlPath.HEALTH,
            UrlPath.USER + UrlPath.LOGIN,
            UrlPath.USER + UrlPath.TOKEN_GENERATE,
            UrlPath.USER + UrlPath.REGISTER
    };
}
