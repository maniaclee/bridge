# Bridge-api

Api网关框架，统一管理各种service服务，对外提供统一的访问接口。

## Quick Start

比如有一个服务TestService:

```java
public class TestService {
    public void echo(String s) {
        System.out.println("hello: " + s);
    }
}
```

用Api网关Bridge来对外提供访问：

```java
Bridge bridge = new Bridge().addService(new TestService());//用Bridge接管TestService
bridge.proxy(new Request("TestService", "echo", "{s:'sdf'}"));//调用服务，用json来表示参数
```

