package africa.semicolon.application.port.input.walletUseCase;

public interface initializeTransferUseCase<T,R> {
    T transfer(R request);
}
