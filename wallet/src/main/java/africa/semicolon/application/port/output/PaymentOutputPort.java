package africa.semicolon.application.port.output;

public interface PaymentOutputPort<T,R,S,V> {

    T deposit(R request);

    S transfer(V request);
}
