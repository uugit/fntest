package config;

import com.google.common.base.Splitter;
import com.playcrab.kof.gs.app.KOFBizException;
import com.playcrab.kof.gs.app.ServerErrorDefine;
import com.playcrab.kof.gs.enums.ErrorLogType;
import com.playcrab.kof.gs.enums.ServerRunMode;
import com.playcrab.kof.gs.enums.TencentType;
import com.playcrab.kof.gs.util.CommonHelper;
import com.playcrab.kof.gs.util.DataConfigSettingCollection;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static java.util.Locale.ENGLISH;

public class ConfigLoaderBase {

    private static Logger logger = LoggerFactory.getLogger(ConfigLoaderBase.class);

    public Properties loadBaseConfigProperties(String url) {
        Properties properties = new Properties();
        FileInputStream inStream;
        try {
            inStream = new FileInputStream(url);
            properties.load(inStream);
        } catch (Exception e) {
            String msg = "load conf failed:" + url;
           // CommonHelper.logError(ErrorLogType.RUN_TIME_EXCEPTION.value(), msg, e, "", 0, "");
        }
        return properties;
    }
    
    /**
     * 自动加载properties值到 GSRuntimeConfig 类中 
     * @param properties
     * @param configObj
     * @return
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws KOFBizException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     */
    public GSRuntimeConfig loadConfig(Properties properties, JSONObject configObj) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, KOFBizException, IllegalArgumentException, InvocationTargetException {
    	
    		GSRuntimeConfig config = null;
    		Class<?> clz = GSRuntimeConfig.class;
	    		config = (GSRuntimeConfig)clz.newInstance();
	    		@SuppressWarnings("unchecked")
				Set<Field> fields = ReflectionUtils.getAllFields(clz, ReflectionUtils.withAnnotation(LoadBaseField.class));
	    		Iterator<Field> itf = fields.iterator();
	    		//循环处理普通的属性
	    		while(itf.hasNext()) {
	    			Field field = itf.next();
	    			LoadBaseField fieldAnno = field.getAnnotation(LoadBaseField.class);
	    			String key = fieldAnno.name();
	    			String defaultValue = fieldAnno.value();
	    			if(GSRuntimeConfig.getSpecialAnno().contains(key)) continue;
					String name = field.getName();
					String mathodName = "set" + name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
					Method mth = clz.getMethod(mathodName, field.getType());
					
					Object value = this.getValueByType(properties, field.getType(), key, defaultValue);
					mth.invoke(config, value);
				//验证是否设置成功
//					String mathodNameGet = "get" + name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
//					if(field.getType().getName().endsWith("boolean")) {
//						mathodNameGet = "is" + name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
//					}
//					Method get = clz.getMethod(mathodNameGet);
//					Object getValue = get.invoke(gsr, new Object[]{});
//					System.out.println("field:"+field.getName()+"---getValue:"+getValue);
    			}
    		config = this.disposeSpecialAnno(config, properties, configObj);
    		GSRuntimeConfig.instance = config;
    		
//    		System.out.println("---反射加载---" + JsonSerizlizer.toJsonString(config));
    		
    		return config;
    }
    
