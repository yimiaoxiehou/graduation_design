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
