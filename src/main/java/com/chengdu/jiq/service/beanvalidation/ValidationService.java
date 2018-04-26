package com.chengdu.jiq.service.beanvalidation;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * Created by jiyiqin on 2017/12/3.
 */
@Service
@Validated
public class ValidationService {

    public boolean create(@Size(min = 4, max = 6) String name, @Max(value = 100) Integer age) {
        return true;
    }
}
