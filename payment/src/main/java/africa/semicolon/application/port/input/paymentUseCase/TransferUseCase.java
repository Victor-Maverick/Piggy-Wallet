package africa.semicolon.application.port.input.paymentUseCase;

public interface TransferUseCase<T,S> {
    T transfer(S request);
}
