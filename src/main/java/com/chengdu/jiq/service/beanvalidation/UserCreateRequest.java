package com.chengdu.jiq.service.beanvalidation;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by jiyiqin on 2017/12/3.
 */
public class UserCreateRequest {
    @NotBlank(message = "name is null")
    private String name;
    @NotNull(message = "age is null")
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
