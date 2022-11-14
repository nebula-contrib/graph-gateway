#! /bin/shell

BASE_PATH=`pwd`

# 项目启动jar包名称
APPLICATION_JAR="graph-web.jar"

#
JAVA_OPT="-server -Xms512m -Xmx512m -Xmn256m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=128m"
JAVA_OPT="${JAVA_OPT} -XX:-OmitStackTraceInFastThrow -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -Xloggc:logs/gc.log"

# 启动环境设置：dev、 test、 prod
#nohup java ${JAVA_OPT} -jar ${APPLICATION_JAR} --spring.profiles.active=dev --spring.config.location="config/" 2>&1 &
nohup java ${JAVA_OPT} -jar ${BASE_PATH}/${APPLICATION_JAR} --spring.profiles.active=dev --spring.config.location="config/" >/dev/null 2>&1 &