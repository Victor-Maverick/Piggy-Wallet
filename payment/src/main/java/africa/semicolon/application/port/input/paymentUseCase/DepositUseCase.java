package africa.semicolon.application.port.input.paymentUseCase;

public interface DepositUseCase<T,R> {
    T deposit(R request);
}
