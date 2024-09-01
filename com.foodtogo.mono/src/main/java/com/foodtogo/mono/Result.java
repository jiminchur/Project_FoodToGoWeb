package com.foodtogo.mono;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T> {
    private T data;

    private Result(T data) {
        this.data = data;
    }

    public static <T> Result<T> of(T data) {
        return new Result<>(data);
    }
}

