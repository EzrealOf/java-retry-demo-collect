#### java 重试介绍

背景：项目中在文件上传以及消息发送中出现了网络问题导致的 消息未达 或者 超时的现象，所以在项目中使用。

本文目的：主要是记录解决问题的思路以及查阅其他资料对于问题的扩展

	1. 项目中主要使用的是 构建线程池 通过配置任务来实现对于文件上传任务的重试以及异步。
    2.消息发送的重试 使用了spring retry ，通过注解来实现消息的重试