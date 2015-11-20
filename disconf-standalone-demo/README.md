disconf-standalone-demo
=======

使用disconf的standalone demo程序(基于spring)

## Quick Start

### 基于注解式的分布式配置

#### 第一步：撰写配置类

目录 com.example.disconf.demo.config 下

- code.properties: 分布式配置文件
- coefficients.properties: 分布式配置文件
- redis.properties: 分布式配置文件
- remote.properties: 分布式配置文件
- empty.properties:  空的分布式配置文件
- static.properties: 静态配置文件示例
- testXml.xml: xml配置文件示例

以 `code.properties` 举例：

    @Service
    @DisconfFile(filename = "code.properties", copy2TargetDirPath = "disconf")
    public class CodeConfig {
    
        private String codeError = "";
    
        @DisconfFileItem(name = "syserror.paramtype", associateField = "codeError")
        public String getCodeError() {
            return codeError;
        }
    
        public void setCodeError(String codeError) {
            this.codeError = codeError;
        }
    }
  
#### 第一步附： 配置项示例：

com.example.disconf.demo.config.Coefficients.discount

    /**
     * 金融系数文件
     */
    @Service
    @DisconfFile(filename = "coefficients.properties")
    public class Coefficients {
    
        public static final String key = "discountRate";
    
        @Value(value = "2.0d")
        private Double discount;
    }

#### 第二步：撰写回调类

- com.example.disconf.demo.config.JedisConfig: 配置类与回调类 是同一个类的示例
- com.example.disconf.demo.service.callbacks.*: 配置类与配置项 的回调类示例
    - AutoServiceCallback: 多个配置文件同一个回调类
    - RemoteServiceUpdateCallback:
    - SimpleRedisServiceUpdateCallback
    - TestJsonConfigCallback
    - TestXmlConfigCallback: XML的回调函数

#### 第三步：使用起来

看这里 com.example.disconf.demo.task.DisconfDemoTask

- 支持spring注解式(bean)
- 支持静态类(非bean)
