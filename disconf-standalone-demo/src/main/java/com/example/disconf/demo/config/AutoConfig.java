package com.example.disconf.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by knightliao on 16/6/24.
 */
@Service
public class AutoConfig {

    @Value("${auto}")
    private String auto;

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }
}
