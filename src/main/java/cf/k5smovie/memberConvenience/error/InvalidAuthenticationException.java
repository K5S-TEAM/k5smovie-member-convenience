package cf.k5smovie.memberConvenience.error;

public class InvalidAuthenticationException extends RuntimeException{
    public InvalidAuthenticationException(String message) {
        super(message);
    }
}
