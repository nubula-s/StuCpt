package com.stucpt.validator;

import com.stucpt.validator.CheckTimeInterval;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.*;
/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/25/22:12
 * @Description:
 */

@Component
public class CheckTimeIntervalValidator implements ConstraintValidator<CheckTimeInterval, Object> {
    private String startTimeField;
    private String endTimeField;

    @Override
    public void initialize(CheckTimeInterval constraintAnnotation) {
        this.startTimeField = constraintAnnotation.beginTime();
        this.endTimeField = constraintAnnotation.endTime();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        try {
            BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
            String startTime = (String) beanWrapper.getPropertyValue(startTimeField);
            String endTime = (String) beanWrapper.getPropertyValue(endTimeField);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startDateTime = LocalDateTime.parse(startTime, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(endTime, formatter);
            return startDateTime.isBefore(endDateTime);
        } catch (Exception e) {
            return false;
        }
    }

}
