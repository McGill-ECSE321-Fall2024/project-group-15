package group15.gearUp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class GearUpException extends RuntimeException{
    @NonNull
    private HttpStatus status;

    public GearUpException(@NonNull HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    @NonNull
    public HttpStatus getStatus() {
        return status;
    }
}
