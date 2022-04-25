package cf.k5smovie.memberConvenience.error;

public class ApiNotRespondException extends RuntimeException{
    public ApiNotRespondException(String message) {
        super(message);
    }
}
