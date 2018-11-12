Bridge文档
Bridge是个小巧的api网管，可以统一将http请求转发到相应的service中。
最常见的场景是将http请求自动转发到Spring的bean里，省去每次写SpringMvc的麻烦
。
## quick start
maven依赖
```xml
    <dependency>
        <groupId>com.lvbby</groupId>
        <artifactId>bridge-api</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>com.lvbby</groupId>
        <artifactId>bridge-http</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>com.lvbby</groupId>
        <artifactId>bridge-spring</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
```
配置bridge
```java
    @Bean
    public BridgeFactoryBean bridgeFactoryBean() {
        BridgeFactoryBean bridgeFactoryBean = new BridgeFactoryBean();
        //配置扫描的注解，已扫描Spring的Service注解为例
        bridgeFactoryBean.setAnnotations(Lists.newArrayList(Service.class));
        return bridgeFactoryBean;
    }
```
配置http服务的入口
```java
@Controller
@RequestMapping("/api/garfield/**")
public class GarfieldBridgeController extends BridgeController {
}
```
配置就ok了，凡是经过bridge处理的http请求都会自动转发到相应的Component方法
使用http访问服务了
假如spring有一个服务SampleService
```java
@Service
public class SampleService{
    String  invoke(String name,Map<String,String> arg ){
        return null;
    }
}
```
使用http访问服务
```javascript
const res = await fetch('/api/garfield', {
    method: 'POST',
    contentType: 'application/json; charset=UTF-8',
    body: JSON.stringify({
        request: {
            name:'lee',
            arg:{
                 key:'value'
            }
        },
        service: 'SampleService',
        method: 'invoke'
    }),
})
const json = await res.json()
``` 