    /**
     * 处理一些特殊情况
     * 例如：没有某一条属性时，从其他属性获取
     * config.setRedisRealTimeMaxWaitMillis(Integer.parseInt(properties.getProperty("redis.realtimepk.maxWait", properties.getProperty("redis.maxWaitMillis"))));
     * @param config
     * @param properties
     * @param configObj
     * @return
     */
    private GSRuntimeConfig disposeSpecialAnno(GSRuntimeConfig config, Properties properties, JSONObject configObj){
    	if(config == null) return null;
    	
    	//处理一些特殊情况
    	for(String annoKey : config.getSpecialAnno()) {
			if(annoKey.equals("zoneId")) {
				int platId = config.getOsplatform().equalsIgnoreCase("ios") ? 0 : 1;
				config.setZoneId(String.format("%s%s%s", config.getTencentType().value(), platId, config.getServerId()));
				continue;
			}
			if(annoKey.endsWith("redis.realtimepk.host")) {
				if(properties.containsKey("redis.realtimepk.host")) {
					config.setRedisRealTimeHost(properties.getProperty("redis.realtimepk.host"));
					continue;
				} 
				config.setRedisRealTimeHost(config.getRedisGlobalHost());	
			}
			if(annoKey.endsWith("redis.realtimepk.port")) {
				if(properties.containsKey("redis.realtimepk.port")) {
					config.setRedisRealTimePort(Integer.parseInt(properties.getProperty("redis.realtimepk.port")));
					continue;
				} 
				config.setRedisRealTimePort(config.getRedisGlobalPort());
			}
			if(annoKey.endsWith("redis.realtimepk.pwd")) {
				if(properties.containsKey("redis.realtimepk.pwd")) {
					config.setRedisRealTimePassword(properties.getProperty("redis.realtimepk.pwd"));
					continue;
				} 
				config.setRedisRealTimePassword(config.getPassword());
			}
			if(annoKey.endsWith("redis.realtimepk.maxIdle")) {
				if(properties.containsKey("redis.realtimepk.maxIdle")) {
					config.setRedisRealTimeMaxIdle(Integer.parseInt(properties.getProperty("redis.realtimepk.maxIdle")));
					continue;
				} 
				config.setRedisRealTimeMaxIdle(config.getRedisGlobalMaxIdle());
			}
			if(annoKey.endsWith("redis.realtimepk.maxTotal")) {
				if(properties.containsKey("redis.realtimepk.maxTotal")) {
					config.setRedisRealTimeMaxTotal(Integer.parseInt(properties.getProperty("redis.realtimepk.maxTotal")));
					continue;
				} 
				config.setRedisRealTimeMaxTotal(config.getRedisGlobalMaxTotal());
			}
			if(annoKey.endsWith("redis.realtimepk.maxWait")) {
				if(properties.containsKey("redis.realtimepk.maxWait")) {
					config.setRedisRealTimeMaxWaitMillis(Integer.parseInt(properties.getProperty("redis.realtimepk.maxWait")));
					continue;
				} 
				config.setRedisRealTimeMaxWaitMillis(config.getRedisRealTimeMaxWaitMillis());
			}
			if(annoKey.endsWith("redis.crossserver.host")) {
				if(properties.containsKey("redis.crossserver.host")) {
					config.setCsRedisHost(properties.getProperty("redis.crossserver.host"));
					continue;
				} 
				config.setCsRedisHost(config.getRedisHost());
			}
			if(annoKey.endsWith("redis.crossserver.port")) {
				if(properties.containsKey("redis.crossserver.port")) {
					config.setCsRedisPort(Integer.parseInt(properties.getProperty("redis.crossserver.port")));
					continue;
				} 
				config.setCsRedisPort(config.getRedisPort());
			}
			if(annoKey.endsWith("mongo.crossserver.cluster")) {
				if(properties.containsKey("mongo.crossserver.cluster")) {
					config.setCsDbCluster(properties.getProperty("mongo.crossserver.cluster"));
					continue;
				} 
				config.setCsDbCluster(config.getGameDbCluster());
			}
		}
    	
		config.setProperties(properties);
		config.setConfigObj(configObj);
    	
    	return config;
    }
    
    /**
     * 根据字段属性，从properties中获取具体的值
     * @param properties
     * @param type
     * @param name
     * @param defaultValue
     * @return
     * @throws KOFBizException
     */
    private Object getValueByType(Properties properties, Class<?> type, String name, String defaultValue) throws KOFBizException {
		
    	if(!properties.containsKey(name) && defaultValue.equals("@null")) {
    		String msg = String.format("load global properties error! can not find [%s] and defaultValue [%s]!", name, defaultValue);
    				throw new KOFBizException(ServerErrorDefine.SERVER_INNER_ERROR, msg);
    	}
    	if(type.getName().endsWith("boolean")) {
    		if(!properties.containsKey(name)) {
    			return Integer.parseInt(defaultValue) == 1;
    		}
    		return Integer.parseInt(properties.getProperty(name)) == 1;
    	}
    	if(type.getName().endsWith("int")) {
    		if(!properties.containsKey(name)) {
    			return Integer.parseInt(defaultValue);
    		}
    		return Integer.parseInt(properties.getProperty(name));
    	}
    	if(type.getName().endsWith("Long") || type.getName().endsWith("long")) {
    		if(!properties.containsKey(name)) {
    			return Long.parseLong(defaultValue);
    		}
    		return Long.parseLong(properties.getProperty(name));
    	}
    	if(type.getName().endsWith("String")) {
    		if(!properties.containsKey(name)) {
    			return defaultValue;
    		}
    		return properties.getProperty(name);
    	}
    	if(type.getName().endsWith("List")){
    		String bannedStr = "";
    		if(!properties.containsKey(name)) {
    			bannedStr = defaultValue;
    		} else {
    			bannedStr = properties.getProperty(name);
    		}
             List<String> bannedList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(bannedStr);
             return bannedList;
    	}
    	if(type.getName().endsWith("JSONArray")){
    		if(!properties.containsKey(name)) {
    			return JSONArray.fromObject(defaultValue);
    		}
    		return JSONArray.fromObject(properties.getProperty(name));
    	}
    	//if(type.getName().endsWith("TencentType")) {
    	//	return TencentType.getTypeByName(properties.getProperty(name));
    	//}
    	//if(type.getName().endsWith("ServerRunMode")) {
    	//	if(!properties.containsKey(name)) {
    	//		return ServerRunMode.getTypeByName(defaultValue);
    	//	}
    	//	return ServerRunMode.getTypeByName(properties.getProperty(name));
    	//}
    	return "";
    }

