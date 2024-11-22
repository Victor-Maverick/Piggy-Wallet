package africa.semicolon.domain.data.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InitializeMonnifyTransferResponse {
    private boolean requestSuccessful;
    private String responseMessage;
    private String responseCode;

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ResponseBody responseBody;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ResponseBody{
        private double amount;
        private String reference;
        private String status;
        private double totalFee;
        private String destinationAccountName;
        private String destinationBankName;
        private String destinationAccountNumber;
        private String destinationBankCode;
    }

}
