package com.ohgiraffers.cafesyncfinalproject.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class ResponseDTO {

    private int status;
    private String message;
    private Object data;

    public ResponseDTO(){
    }

    public ResponseDTO(int status, String message){
        this.status = status;
        this.message = message;
    }

    public ResponseDTO(HttpStatus status, String message, Object data) {
        this.status = status.value(); // HttpStatus enum 타입에서 value라는 int형 상태 코드 값만 추출
        this.message = message;
        this.data = data;
    }
}
