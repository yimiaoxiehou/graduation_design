### 平台目录结构说明


```
├─master----------------------------父项目，公共依赖
│  │
│  ├─eureka--------------------------微服务注册中心
│  │
│  ├─discovery-----------------------微服务配置中心
│  │
│  ├─monitor-------------------------微服务监控中心
│  │
│  ├─zipkin--------------------------微服务日志采集中心
│  │
│  ├─gateway--------------------------微服务网关中心
│  │
│  ├─provider
│  │  │
│  │  ├─provider-mdc------------------数据服务中心
│  │  │
│  │  ├─provider-omc------------------订单服务中心
│  │  │
│  │  ├─provider-opc------------------对接服务中心
│  │  │
│  │  ├─provider-tpc------------------任务服务中心
│  │  │
│  │  └─provider-uac------------------用户服务中心
│  │
│  ├─provider-api
│  │  │
│  │  ├─provider-mdc-api------------------数据服务中心API
│  │  │
│  │  ├─provider-omc-api------------------订单服务中心API
│  │  │
│  │  ├─provider-opc-api------------------对接服务中心API
│  │  │
│  │  ├─provider-tpc-api------------------任务服务中心API
│  │  │
│  │  ├─provider-sdk-api------------------可靠消息服务API
│  │  │
│  │  └─provider-uac-api------------------用户服务中心API
│  │
│  ├─common
│  │  │
│  │  ├─common-base------------------公共POJO基础包
│  │  │
│  │  ├─common-config------------------公共配置包
│  │  │
│  │  ├─common-core------------------微服务核心依赖包
│  │  │
│  │  ├─common-util------------------公共工具包
│  │  │
│  │  ├─common-zk------------------zookeeper配置
│  │  │
│  │  ├─security-app------------------公共无状态安全认证
│  │  │
│  │  ├─security-core------------------安全服务核心包
│  │  │
│  │  └─security-feign------------------基于auth2的feign配置
│  │
│  ├─generator
│  │  │
│  │  ├─generator-mdc------------------数据服务中心Mybatis Generator
│  │  │
│  │  ├─generator-omc------------------数据服务中心Mybatis Generator
│  │  │
│  │  ├─generator-opc------------------数据服务中心Mybatis Generator
│  │  │
│  │  ├─generator-tpc------------------数据服务中心Mybatis Generator
│  │  │
│  │  └─generator-uac------------------数据服务中心Mybatis Generator
```

### 服务说明

| 服务分类  | 服务名                     |   简介     |  应用地址                | 文档 |
|----------|---------------------------|-----------|-------------------------|------|
|  eureka  | eureka-server             | 注册中心   |  http://localhost:8761  |      |
|  center  | bus-server                | 消息中心   |  http://localhost:8071  |      |
|  center  | config-server             | 配置中心   |  http://localhost:8061  |      |
|  auth    | authorization-server      | 授权服务   |  http://localhost:8000  | [权限服务文档](./auth) 、[授权Server文档](./auth/authorization-server)     |
|  auth    | authentication-server     | 签权服务   |  http://localhost:8001  | [认证Server文档](./auth/authentication-server)    |
|  auth    | authentication-client     | 签权客户端  |  jar包引入              |      |
|  gateway | gateway                   | 网关       |  http://localhost:8443  |      |
|  monitor | admin                     | 总体监控    |  http://localhost:8022  |      |
|  monitor | hystrix-dashboard         | 性能指标展示 |  http://localhost:8021  |      |
|  monitor | turbine                   | 性能指标收集 |  http://localhost:8031  |      |
|  monitor | zipkin                    | 日志收集     |  http://localhost:8091 |      |

### Greenwich.M3 版本对应

| Module | Version | Issues |
| --- | --- | --- |
| Spring Cloud | Greenwich.M3 |   |
| Spring Cloud Aws | 2.0.1.RELEASE | ([issues](https://github.com/spring-cloud/spring-cloud-aws/milestone/24?closed=1)) |
| Spring Cloud Bus | 2.1.0.M2 | ([issues](https://github.com/spring-cloud/spring-cloud-bus/milestone/31?closed=1)) |
| Spring Cloud Cloudfoundry | 2.1.0.M1 |   |
| Spring Cloud Commons | 2.1.0.M2 | ([issues](https://github.com/spring-cloud/spring-cloud-commons/milestone/54?closed=1)) |
| Spring Cloud Config | 2.1.0.M3 | ([issues](https://github.com/spring-cloud/spring-cloud-config/milestone/53?closed=1)) |
| Spring Cloud Contract | 2.1.0.M2 | ([issues](https://github.com/spring-cloud/spring-cloud-contract/milestone/38?closed=1)) |
| Spring Cloud Consul | 2.1.0.M2 | ([issues](https://github.com/spring-cloud/spring-cloud-consul/milestone/36?closed=1)) |
| Spring Cloud Function | 2.0.0.RC2 | ([issues](https://github.com/spring-cloud/spring-cloud-function/milestone/13?closed=1)) |
| Spring Cloud Gateway | 2.1.0.M3 | ([issues](https://github.com/spring-cloud/spring-cloud-gateway/milestone/19?closed=1)) |
| Spring Cloud Gcp | 1.1.0.M3 |   |
| Spring Cloud Kubernetes | 1.0.0.M2 | ([issues](https://github.com/spring-cloud/spring-cloud-kubernetes/milestone/6?closed=1)) |
| Spring Cloud Netflix | 2.1.0.M3 | ([issues](https://github.com/spring-cloud/spring-cloud-netflix/milestone/70?closed=1)) |
| Spring Cloud Openfeign | 2.1.0.M2 |   |
| Spring Cloud Security | 2.1.0.M1 |   |
| Spring Cloud Sleuth | 2.1.0.M2 | ([issues](https://github.com/spring-cloud/spring-cloud-sleuth/milestone/54?closed=1)) |
| Spring Cloud Stream | Fishtown.RC2 | [Blog post](https://spring.io/blog/2018/11/19/spring-cloud-stream-fishtown-rc2-2-1-0-rc2-release-announcement) |
| Spring Cloud Task | 2.1.0.M1 | ([issues](https://github.com/spring-cloud/spring-cloud-task/milestone/29?closed=1)) |
| Spring Cloud Vault | 2.1.0.M2 | ([issues](https://github.com/spring-cloud/spring-cloud-vault/milestone/25?closed=1)) |
| Spring Cloud Zookeeper | 2.1.0.M1 |   |