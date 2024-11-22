package africa.semicolon.infrastructure.adapter.exception;

public class WalletApiException extends RuntimeException{
    public WalletApiException(String message){
        super(message);
    }
}
