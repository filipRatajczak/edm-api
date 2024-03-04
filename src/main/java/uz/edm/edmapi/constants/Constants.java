package uz.edm.edmapi.constants;

import io.grpc.Metadata;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

public class Constants {
    public static final String CONST_BEARER = "Bearer ";
    public static final Metadata.Key<String> CONST_AUTHORIZATION_METADATA = Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER);
    public static final String CONST_AUTHORIZATION= "Authorization";
    public static final String CONST_LOGIN_PATH = "/api/v1/login";
    public static final String CONST_SCHEDULE_PATH = "/api/v1/schedule";


}
