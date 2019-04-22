package com.radiosonda.services;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("prod")
@Service
public class SumServiceImp implements  SumService{

    public String calcSum(String text1, String text2) {
        return text1 + text2;
    }

}
