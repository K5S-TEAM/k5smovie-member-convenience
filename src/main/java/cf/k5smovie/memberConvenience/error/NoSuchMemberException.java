package cf.k5smovie.memberConvenience.error;

public class NoSuchMemberException extends RuntimeException{
    public NoSuchMemberException(String message) {
        super(message);
    }
}
