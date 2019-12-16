package com.example.disconf.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.disconf.demo.task.DisconfDemoTask;

import software.amazon.codeguruprofilerjavaagent.Profiler;

/**
 * @author liaoqiqi
 * @version 2014-6-17
 */
public class DisconfDemoMain {

    protected static final Logger LOGGER = LoggerFactory.getLogger(DisconfDemoMain.class);

    private static String[] fn = null;

    // 初始化spring文档
    private static void contextInitialized() {
        fn = new String[] {"applicationContext.xml"};
    }

    /**
     * @param args
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        contextInitialized();
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(fn);

        DisconfDemoTask task = ctx.getBean("disconfDemoTask", DisconfDemoTask.class);

        int ret = task.run();
        
        new Profiler.Builder()
           .profilingGroupName("test")
           .build().start()

        System.exit(ret);
    }
}
