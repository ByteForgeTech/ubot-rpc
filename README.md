# UBot - RPC

> 本项目基于协议 [kritor](https://github.com/KarinJS/kritor)

UBot 机器人的 rpc-interface 与 rpc-client 模块实现，并提供简单的服务端用于测试

## Import

### inter

```xml
<dependency>
  <groupId>cn.byteforge.ubot.rpc.inter</groupId>
  <artifactId>interface</artifactId>
  <version>1.0.0</version>
</dependency>
```

### client

```xml
<dependency>
  <groupId>cn.byteforge.ubot.rpc.client</groupId>
  <artifactId>UBotMC</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Features

- [x] ubot 账户鉴权
- [ ] client 与 server 通信
  - [ ] 自动重连，保活检测 
  - [x] QQ 基本功能实现
    - [x] 事件下发
    - [x] 消息发送
  - [x] 自定义拓展功能实现
    - [ ] 消息互通附属
- [ ] 依赖解耦合，支持热更新

### MC Client

#### JS Plugin Format

```javascript

```
