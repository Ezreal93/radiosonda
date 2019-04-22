package com.bettercoding.jfx.services;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
public class FakeSumService implements  SumService{

    public String calcSum(String text1, String text2) {
        return "simulado";
    }

}
