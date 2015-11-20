#!/usr/bin/env bash

java -Dlogback.configurationFile=logback.xml \
    -Djava.ext.dirs=lib \
    -Xms128M -Xmx256M -cp .:disconf-standalone-demo.jar \
    com.example.disconf.demo.DisconfDemoMain