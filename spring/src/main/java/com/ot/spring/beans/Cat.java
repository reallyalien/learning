package com.ot.spring.beans;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

@Data
public class Cat {

    @Value("#{10}")
    public int age;
    @Value("jack")
    public String name;

}
