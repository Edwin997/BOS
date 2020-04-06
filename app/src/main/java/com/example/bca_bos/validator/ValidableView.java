package com.example.bca_bos.validator;

public interface ValidableView {
    void addValidator(IValidator p_validator);
    boolean checkValidator();
}
