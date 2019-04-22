package com.example.hjh.response;


import lombok.Data;

@Data
public class Result {
    private  boolean isSuccess;
    private String message;
    private Object data;
    private String token;


}
