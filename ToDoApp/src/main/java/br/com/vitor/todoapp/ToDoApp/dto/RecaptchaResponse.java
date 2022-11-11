package br.com.vitor.todoapp.ToDoApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecaptchaResponse {

    private boolean success;
    private String hostname;
    private String challenge_ts;

    @JsonProperty("error-codes")
    private String[] errorCodes;
}
