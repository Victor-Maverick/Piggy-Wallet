package africa.semicolon.infrastructure.adapter.input.rest;

import africa.semicolon.application.port.input.paymentUseCase.DepositUseCase;
import africa.semicolon.application.port.input.paymentUseCase.TransferUseCase;
import africa.semicolon.domain.data.dto.request.InitializeTransactionRequest;
import africa.semicolon.domain.data.dto.request.InitializeTransferRequest;
import africa.semicolon.domain.data.dto.response.ApiResponse;
import africa.semicolon.domain.data.dto.response.InitializeMonnifyTransferResponse;
import africa.semicolon.domain.data.dto.response.InitializeTransactionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentRestAdapter {

    private final DepositUseCase<InitializeTransactionResponse, InitializeTransactionRequest> depositUseCase;
    private final TransferUseCase<InitializeMonnifyTransferResponse, InitializeTransferRequest> transferUseCase;

    public PaymentRestAdapter(DepositUseCase<InitializeTransactionResponse, InitializeTransactionRequest> depositUseCase, TransferUseCase<InitializeMonnifyTransferResponse, InitializeTransferRequest> transferUseCase) {
        this.depositUseCase = depositUseCase;
        this.transferUseCase = transferUseCase;
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody InitializeTransactionRequest request) {
        try{
            InitializeTransactionResponse response = depositUseCase.deposit(request);
            return new ResponseEntity<>(new ApiResponse(true, response),OK);
        }catch (Exception exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()),BAD_REQUEST);
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?>transfer(@RequestBody InitializeTransferRequest request){
        try{
            InitializeMonnifyTransferResponse response = transferUseCase.transfer(request);
            return new ResponseEntity<>(new ApiResponse(true,response), OK);
        }
        catch (Exception exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()),BAD_REQUEST);
        }
    }
}
