package uz.edm.edmapi.constants;

import io.grpc.Metadata;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

public class Constants {
    public static final String BEARER = "Bearer";
    public static final Metadata.Key<String> AUTHORIZATION = Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER);

}