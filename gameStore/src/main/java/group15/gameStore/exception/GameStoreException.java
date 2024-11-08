package group15.gameStore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class GameStoreException extends RuntimeException{
    @NonNull
    private HttpStatus status;

    public GameStoreException(@NonNull HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    @NonNull
    public HttpStatus getStatus() {
        return status;
    }
}
