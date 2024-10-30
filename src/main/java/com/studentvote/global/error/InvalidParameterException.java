package com.studentvote.global.error;

import com.studentvote.global.payload.ErrorCode;
import java.util.List;
import lombok.Getter;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

@Getter
public class InvalidParameterException extends DefaultException {

    private final Errors errors;

    public InvalidParameterException(Errors errors) {
        super(ErrorCode.INVALID_PARAMETER);
        this.errors = errors;
    }

    public List<FieldError> getFieldErrors() {
        return errors.getFieldErrors();
    }
}
