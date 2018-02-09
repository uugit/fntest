package com.fn.test.logic;

import config.IConfigLoader;
import config.RemoteConfigServerLoader;
import logger.SystemLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Conf;
import util.RedisUtil;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by fna on 2018/2/6.
 */
public class GameTest {

    private static Logger logger = null;

    public static void main(String args[]) {

        System.out.println("hello");

        String host = "127.0.0.1";
        int port = 6379;

        //RedisUtil.gsInstance.init(1, 10, 1000, host, port);
        //
        //RedisUtil.gsInstance.set("test", "1");

        //这个方法需要配置JVM系统属性
       // System.getProperty("configurePath")

        /**
         * service  23315  1.4 12.6 3383584 1022684 ?     Sl   Feb06  14:25 /data/home/user00/kof/usr/jdk/bin/java -Xmx800m -Xms800m -Xloggc:/data/home/user00/log/gameserver/gc.log -XX:+PrintHeapAtGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCApplicationStoppedTime -XX:NewRatio=4 -XX:SurvivorRatio=4 -XX:+HeapDumpOnOutOfMemoryError -XX:-OmitStackTraceInFastThrow -XX:HeapDumpPath=/data/home/user00/kof/backend/bin/logs/jvm_heapdump.bin -Dfile.encoding=UTF-8 -Ddebug=1 -Dsun.zip.disableMemoryMapping=true -Dgameserver.home=/data/home/user00/kof/backend/bin -Dserver_id=66 -Dlog.home=/data/home/user00/log/gameserver/logs_66 -DClassHotReloadJarPath=/data/home/user00/kof/version/apps/kof_jp_master.180129.01/reload.conf -DClassHotReloadLogDir=/data/home/user00/log/gameserver/reload/ -javaagent:/data/home/user00/kof/backend/bin/chr.jar -cp /data/home/user00/kof/version/apps/kof_jp_master.180129.01/kof_jp_master.180129.01.jar:/data/home/user00/kof/backend/lib/:/data/home/user00/kof/backend/lib/* com.playcrab.kof.gs.GameServer
         * */


        System.out.println(System.getProperty("configurePath"));

        initializeLog4j();

        logger.info("fn=nnn");


        String gameServerPath = getGameServerPath();
        String configFilePath = gameServerPath + "/config/game_server_runtime.conf";
        Conf.init(configFilePath);

        IConfigLoader loader = new RemoteConfigServerLoader();
        try {
            loader.loadConfig(configFilePath);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | KOFBizException e) {
          //  CommonHelper.logError(ErrorLogType.RUN_TIME_EXCEPTION.value(), "load global properties failed.", e, "", 0, "");
           // System.exit(1);
            return;
        }
    }

    public static String getGameServerPath() {
        return System.getProperty("configurePath");
    }

    public static void initializeLog4j() {
        String configFileStr = System.getProperty("configurePath") + "/config/logback.xml";
        System.getProperties().setProperty("logback.configurationFile", configFileStr);
        System.out.println("logback.configurationFile : " + configFileStr);
        logger = LoggerFactory.getLogger(SystemLogger.class);




        //Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
        //
        //    public void uncaughtException(Thread t, Throwable e) {
        //        LoggerFactory.getLogger(ErrorLogger.class).error("Uncaught Exception in thread '" + t.getName() + "'", e);
        //        LoggerFactory.getLogger(DataLogger.class).error("Uncaught Exception in thread '" + t.getName() + "'", e);
        //    }
        //});
    }


}
