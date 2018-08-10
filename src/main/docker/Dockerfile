# 指定基础镜像
FROM java:openjdk-8-jdk-alpine
# 创建/tmp目录并持久化到Docker数据文件夹
VOLUME /tmp
# 将应用jar包复制到/app.jar
COPY video-service-0.0.1.jar app.jar
RUN bash -c 'touch /app.jar'
# 容器运行后默认执行的命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
