package africa.semicolon.infrastructure.adapter.output;

import africa.semicolon.application.port.output.PaymentOutputPort;
import africa.semicolon.domain.data.dto.request.InitializeTransactionRequest;
import africa.semicolon.domain.data.dto.request.InitializeTransferRequest;
import africa.semicolon.domain.data.dto.response.InitializeMonnifyTransferResponse;
import africa.semicolon.domain.data.dto.response.InitializeTransactionResponse;
import africa.semicolon.domain.data.dto.response.MonnifyAuthenticationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class MonnifyAdapter implements PaymentOutputPort<InitializeTransactionResponse, InitializeTransactionRequest,InitializeMonnifyTransferResponse, InitializeTransferRequest> {
    @Value("${monnify.api.key}")
    private String monnifyApiKey;

    @Value("${monnify.secret.key}")
    private String monnifyApiSecret;

    @Value("${monnify.base_url}")
    private String baseUrl;

    @Value("${monnify.login_url}")
    private String loginUrl;

    @Value("${monnify.contract_code}")
    private String contractCode;
    @Value("${monnify.transaction.redirect_url}")
    private String redirectUrl;

    private final RestTemplate restTemplate;

    public MonnifyAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    private MonnifyAuthenticationResponse generateAccessToken() {
        String encodedCredentials = monnifyApiKey + ":" + monnifyApiSecret;
        encodedCredentials = Base64.getEncoder().encodeToString(encodedCredentials.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Basic " + encodedCredentials);
        HttpEntity<String> entity = new HttpEntity<>("{}", headers);
        ResponseEntity<MonnifyAuthenticationResponse> response = restTemplate.exchange(loginUrl, HttpMethod.POST, entity, MonnifyAuthenticationResponse.class);
        return response.getBody();
    }


    @Override
    public InitializeTransactionResponse deposit(InitializeTransactionRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        String initializationUrl = baseUrl + "/api/v1/merchant/transactions/init-transaction";
        String accessToken = getAccessToken();
        String paymentReference = generateReference(request.getSenderId());


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, Object> body = new HashMap<>();
        body.put("amount", request.getAmount());
        body.put("customerName", request.getFirstName() + " " + request.getFirstName());
        body.put("customerEmail", request.getEmail());
        body.put("paymentReference", paymentReference);
        body.put("paymentDescription", request.getPaymentDescription());
        body.put("currencyCode", "NGN");
        body.put("contractCode", contractCode);
        body.put("redirectUrl", redirectUrl);
        body.put("paymentMethods", new String[]{String.valueOf(request.getPaymentMethod())});

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(initializationUrl, httpEntity, String.class);
            return objectMapper.readValue(responseEntity.getBody(), InitializeTransactionResponse.class);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to initialize Monnify transaction", exception);
        }
    }



    private String getAccessToken() {
        MonnifyAuthenticationResponse authenticateResponse = generateAccessToken();
        return authenticateResponse.getResponseBody().getAccessToken();
    }

    private static String generateReference(UUID id) {
        return id + "-" + UUID.randomUUID();
    }


    @Override
    public InitializeMonnifyTransferResponse transfer(InitializeTransferRequest request) {
        String reference = generateReference(request.getWalletId());
        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        String requestBody = String.format(
                "{\"amount\": %.2f, \"reference\":\"%s\", \"narration\":\"%s\", \"destinationAccountNumber\":\"%s\", \"destinationBankCode\":\"057\", \"currency\":\"%s\", \"sourceAccountNumber\":\"%s\"}",
                request.getAmount(),
                reference,
                request.getNarration(),
                request.getReceiverAccount(),
                "NGN",
                "3561571756"
        );
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                baseUrl + "/api/v2/disbursements/single",
                HttpMethod.POST,
                entity,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        InitializeMonnifyTransferResponse transferResponse;
        try {
            transferResponse = objectMapper.readValue(responseEntity.getBody(), InitializeMonnifyTransferResponse.class);
            transferResponse.setRequestSuccessful(true);
        } catch (Exception e) {
            transferResponse = new InitializeMonnifyTransferResponse();
            transferResponse.setRequestSuccessful(false);
            transferResponse.setResponseMessage("Failed to parse response: " + e.getMessage());
            transferResponse.setResponseCode("PARSING_ERROR");
        }
        return transferResponse;
    }



}
