package com.example.disconf.demo.task;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.disconf.client.usertools.DisconfDataGetter;
import com.example.disconf.demo.config.AutoConfig;
import com.example.disconf.demo.config.CodeConfig;
import com.example.disconf.demo.config.JedisConfig;
import com.example.disconf.demo.service.AutoService;
import com.example.disconf.demo.service.AutoService2;
import com.example.disconf.demo.service.BaoBaoService;
import com.example.disconf.demo.service.SimpleRedisService;
import com.example.disconf.demo.service.SimpleStaticService;

/**
 * 演示分布式配置文件、分布式配置的更新Demo
 *
 * @author liaoqiqi
 * @version 2014-6-17
 */
@Service
public class DisconfDemoTask {

    protected static final Logger LOGGER = LoggerFactory.getLogger(DisconfDemoTask.class);

    @Autowired
    private BaoBaoService baoBaoService;

    @Autowired
    private SimpleRedisService simpleRedisService;

    @Autowired
    private JedisConfig jedisConfig;

    @Autowired
    private AutoService autoService;

    @Autowired
    private AutoService2 autoService2;

    @Autowired
    private CodeConfig codeConfig;

    private static final String REDIS_KEY = "disconf_key";

    @Autowired
    private AutoConfig autoConfig;

    /**
     *
     */
    public int run() {

        try {

            while (true) {

                //
                // service demo
                //

                LOGGER.info("baobao--baifa: {}", baoBaoService.calcBaiFa());
                LOGGER.info("baobao--yuerbao: {}", baoBaoService.calcYuErBao());

                Thread.sleep(5000);

                LOGGER.info(MessageFormat.format("redis( {0} , {1,number,#} ) get key : {2}", jedisConfig.getHost(),
                        jedisConfig.getPort(), REDIS_KEY));

                LOGGER.info("redis( {} , {} )", jedisConfig.getHost(), jedisConfig.getPort());

                LOGGER.info("code config: {}", codeConfig.getCodeError());

                //
                // xml demo
                //

                LOGGER.info("autoservice: {}", autoService.getAuto());

                LOGGER.info("autoservice2: {}", autoService2.getAuto2());

                //
                // static config demo
                //
                LOGGER.info("static file data: {}", SimpleStaticService.getStaticFileData());

                LOGGER.info("static item data: {}", SimpleStaticService.getStaticItem());

                //
                // 动态的写法
                //
                LOGGER.info(DisconfDataGetter.getByFile("redis.properties").toString());
                LOGGER.info(DisconfDataGetter.getByFile("autoconfig.properties").toString());
                LOGGER.info(DisconfDataGetter.getByFile("autoconfig.properties").get("auto").toString());
                LOGGER.info(DisconfDataGetter.getByFileItem("autoconfig.properties", "auto").toString());
                LOGGER.info(DisconfDataGetter.getByItem("moneyInvest").toString());

                LOGGER.info("get bean @value : {}", autoConfig.getAuto());
            }

        } catch (Exception e) {

            LOGGER.error(e.toString(), e);
        }

        return 0;
    }
}
