FROM eclipse-temurin:17-alpine
VOLUME /tmp
VOLUME /logs
# 设置工作目录
WORKDIR /home
# 构建参数
ARG JAR_FILE=nebula-wechat-2024.0.0.jar
ENV jar=$JAR_FILE
# 复制 JAR 文件
COPY target/$jar ./$jar
# 配置时区和字体
RUN sed -i.bak 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories \
    && apk add --no-cache tzdata font-noto font-noto-cjk \
    && rm -rf /var/cache/apk/* \
    && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo 'Asia/Shanghai' >/etc/timezone \
    && mkfontscale && mkfontdir && fc-cache
# 暴露端口
EXPOSE 8085
# 运行应用程序
ENTRYPOINT ["sh", "-c", "java -Xms128m -Xmx128m -jar \"$jar\""]
