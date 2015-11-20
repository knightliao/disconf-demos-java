disconf-spring-boot-demo
=======

基于微服务架构的流行，disconf作为微服务的标配，目标 是实现无配置化的jar启动微服务项目。

本demo 使用disconf的spring-boot demo程序,更少的配置.

## Quick start

### Main

项目启动运行Application main方法

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan(basePackages = {"com.baidu","com.example"})
    @PropertySource({"classpath:application.properties"})
    @ImportResource({"classpath:disconf.xml"})//引入disconf
    public class Application extends SpringBootServletInitializer {

        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }

        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
            return application.sources(Application.class);
        }
    }

### xml配置

disconf.xml

    <!-- 使用disconf必须添加以下配置 -->
    <bean id="disconfMgrBean" class="com.baidu.disconf.client.DisconfMgrBean"
          destroy-method="destroy">
        <property name="scanPackage" value="com.example.disconf"/>
    </bean>
    <bean id="disconfMgrBean2" class="com.baidu.disconf.client.DisconfMgrBeanSecond"
          init-method="init" destroy-method="destroy">
    </bean>

    <!-- 使用托管方式的disconf配置(无代码侵入, 配置更改会自动reload)-->
    <bean id="configproperties_disconf"
          class="com.baidu.disconf.client.addons.properties.ReloadablePropertiesFactoryBean">
        <property name="locations">
            <list>
                <value>file:autoconfig.properties</value>
            </list>
        </property>
    </bean>

    <bean id="propertyConfigurer"
          class="com.baidu.disconf.client.addons.properties.ReloadingPropertyPlaceholderConfigurer">
        <property name="ignoreResourceNotFound" value="true"/>
        <property name="ignoreUnresolvablePlaceholders" value="true"/>
        <property name="propertiesArray">
            <list>
                <ref bean="configproperties_disconf"/>
            </list>
        </property>
    </bean>


    <bean id="autoService" class="com.example.disconf.config.AutoService">
        <property name="auto" value="${auto=100}"/>
    </bean>

### 使用

#### 注解式 分布式配置

    @Service
    @Scope("singleton")
    @DisconfFile(filename = "simple.properties")
    public class SimpleConfig {
        // 代表连接地址
        private String host;
    
        // 代表连接port
        private int port;
    
        /**
         * 地址
         *
         * @return
         */
        @DisconfFileItem(name = "host")
        public String getHost() {
            return host;
        }
    
        public void setHost(String host) {
            this.host = host;
        }
    
        /**
         * 端口
         *
         * @return
         */
        @DisconfFileItem(name = "port")
        public int getPort() {
            return port;
        }
    
        public void setPort(int port) {
            this.port = port;
        }
    }
    
#### 回调函数

    /**
     * 更新配置时的回调函数
     */
    @Service
    @DisconfUpdateService(classes = { SimpleConfig.class})
    public class SimpleDemoServiceUpdateCallback implements IDisconfUpdate {
    
        protected static final Logger LOGGER = LoggerFactory
                .getLogger(SimpleDemoServiceUpdateCallback.class);
    
        @Autowired
        private SimpleDemoService simpleDemoService;
    
        public void reload() throws Exception {
            simpleDemoService.changeConfig();
        }
    
    }

#### XML 分布式配置

file:autoconfig.properties

### Run 打包、部署、运行

#### war 包式

只要修改下pom.xml，使用war进行打包，然后放到tomcat下运行即可。

#### Jar 包式

微服务的一个重要特征就是不需要tomcat了，一个jar就可以运行。这可能是未来的趋势。因此demo演示了 Jar包运行方式。

##### 打包

mvn package

结果是

    ➜  disconf-spring-boot-web git:(master) ✗ ls target
    classes                                    disconf-spring-boot-web-1.0.0.jar.original maven-archiver
    disconf-spring-boot-web-1.0.0.jar          generated-sources                          maven-status
    ➜  disconf-spring-boot-web git:(master) ✗

##### 运行

    cd target
    java -jar disconf-spring-boot-web-1.0.0.jar
    
结果

    ➜  target git:(master) ✗ ls
    autoconfig.properties                      disconf                                    log                                        simple.properties.lock
    autoconfig.properties.lock                 disconf-spring-boot-web-1.0.0.jar          maven-archiver
    classes                                    disconf-spring-boot-web-1.0.0.jar.original maven-status
    config                                     generated-sources                          simple.properties
    ➜  target git:(master) ✗

spring-boot 采用jar包的运行方式，classpath是jar包内。因此，disconf如果发现是jar包启动方式，便会把当前执行路径当成classpath来读写配置文件。
所以配置文件均会被下载到执行路径。