package com.qaqtutu.ofdbox.core.utils;

import com.qaqtutu.ofdbox.core.exception.BeanValidException;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
*@Description: 对象验证工具
*@Author: 张家尧
*@date: 2020/10/1 9:50
*/
public class BeanValidUtils {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void valid(T bean,Class<?>... groups){
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(bean, groups);
        if (constraintViolations.isEmpty()) {
            return;
        }
        List<String> errors = new ArrayList<>(10);
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            errors.add(constraintViolation.getPropertyPath() + constraintViolation.getMessage());
        }

        throw new BeanValidException(StringUtils.join(errors,","));
    }

}
