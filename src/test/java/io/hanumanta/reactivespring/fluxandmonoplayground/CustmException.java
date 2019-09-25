package io.hanumanta.reactivespring.fluxandmonoplayground;

import lombok.Data;

@Data
public class CustmException extends Throwable {
    private String messgae;
    public CustmException(Throwable e) {
    this.messgae = e.getMessage();
    }
}
