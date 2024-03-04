package uz.edm.edmapi.grpc;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uz.edm.edmapi.constants.Constants;
import uz.edm.edmapi.model.IndexerAuthTokenHolder;

import java.util.concurrent.Executor;


@Slf4j
@RequiredArgsConstructor
public class JwtTokenSender extends CallCredentials {

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier) {
        executor.execute(() -> {
            try {
                Metadata headers = new Metadata();
                headers.put(Constants.CONST_AUTHORIZATION_METADATA, Constants.CONST_BEARER + IndexerAuthTokenHolder.jwtToken);
                metadataApplier.apply(headers);
            } catch (Throwable e) {
                metadataApplier.fail(Status.UNAUTHENTICATED.withCause(e));
            }
        });
    }

    @Override
    public void thisUsesUnstableApi() {
        // noop
    }
}