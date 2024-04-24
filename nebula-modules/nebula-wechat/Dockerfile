FROM eclipse-temurin:17-alpine
VOLUME /tmp
VOLUME /logs
ARG JAR_FILE=nebula-wechat-2024.0.0.jar
ENV jar=$JAR_FILE
ADD target/$JAR_FILE /home/$jar
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories  \
    && apk add --update ttf-dejavu fontconfig  \
    && rm -rf /var/cache/apk/*  \
    && mkfontscale && mkfontdir && fc-cache
EXPOSE 8085
ENTRYPOINT java -jar -Xms128m -Xmx256m  /home/$jar