package com.auto.boot.common.utils;

import org.apache.commons.collections4.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * 校验工具类
 *
 * @author zhaohaifan
 */
public class ValidatorUtils {

    public static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验对象
     *
     * @param param 校验的对象
     * @param groupClass 分组
     * @param <T> 泛型
     */
    public static <T> void valid(T param, Class<?> ... groupClass){
        Set<ConstraintViolation<T>> validateSet = VALIDATOR.validate(param, groupClass);
        if (CollectionUtils.isEmpty(validateSet)){
            return;
        }
        ConstraintViolation<T> first = validateSet.stream().findFirst().orElse(null);
        if (first == null) {
            return;
        }
        throw new ValidationException(first.getMessage());
    }
}
