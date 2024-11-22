package africa.semicolon.application.port.input.walletUseCase;

public interface InitializeDepositUseCase<T,R> {
    T deposit(R request);
}
