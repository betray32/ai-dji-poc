package com.milo.DJL_IA;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AutoExec implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("I'm auto generated");
    }
}