    public GSRuntimeConfig loadConfigFromProperties(Properties properties, JSONObject configObj) {

        GSRuntimeConfig config = new GSRuntimeConfig();
        config.setConfigObj(configObj);

        config.setGameId(properties.getProperty("game_id"));
        config.setPlatform(properties.getProperty("platform"));
        config.setServerId(properties.getProperty("server_id"));
        config.setGlobalAddr(properties.getProperty("global_addr"));
        config.setGlobalUrl(properties.getProperty("global_url"));

        config.setServerIp(properties.getProperty("server_ip", "127.0.0.1"));
        config.setWanIp(properties.getProperty("wan_ip", "127.0.0.1"));
        config.setServerPort(Integer.parseInt(properties.getProperty("server_port")));
        config.setHttpListenHost(properties.getProperty("lan_ip", "127.0.0.1"));
        config.setHttpListenPort(Integer.parseInt(properties.getProperty("http_port")));
        String runModelStr = properties.getProperty("run_model", "dev");
        //if (runModelStr.equalsIgnoreCase("prod")) {
        //    config.setRunModel(ServerRunMode.PROD);
        //} else {
        //    config.setRunModel(ServerRunMode.DEV);
        //}

        config.setOsplatform(properties.getProperty("osplatform"));
        config.setSendBufferSize(Integer.parseInt(properties.getProperty("send_buffer_size")));
        config.setIoWorkerThreadSize(Integer.parseInt(properties.getProperty("io_worker_thread_num")));
        config.setLogicWorkerThreadSize(Integer.parseInt(properties.getProperty("logic_worker_num_per_thread")));
        config.setDbPresistenceInterval(Integer.parseInt(properties.getProperty("db_presistence_interval")));
        config.setStatisticLogPath(properties.getProperty("statistic_log_path", ""));
        config.setSnapshotLogPath(properties.getProperty("snapshot_log_path", ""));
        config.setDataConfigVersion(properties.getProperty("config_revision", "0"));
        config.setPhyServerId(properties.getProperty("physical_server_id"));
        config.setLogLevel(properties.getProperty("log_level", "debug"));
        config.setServerOpenTime(Long.parseLong(properties.getProperty("server_open_time")));
        config.setVMSVersion(properties.getProperty("version"));
        config.setBackendVersion(properties.getProperty("backend_tag"));

        config.setGameDbReplicaSet(properties.getProperty("mongo.replicaset"));
        config.setGameDbCluster(properties.getProperty("mongo.cluster"));
        config.setGameDbName(properties.getProperty("mongo.db"));
        config.setGameDbConnPerHost(Integer.parseInt(properties.getProperty("mongo.db_conn_per_host")));
        config.setGameDbCluster(properties.getProperty("mongo.cluster"));
        config.setGameDbReplicaSet(properties.getProperty("mongo.replicaset"));
        config.setGameDbUser(properties.getProperty("mongo.user"));
        config.setGameDbPassword(properties.getProperty("mongo.password"));

        config.setRedisHost(properties.getProperty("redis.host"));
        config.setRedisPort(Integer.parseInt(properties.getProperty("redis.port")));
        config.setMaxIdle(Integer.parseInt(properties.getProperty("redis.maxIdle")));
        config.setMaxTotal(Integer.parseInt(properties.getProperty("redis.maxTotal")));
        config.setMaxWaitMillis(Integer.parseInt(properties.getProperty("redis.maxWaitMillis")));
        config.setRedisGlobalHost(properties.getProperty("redis.global.host"));
        config.setRedisGlobalPort(Integer.parseInt(properties.getProperty("redis.global.port")));
        config.setRedisGlobalMaxIdle(Integer.parseInt(properties.getProperty("redis.global.maxIdle")));
        config.setRedisGlobalMaxTotal(Integer.parseInt(properties.getProperty("redis.global.maxTotal")));
        config.setPassword(properties.getProperty("redis.password"));

        config.setRedisRealTimeHost(properties.getProperty("redis.realtimepk.host", properties.getProperty("redis.global.host")));
        config.setRedisRealTimePort(Integer.parseInt(properties.getProperty("redis.realtimepk.port", properties.getProperty("redis.global.port"))));
        config.setRedisRealTimePassword(properties.getProperty("redis.realtimepk.pwd", properties.getProperty("redis.password")));
        config.setRedisRealTimeMaxIdle(Integer.parseInt(properties.getProperty("redis.realtimepk.maxIdle", properties.getProperty("redis.global.maxIdle"))));
        config.setRedisRealTimeMaxTotal(Integer.parseInt(properties.getProperty("redis.realtimepk.maxTotal", properties.getProperty("redis.global.maxTotal"))));
        config.setRedisRealTimeMaxWaitMillis(Integer.parseInt(properties.getProperty("redis.realtimepk.maxWait", properties.getProperty("redis.maxWaitMillis"))));

        config.setConfigDbCluster(properties.getProperty("mongo.config.cluster"));
        config.setConfigDbReplicaSet(properties.getProperty("mongo.config.replicaset"));
        config.setConfigDbName(properties.getProperty("dataconfig.db"));

        config.setErrorDbCluster(properties.getProperty("mongo.error.cluster"));
        config.setErrorDbReplicaSet(properties.getProperty("mongo.error.replicaset"));
        config.setErrorDbName(properties.getProperty("mongo.error.db"));

        config.setKofSecurityOpen(Integer.parseInt(properties.getProperty("kof_security")));
        //chat server uri.
        config.setChatServerUri(properties.getProperty("chat_server_uri"));
        config.setChatConfig(properties.getProperty("chat_config"));

        //pvp server config.
        //        config.setPvpServerHost(properties.getProperty("pvp_server_host"));
        //        config.setPvpServerPort(Integer.parseInt(properties.getProperty("pvp_server_port")));
        config.setPvpConfig(properties.getProperty("pvp_config"));
        config.setBattleCheck(Integer.parseInt(properties.getProperty("battle_check")));
        config.setAttrCheck((properties.containsKey("attr_check")) ? Integer.parseInt(properties.getProperty("attr_check")) : 0);

        //tencent tlog
        config.setTlogHost(properties.getProperty("tlog_host"));
        config.setTlogPort(Integer.parseInt(properties.getProperty("tlog_port")));
        config.setTlogOpen(Integer.parseInt(properties.getProperty("tlog_open")));
        config.setTlogCluster(JSONArray.fromObject(properties.getProperty("tlog_cluster")));
        config.setTrealtime(Integer.parseInt(properties.getProperty("trealtime")));

        //tencent push key
        config.setPushAccessId(0);
        config.setPushSecretKey("");

        //加个容错防止忘记配置以后直接跪了...
        if (properties.getProperty("midas_prefix") != null) {
            config.setMidasPrefix(properties.getProperty("midas_prefix"));
        } else {
            config.setMidasPrefix("");
        }

        if (properties.getProperty("disable_competition") != null) {
            int disable = Integer.parseInt(properties.getProperty("disable_competition"));
            config.setDisableCompetition(disable);
        } else {
            config.setDisableCompetition(0);
        }

        //closeBronze 铜像馆开关
        if (properties.getProperty("openBronze") != null) {
            int disable = Integer.parseInt(properties.getProperty("openBronze"));
            config.setEnableBronze(disable);
        } else {
            config.setEnableBronze(1);
        }

        //closeEsoteric 奥义开关
        if (properties.getProperty("closeEsoteric") != null) {
            int disable = Integer.parseInt(properties.getProperty("closeEsoteric"));
            config.setEnableEsoteric(disable);
        } else {
            config.setEnableEsoteric(1);
        }

        //closeSkin 皮肤开关
        if (properties.getProperty("closeSkin") != null) {
            int disable = Integer.parseInt(properties.getProperty("closeSkin"));
            config.setEnableSkin(disable);
        } else {
            config.setEnableSkin(1);
        }

        //unlock system 开关, 如果没配置则默认打开
        if (properties.getProperty("enable_unlocksystem") != null) {
            int enable = Integer.parseInt(properties.getProperty("enable_unlocksystem"));
            config.setEnableUnlockSystem(enable);
        } else {
            config.setEnableUnlockSystem(0);
        }
        
        //删除30天未登陆玩家全部邮件开关
        config.setEnableDelPlayerallMail30(Integer.parseInt(properties.getProperty("delPlayerMail30IsOpen", "1")));

        //错误监控
        config.setErrorMonitorIsOpen(Integer.parseInt(properties.getProperty("errorMonitorIsOpen", "0")));

        //排队相关配置
        config.setQueueEnable(Integer.parseInt(properties.getProperty("queue_enable")));
        config.setQueueNum(Integer.parseInt(properties.getProperty("queue_num")));
        config.setQueueOnlineNum(Integer.parseInt(properties.getProperty("queue_online_num")));
        config.setQueuePushInterval(Integer.parseInt(properties.getProperty("queue_push_interval")));
        config.setQueuePushNum(Integer.parseInt(properties.getProperty("queue_push_num")));

        if (properties.containsKey("banned_ops")) {
            String bannedStr = properties.getProperty("banned_ops");
            List<String> bannedList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(bannedStr);
            config.setBannedOps(bannedList);
        }

        //社团战开关
        if (properties.containsKey("open_unionfight")) {
            int openUnionFight = Integer.parseInt(properties.getProperty("open_unionfight"));
            config.setOpenUnionFight((openUnionFight == 1));
        }

        //公会副本开关
        if (properties.containsKey("open_unioninstance")) {
            int openUnionInstance = Integer.parseInt(properties.getProperty("open_unioninstance"));
            config.setOpenUnionInstance(openUnionInstance == 1);
        }

        //跨服1v1
        if (properties.containsKey("open_crosswar")) {
            int openCrosswar = Integer.parseInt(properties.getProperty("open_crosswar"));
            config.setOpenCSArena(openCrosswar == 1);
        }

        //列传
        if (properties.containsKey("enable_biography")) {
            int enableBiography = Integer.parseInt(properties.getProperty("enable_biography"));
            config.setEnableBiography(enableBiography == 1);
        }

        //跨服拳皇争霸
        if (properties.containsKey("open_crosscom")) {
            int openCrosscom = Integer.parseInt(properties.getProperty("open_crosscom"));
            config.setOpenCSCom(openCrosscom == 1);
        }

        //外部推送
        config.setEnableExternalPush(Integer.parseInt(properties.getProperty("enable_external_push", "1")));

        //各种pve开关
        config.setEnableUnionInsCheck(Integer.parseInt(properties.getProperty("enable_union_ins_check", "1")));
        config.setEnableMainPVECheck(Integer.parseInt(properties.getProperty("enable_main_pve_check", "1")));
        config.setEnableEliteCheck(Integer.parseInt(properties.getProperty("enable_elite_check", "1")));
        config.setEnableNightMareCheck(Integer.parseInt(properties.getProperty("enable_nightmare_check", "1")));
        config.setEnableTeamCheck(Integer.parseInt(properties.getProperty("enable_team_check", "1")));
        config.setEnableSpecialCheck(Integer.parseInt(properties.getProperty("enable_special_check", "1")));
        config.setEnableTowerCheck(Integer.parseInt(properties.getProperty("enable_tower_check", "1")));
        //        config.setRpcListenPort(Integer.parseInt(properties.getProperty("rpc_listen_port")));

        //crossserver数据库
        config.setCsDbCluster(properties.getProperty("mongo.crossserver.cluster", config.getGameDbCluster()));
        config.setCsDbName(properties.getProperty("mongo.crossserver.db", "aqqcs"));
        config.setCsDbReplicaSet(properties.getProperty("mongo.crossserver.replica", ""));
        config.setCsRedisHost(properties.getProperty("redis.crossserver.host", config.getRedisHost()));
        config.setCsRedisPort(Integer.parseInt(properties.getProperty("redis.crossserver.port", String.valueOf(config.getRedisPort()))));

        //分区推荐状态
        config.setRecommend(Integer.parseInt(properties.getProperty("recommand", "0")));
        config.setRpcListenPort(Integer.parseInt(properties.getProperty("rpc_listen_port")));

        config.setProperties(properties);
        config.setGroup(properties.getProperty("group"));
        DataConfigSettingCollection.setName(config.getGroup());

        GSRuntimeConfig.instance = config;

        return config;
    }
}
