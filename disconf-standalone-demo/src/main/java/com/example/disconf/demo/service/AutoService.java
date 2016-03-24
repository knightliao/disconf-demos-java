package com.example.disconf.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by knightliao on 15/3/19.
 */
public class AutoService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AutoService.class);

    private String auto;

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
        LOGGER.info("i' m here: setting auto");
    }
}
