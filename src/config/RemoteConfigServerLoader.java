package config;

import logger.SystemLogger;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Properties;

public class RemoteConfigServerLoader extends ConfigLoaderBase implements IConfigLoader {

    private static Logger logger = LoggerFactory.getLogger(SystemLogger.class);

    @Override
    public GSRuntimeConfig loadConfig(String localConfigFilePath) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Properties properties = super.loadBaseConfigProperties(localConfigFilePath);

        String url = properties.getProperty("global_addr");
        String global = properties.getProperty("global_url");
        String gameId = properties.getProperty("game_id");
        String serverId = properties.getProperty("server_id");
        if (System.getProperties().containsKey("server_id")) {
            serverId = System.getProperty("server_id");
            properties.put("server_id", serverId);
        }

        logger.info("====== server info ======");
        logger.info("game_id : {}", gameId);
        logger.info("server_id : {}", serverId);

        //优先从配置文件中读取
        String configStr = "";
        JSONObject configObj = null;
        String filePath = String.format("/data/home/user00/kof/filecache/config/gameserver/section/%s.json", serverId);
        File f = new File(filePath);
        try {
            logger.info("====Read Config From Local File====");
            logger.info(filePath);
            FileReader r = new FileReader(f);
            BufferedReader b = new BufferedReader(r);
            String s;
            while ((s = b.readLine()) != null) {
                configStr += s;
            }
            configObj = JSONObject.fromObject(configStr);
            // 本地测试用
            // configObj = JSONObject.fromObject(configStr).getJSONObject("d");
        } catch (Exception e) {
         //   CommonHelper.logError(ErrorLogType.RUN_TIME_EXCEPTION.value(), "", e, "", 0, "");
        }

        if (configStr.equals("")) {
            url = String.format("http://%s/%s/index.php/gmapi/section/loadGsConfig?gameId=%s&serverId=%s", url, global, gameId, serverId);

            logger.info("url : {}", url);
            //configStr = HttpClientHelper.doGet(url);
            //configObj = JSONObject.fromObject(configStr).getJSONObject("d");
        }

        logger.info("====remote load config====");
        logger.info(configObj.toString());
        logger.info("==========================");

        Iterator<String> jsonKeys = configObj.keys();
        while (jsonKeys.hasNext()) {
            String key = jsonKeys.next();
            String value = configObj.getString(key);
            properties.setProperty(key, value);
        }
        return super.loadConfig(properties, configObj);
        //        return super.loadConfigFromProperties(properties, configObj);
    }
}
