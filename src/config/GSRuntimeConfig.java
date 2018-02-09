package config;

import com.google.common.collect.Lists;
import com.playcrab.kof.gs.enums.ServerRunMode;
import com.playcrab.kof.gs.enums.TencentType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.annotation.Transient;

import java.util.List;
import java.util.Properties;

/**
 * 采用反射机制，自动赋值，规则如下 1.每条用到的属性，必须增加 @LoadBaseField(name="game_id", value="")
 * 
 * @LoadBaseField 支持value=""，如果不填写value的情况，则默认value="@null" 2.定义属性时，不要使用is开头
 *                3.必须使用规范的set/get方法 正确格式:setAbcDef
 * @author playcrab
 *
 */
public class GSRuntimeConfig {

	public static GSRuntimeConfig instance = new GSRuntimeConfig();

	private Properties properties = null;

	@Transient
	private static List<String> specialAnno = Lists.newArrayList(
	        "redis.realtimepk.host", "redis.realtimepk.port", "redis.realtimepk.pwd",
	        "redis.realtimepk.maxIdle", "redis.realtimepk.maxTotal", "redis.realtimepk.maxWait", 
	        "zoneId", "redis.crossserver.host", "redis.crossserver.port", "mongo.crossserver.cluster");

	/**
	 * 全部配置数据
	 */
	private JSONObject configObj;

	/** game 基本信息相关 **/
	/**
	 * 游戏唯一标识
	 */
	@LoadBaseField(name = "game_id")
	private String gameId;

	/**
	 * 游戏平台
	 */
	@LoadBaseField(name = "platform")
	private String platform;

	/**
	 * 游戏os平台
	 */
	@LoadBaseField(name = "osplatform")
	private String osplatform;

	/**
	 * 分区Id
	 */
	@LoadBaseField(name = "server_id")
	private String serverId;

	/**
	 * 物理服务器Id
	 */
	@LoadBaseField(name = "physical_server_id", value = "127.0.0.1")
	private String phyServerId;

	/**
	 * 物理服务器IP
	 */
	@LoadBaseField(name = "wan_ip", value = "127.0.0.1")
	private String wanIp;

	/**
	 * 微信AppId
	 */
	@LoadBaseField(name = "wx_appid")
	private String wxAppId;

	/**
	 * 微信AppKey
	 */
	@LoadBaseField(name = "wx_appkey")
	private String wxAppKey;

	/**
	 * qqAppId
	 */
	@LoadBaseField(name = "qq_appid")
	private String qqAppId;

	/**
	 * qqAppKey
	 */
	@LoadBaseField(name = "qq_appkey")
	private String qqAppKey;

	/**
	 * payAppId
	 */
	@LoadBaseField(name = "pay_appid")
	private String payAppId;

	/**
	 * payAppKey
	 */
	@LoadBaseField(name = "pay_appkey")
	private String payAppKey;

	/**
	 * 查询米大师云账户地址
	 */
	@LoadBaseField(name = "msdk_host")
	private String msdkHost;

	/**
	 * 数据上报地址
	 */
	@LoadBaseField(name = "msdk_data_host")
	private String msdkDataHost;

	/**
	 * pushAccessId
	 */
	@LoadBaseField(name = "push_accessId")
	private long pushAccessId;

	/**
	 * pushSecretKey
	 */
	@LoadBaseField(name = "push_secretKey")
	private String pushSecretKey;

	/**
	 * 微信还是qq用户的服务器
	 */
	@LoadBaseField(name = "tencent_type")
	private TencentType tencentType;

	/**
	 * 分区分组
	 */
	@LoadBaseField(name = "group")
	private String group;

	/**
	 * global地址
	 */
	@LoadBaseField(name = "global_addr")
	private String globalAddr;

	/**
	 * global地址
	 */
	@LoadBaseField(name = "global_url")
	private String globalUrl;

	/**
	 * global地址
	 */
	@LoadBaseField(name = "chat_server_uri")
	private String chatServerUri;

	@LoadBaseField(name = "chat_config")
	private String chatConfig;

	/** net 配置相关 **/
	/**
	 * IP地址
	 */
	@LoadBaseField(name = "server_ip", value = "127.0.0.1")
	private String serverIp = "127.0.0.1";

	/**
	 * 端口号
	 */
	@LoadBaseField(name = "server_port")
	private int serverPort;

	/**
	 * http host
	 */
	@LoadBaseField(name = "lan_ip", value = "127.0.0.1")
	private String httpListenHost;

	/**
	 * http端口号
	 */
	@LoadBaseField(name = "http_port")
	private int httpListenPort;

	/**
	 * pvp cluster
	 */
	@LoadBaseField(name = "pvp_config")
	private String pvpConfig;

	/**
	 * voice cluster
	 */
	@LoadBaseField(name = "voice_config")
	private String voiceConfig;

	/**
	 * 数据统计记录日志
	 */
	@LoadBaseField(name = "statistic_log_path")
	private String statisticLogPath;

    /**
     * 快照保存路径
     */
    @LoadBaseField(name = "snapshot_log_path", value = "")
    private String snapshotLogPath;

    /**
     * 消息过滤路径
     */
    @LoadBaseField(name = "gs_input_filter_path", value = "/config/game_server_filter.conf")
    private String inputFilterPath;

	/**
	 * 运行模式
	 */
	@LoadBaseField(name = "run_model", value = "dev")
	private ServerRunMode runModel = ServerRunMode.DEV;

	/**
	 * jnet send buffer size
	 */
	@LoadBaseField(name = "send_buffer_size")
	private int sendBufferSize = 262144;

	/**
	 * io线程数量
	 */
	@LoadBaseField(name = "io_worker_thread_num")
	private int ioWorkerThreadSize = 2;

	/**
	 * logic线程数量
	 */
	@LoadBaseField(name = "logic_worker_num_per_thread")
	private int logicWorkerThreadSize = 2;

	/**
	 * 持久化时间间隔
	 */
	@LoadBaseField(name = "db_presistence_interval")
	private int dbPresistenceInterval = 60;

	/**
	 * 踢非活跃用户下线时间，单位分钟，设置小于0代表此项配置不生效
	 */
	private int kickInactivePlayerInterval = 20;

	/** mongodb 配置相关 **/
	/**
	 * game db host
	 */
	private String gameDbHost = "127.0.0.1";

	/**
	 * game db port
	 */
	private int gameDbPort;

	/**
	 * game db name
	 */
	@LoadBaseField(name = "mongo.db")
	private String gameDbName;

	@LoadBaseField(name = "mongo.user")
	private String gameDbUser;

	@LoadBaseField(name = "mongo.password")
	private String gameDbPassword;

	/**
	 * game db replicaset name
	 */
	@LoadBaseField(name = "mongo.replicaset")
	private String gameDbReplicaSet;

	/**
	 * game db cluster
	 */
	@LoadBaseField(name = "mongo.cluster")
	private String gameDbCluster;

	/**
	 * game connections per host.
	 */
	@LoadBaseField(name = "mongo.db_conn_per_host")
	private int gameDbConnPerHost;

	/**
	 * dataconfig db cluster
	 */
	@LoadBaseField(name = "mongo.config.cluster")
	private String configDbCluster;

	/**
	 * dataconfig db replicaset name
	 */
	@LoadBaseField(name = "mongo.config.replicaset")
	private String configDbReplicaSet;

	/**
	 * error monitor db cluster
	 */
	@LoadBaseField(name = "mongo.error.cluster")
	private String errorDbCluster;

	/**
	 * error monitor db replicaset name
	 */
	@LoadBaseField(name = "mongo.error.replicaset")
	private String errorDbReplicaSet;

	/**
	 * error monitor db name
	 */
	@LoadBaseField(name = "mongo.error.db")
	private String errorDbName;

	/**
	 * error monitor db
	 */
	private String configDbHost = "127.0.0.1";

	/**
	 * config db port
	 */
	private int configDbPort;

	/**
	 * config db name
	 */
	@LoadBaseField(name = "dataconfig.db")
	private String configDbName;

	/**
	 * 开服时间
	 */
	@LoadBaseField(name = "server_open_time")
	private Long serverOpenTime;

	/**
	 * 服务器当前数值版本
	 */
	@LoadBaseField(name = "config_revision", value = "0")
	private String dataConfigVersion = "";

	/**
	 * 服务器当前代码版本
	 */
	@LoadBaseField(name = "backend_tag")
	private String backendVersion = "";

	/**
	 * VSM版本号
	 */
	@LoadBaseField(name = "version")
	private String VMSVersion = "";

	/**
	 * 日志级别
	 */
	@LoadBaseField(name = "log_level", value = "debug")
	private String logLevel = "debug";

	/** redis 配置相关 **/
	@LoadBaseField(name = "redis.host")
	private String redisHost = "127.0.0.1";

	@LoadBaseField(name = "redis.port")
	private int redisPort = 6379;

	@LoadBaseField(name = "redis.maxIdle")
	private int maxIdle = 10;

	@LoadBaseField(name = "redis.maxTotal")
	private int maxTotal = 10;

	@LoadBaseField(name = "redis.maxWaitMillis")
	private int maxWaitMillis = 1000;

	private boolean testOnBorrow = true;

	@LoadBaseField(name = "redis.password")
	private String password = "";

	@LoadBaseField(name = "redis.global.host")
	private String redisGlobalHost = "";

	@LoadBaseField(name = "redis.global.port")
	private int redisGlobalPort = 6379;

	@LoadBaseField(name = "redis.global.maxIdle")
	private int redisGlobalMaxIdle = 10;

	@LoadBaseField(name = "redis.global.maxTotal")
	private int redisGlobalMaxTotal = 10;

	/**
	 * realtime redis
	 */
	@LoadBaseField(name = "redis.realtimepk.host")
	private String redisRealTimeHost = "127.0.0.1";

	@LoadBaseField(name = "redis.realtimepk.port")
	private int redisRealTimePort = 6379;

	@LoadBaseField(name = "redis.realtimepk.pwd")
	private String redisRealTimePassword = "";

	@LoadBaseField(name = "redis.realtimepk.maxIdle")
	private int redisRealTimeMaxIdle = 10;

	@LoadBaseField(name = "redis.realtimepk.maxTotal")
	private int redisRealTimeMaxTotal = 10;

	@LoadBaseField(name = "redis.realtimepk.maxWait")
	private int RedisRealTimeMaxWaitMillis = 1000;
	
	/**
     * battleReport redis
     */
    @LoadBaseField(name = "redis.battleReport.host")
    private String redisBattleReportHost = "127.0.0.1";

    @LoadBaseField(name = "redis.battleReport.port")
    private int redisBattleReportPort = 6379;

    @LoadBaseField(name = "redis.battleReport.pwd")
    private String redisBattleReportPassword = "";

    @LoadBaseField(name = "redis.battleReport.maxIdle")
    private int redisBattleReportMaxIdle = 10;

    @LoadBaseField(name = "redis.battleReport.maxTotal")
    private int redisBattleReportMaxTotal = 10;

    @LoadBaseField(name = "redis.battleReport.maxWait")
    private int redisBattleReportMaxWaitMillis = 1000;

	/**
	 * tlog server host
	 */
	@LoadBaseField(name = "tlog_host")
	private String tlogHost;

	/**
	 * tlog server port
	 */
	@LoadBaseField(name = "tlog_port")
	private int tlogPort;

	/**
	 * tlog server cluster
	 */
	@LoadBaseField(name = "tlog_cluster")
	private JSONArray tlogCluster;

	/**
	 * 是否开启tlog服务器
	 */
	@LoadBaseField(name = "tlog_open")
	private int tlogOpen;

	/**
	 * 是否开启tSecurityOpen
	 */
	@LoadBaseField(name = "t_security", value = "0")
	private int tSecurityOpen;

	/**
	 * 是否开启tApolloOpen
	 */
	@LoadBaseField(name = "t_apollo", value = "1")
	private int tApolloOpen;

	/**
	 * 付费方式 先扣免费钻石,还是先扣付费钻石  0  是先扣免费 1是先扣付费 JP
	 */
	@LoadBaseField(name = "payType", value = "0")
	private int payType;

	//终极之战开开关
	@LoadBaseField(name = "open_finalwar", value = "1")
	private boolean openFianlWar = true;

	/**
	 * 是否开启三防
	 */
	@LoadBaseField(name = "kof_security")
	private int kofSecurityOpen;

	@LoadBaseField(name = "battle_check")
	private int battleCheck;

	@LoadBaseField(name = "attr_check", value = "0")
	private int attrCheck = 0;

	/**
	 * 是否开启实时统计
	 */
	@LoadBaseField(name = "trealtime")
	private int trealtime;

	/**
	 * tlog 和 midashi 使用 规则： 第一位是areaID（1：微信，2：手Q） 第二位是platID（0：ios，1：android）
	 * 正式服务后三位是001-899 测试服后三位是900-999
	 */
	@LoadBaseField(name = "zoneId")
	private String zoneId;

	@LoadBaseField(name = "discountid")
	private String discountid;

	@LoadBaseField(name = "giftid")
	private String giftid;

	/**
	 * midas请求前缀,一般为空,有时候为"/v3/r"...
	 */
	@LoadBaseField(name = "midas_prefix")
	private String midasPrefix = "";

	@LoadBaseField(name = "queue_enable")
	private int queueEnable = 0;

	@LoadBaseField(name = "queue_online_num")
	private int queueOnlineNum = 3000;

	@LoadBaseField(name = "queue_push_num")
	private int queuePushNum = 100;

	@LoadBaseField(name = "queue_num")
	private int queueNum = 2000;

	@LoadBaseField(name = "queue_push_interval")
	private long queuePushInterval = 10;

	@LoadBaseField(name = "disable_competition", value = "0")
	private int disableCompetition = 0;

	@LoadBaseField(name = "openBronze", value = "1")
	private int enableBronze = 0;

	@LoadBaseField(name = "closeSkin", value = "1")
	private int enableSkin = 0;

	@LoadBaseField(name = "closeEsoteric", value = "1")
	private int enableEsoteric = 0;

	@LoadBaseField(name = "open_unionfight")
	private boolean openUnionFight = false;

	/**
	 * 公会副本开关
	 */
	@LoadBaseField(name = "open_unioninstance", value = "1")
	private boolean openUnionInstance = true;

	@LoadBaseField(name = "open_crosscom", value = "1")
	private boolean openCSCom = true;

	@LoadBaseField(name = "open_crosssports", value = "1")
	private boolean openCSSports = true;

	@LoadBaseField(name = "open_crosswar", value = "1")
	private boolean openCSArena = true;

	/**
	 * 列传开关
	 */
	@LoadBaseField(name = "enable_biography", value = "1")
	private boolean enableBiography = true;

	/** 巅峰竞技场开关(0:开启；1:关闭；) */
	@LoadBaseField(name = "enable_peak_sports_check", value = "0")
	private int enablePeakSports = 0;

	/**
	 * 禁用的opType
	 */
	@LoadBaseField(name = "banned_ops")
	private List<String> bannedOps = Lists.newArrayList();


	/**
	 * 实验室开关
	 * 1-开，0-关闭
	 */
	@LoadBaseField(name = "enable_laboratory", value = "1")
	private boolean enableLaboratory = true;

	/**
	 * 错误监控开关
	 * 
	 * @return
	 */
	@LoadBaseField(name = "errorMonitorIsOpen", value = "1")
	private int errorMonitorIsOpen = 0;

    @LoadBaseField(name = "open_crossclub", value = "1")
    private boolean openCSClub = true;

    @LoadBaseField(name = "enable_unlocksystem", value = "1")
	private int enableUnlockSystem = 1;

	@LoadBaseField(name = "enable_external_push", value = "1")
	private int enableExternalPush = 1;

	@LoadBaseField(name = "enable_union_ins_check", value = "1")
	private int enableUnionInsCheck = 1;
	
	@LoadBaseField(name = "enable_realtime_title", value = "0")
	private int enableRealTimeTitle = 0;
    
    /**
     * 八门开关
     */
    @LoadBaseField(name = "enable_eightDoor", value = "1")
    private boolean enableEightDoor = true;
    
    @LoadBaseField(name = "enable_boss_check", value = "1")
	private int enableBossCheck = 1;

	@LoadBaseField(name = "enable_main_pve_check", value = "1")
	private int enableMainPVECheck = 1;

	@LoadBaseField(name = "enable_elite_check", value = "1")
	private int enableEliteCheck = 1;

	@LoadBaseField(name = "enable_nightmare_check", value = "1")
	private int enableNightMareCheck = 1;

	@LoadBaseField(name = "enable_special_check", value = "1")
	private int enableSpecialCheck = 1;

	@LoadBaseField(name = "enable_team_check", value = "1")
	private int enableTeamCheck = 1;

	@LoadBaseField(name = "enable_tower_check", value = "1")
	private int enableTowerCheck = 1;

	@LoadBaseField(name = "enable_holiday_check", value = "1")
	private int enableHolidayCheck = 1;
	
	@LoadBaseField(name = "enable_reincarnation_check", value = "1")
	private int enableReincarnationCheck = 1;

	@LoadBaseField(name = "recommand", value = "0")
	private int recommend = 0;

	/**
	 * rpc端口号
	 */
	@LoadBaseField(name = "rpc_listen_port")
	private int rpcListenPort;

	private int rpcMaxConns;

	/** 跨服战数据库 **/
	@LoadBaseField(name = "redis.crossserver.host")
	private String csRedisHost;

	@LoadBaseField(name = "redis.crossserver.port")
	private int csRedisPort;

	@LoadBaseField(name = "mongo.crossserver.replica")
	private String csDbReplicaSet;

	@LoadBaseField(name = "mongo.crossserver.cluster")
	private String csDbCluster;

	@LoadBaseField(name = "mongo.crossserver.db", value = "aqqcs")
	private String csDbName;

	@LoadBaseField(name = "server_chat_port", value = "8887")
	private int serverChatPort;

	/** 30天未登录玩家 邮件全部删除 **/
	private int delPlayerMail30IsOpen = 0;

	/**
	 * 巅峰赛事
	 */
	@LoadBaseField(name = "real_com_key", value = "ALL")
	private String realComKey;

	@LoadBaseField(name = "real_com_run", value = "0")
	private boolean realComRun = false;
	
	@LoadBaseField(name = "world_server_ip", value = "")
	private String worldServerIp;

	@LoadBaseField(name = "world_server_port", value = "8888")
	private int worldServerPort;

	@LoadBaseField(name = "combat_suppress", value = "0")
	private int combatSuppress;

	@LoadBaseField(name = "nation", value = "CN")
	private String nation;

	/**
	 * 援护开关
	 */
	@LoadBaseField(name = "enable_intervene", value = "1")
	private boolean enableIntervene = true;

	public int getEnableDelPlayerallMail30() {
		return delPlayerMail30IsOpen;
	}

	public void setEnableDelPlayerallMail30(int enableDelMail) {
		this.delPlayerMail30IsOpen = enableDelMail;
	}

	public int getErrorMonitorIsOpen() {
		return errorMonitorIsOpen;
	}

	public void setErrorMonitorIsOpen(int isOpen) {
		this.errorMonitorIsOpen = isOpen;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getOsplatform() {
		return osplatform;
	}

	public void setOsplatform(String osplatform) {
		this.osplatform = osplatform;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public int getHttpListenPort() {
		return httpListenPort;
	}

	public void setHttpListenPort(int httpListenPort) {
		this.httpListenPort = httpListenPort;
	}

	public int getSendBufferSize() {
		return sendBufferSize;
	}

	public void setSendBufferSize(int sendBufferSize) {
		this.sendBufferSize = sendBufferSize;
	}

	public int getIoWorkerThreadSize() {
		return ioWorkerThreadSize;
	}

	public void setIoWorkerThreadSize(int ioWorkerThreadSize) {
		this.ioWorkerThreadSize = ioWorkerThreadSize;
	}

	public int getLogicWorkerThreadSize() {
		return logicWorkerThreadSize;
	}

	public void setLogicWorkerThreadSize(int logicWorkerThreadSize) {
		this.logicWorkerThreadSize = logicWorkerThreadSize;
	}

	public String getGameDbHost() {
		return gameDbHost;
	}

	public void setGameDbHost(String gameDbHost) {
		this.gameDbHost = gameDbHost;
	}

	public int getGameDbPort() {
		return gameDbPort;
	}

	public void setGameDbPort(int gameDbPort) {
		this.gameDbPort = gameDbPort;
	}

	public String getGameDbName() {
		return gameDbName;
	}

	public void setGameDbName(String gameDbName) {
		this.gameDbName = gameDbName;
	}

	public String getConfigDbHost() {
		return configDbHost;
	}

	public void setConfigDbHost(String configDbHost) {
		this.configDbHost = configDbHost;
	}

	public int getConfigDbPort() {
		return configDbPort;
	}

	public void setConfigDbPort(int configDbPort) {
		this.configDbPort = configDbPort;
	}

	public String getConfigDbName() {
		return configDbName;
	}

	public void setConfigDbName(String configDbName) {
		this.configDbName = configDbName;
	}

	public String getRedisHost() {
		return redisHost;
	}

	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}

	public int getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(int maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public int getDbPresistenceInterval() {
		return dbPresistenceInterval;
	}

	public void setDbPresistenceInterval(int dbPresistenceInterval) {
		this.dbPresistenceInterval = dbPresistenceInterval;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getStatisticLogPath() {
		return statisticLogPath;
	}

	public void setStatisticLogPath(String statisticLogPath) {
		this.statisticLogPath = statisticLogPath;
	}

    public String getInputFilterPath() {
        return inputFilterPath;
    }

    public void setInputFilterPath(String inputFilterPath) {
        this.inputFilterPath = inputFilterPath;
    }

	public Long getServerOpenTime() {
		return serverOpenTime;
	}

	public void setServerOpenTime(Long serverOpenTime) {
		// 秒转换成毫秒
		this.serverOpenTime = serverOpenTime * 1000;
	}

	public String getDataConfigVersion() {
		return dataConfigVersion;
	}

	public void setDataConfigVersion(String dataConfigVersion) {
		this.dataConfigVersion = dataConfigVersion;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getGlobalAddr() {
		return globalAddr;
	}

	public void setGlobalAddr(String globalAddr) {
		this.globalAddr = globalAddr;
	}

	public ServerRunMode getRunModel() {
		return runModel;
	}

	public void setRunModel(ServerRunMode runModel) {
		this.runModel = runModel;
	}

	public String getSnapshotLogPath() {
		return snapshotLogPath;
	}

	public void setSnapshotLogPath(String snapshotLogPath) {
		this.snapshotLogPath = snapshotLogPath;
	}

	public String getGlobalUrl() {
		return globalUrl;
	}

	public void setGlobalUrl(String globalUrl) {
		this.globalUrl = globalUrl;
	}

	public int getKickInactivePlayerInterval() {
		return kickInactivePlayerInterval;
	}

	public void setKickInactivePlayerInterval(int kickInactivePlayerInterval) {
		this.kickInactivePlayerInterval = kickInactivePlayerInterval;
	}

	public String getPhyServerId() {
		return phyServerId;
	}

	public void setPhyServerId(String phyServerId) {
		this.phyServerId = phyServerId;
	}

	public String getQqAppId() {
		return qqAppId;
	}

	public void setQqAppId(String qqAppId) {
		this.qqAppId = qqAppId;
	}

	public String getWxAppId() {
		return wxAppId;
	}

	public void setWxAppId(String wxAppId) {
		this.wxAppId = wxAppId;
	}

	public String getWanIp() {
		return wanIp;
	}

	public void setWanIp(String wanIp) {
		this.wanIp = wanIp;
	}

	public String getQqAppKey() {
		return qqAppKey;
	}

	public void setQqAppKey(String qqAppKey) {
		this.qqAppKey = qqAppKey;
	}

	public String getWxAppKey() {
		return wxAppKey;
	}

	public void setWxAppKey(String wxAppKey) {
		this.wxAppKey = wxAppKey;
	}

	public String getMsdkHost() {
		return msdkHost;
	}

	public String getMsdkDataHost() {
		return msdkDataHost;
	}

	public void setMsdkDataHost(String upDataHost) {
		this.msdkDataHost = upDataHost;
	}

	public void setMsdkHost(String msdkHost) {
		this.msdkHost = msdkHost;
	}

	public String getTlogHost() {
		return tlogHost;
	}

	public void setTlogHost(String tlogHost) {
		this.tlogHost = tlogHost;
	}

	public int getGameDbConnPerHost() {
		return gameDbConnPerHost;
	}

	public void setGameDbConnPerHost(int gameDbConnPerHost) {
		this.gameDbConnPerHost = gameDbConnPerHost;
	}

	public int getTlogPort() {
		return tlogPort;
	}

	public void setTlogPort(int tlogPort) {
		this.tlogPort = tlogPort;
	}

	public int getTlogOpen() {
		return tlogOpen;
	}

	public void setTlogOpen(int tlogOpen) {
		this.tlogOpen = tlogOpen;
	}

	public boolean isTlogOpen() {
		return this.tlogOpen == 1;
	}

	public boolean isTSecurityOpen() {
		return this.tSecurityOpen == 1;
	}

	public boolean isTApolloOpen() {
		return this.tApolloOpen == 1;
	}

	public String getPayAppId() {
		return payAppId;
	}

	public void setPayAppId(String payAppId) {
		this.payAppId = payAppId;
	}

	public String getPayAppKey() {
		return payAppKey;
	}

	public void setPayAppKey(String payAppKey) {
		this.payAppKey = payAppKey;
	}

	public TencentType getTencentType() {
		return tencentType;
	}

	public void setTencentType(TencentType tencentType) {
		this.tencentType = tencentType;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getDiscountid() {
		return discountid;
	}

	public void setDiscountid(String discountid) {
		this.discountid = discountid;
	}

	public String getGiftid() {
		return giftid;
	}

	public void setGiftid(String giftid) {
		this.giftid = giftid;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getVMSVersion() {
		return VMSVersion;
	}

	public void setVMSVersion(String vMSVersion) {
		VMSVersion = vMSVersion;
	}

	public int getTrealtime() {
		return trealtime;
	}

	public void setTrealtime(int trealtime) {
		this.trealtime = trealtime;
	}

	public boolean isRealTimeOpen() {
		return this.trealtime == 1;
	}

	public String getBackendVersion() {
		return backendVersion;
	}

	public void setBackendVersion(String backendVersion) {
		this.backendVersion = backendVersion;
	}

	public long getPushAccessId() {
		return pushAccessId;
	}

	public void setPushAccessId(long pushAccessId) {
		this.pushAccessId = pushAccessId;
	}

	public String getPushSecretKey() {
		return pushSecretKey;
	}

	public void setPushSecretKey(String pushSecretKey) {
		this.pushSecretKey = pushSecretKey;
	}

	public int getTSecurityOpen() {
		return tSecurityOpen;
	}
	
    public void setTSecurityOpen(int tSecurityOpen) {
        this.tSecurityOpen = tSecurityOpen;
    }

	public int getTApolloOpen() {
		return tApolloOpen;
	}

    public void setTApolloOpen(int tApolloOpen) {
        this.tApolloOpen = tApolloOpen;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMidasPrefix() {
		return midasPrefix;
	}

	public void setMidasPrefix(String midasPrefix) {
		this.midasPrefix = midasPrefix;
	}

	public String getGameDbCluster() {
		return gameDbCluster;
	}

	public void setGameDbCluster(String gameDbCluster) {
		this.gameDbCluster = gameDbCluster;
	}

	public String getGameDbReplicaSet() {
		return gameDbReplicaSet;
	}

	public void setGameDbReplicaSet(String gameDbReplicaSet) {
		this.gameDbReplicaSet = gameDbReplicaSet;
	}

	public int getQueueEnable() {
		return queueEnable;
	}

	public void setQueueEnable(int queueEnable) {
		this.queueEnable = queueEnable;
	}

	public int getQueueOnlineNum() {
		return queueOnlineNum;
	}

	public void setQueueOnlineNum(int queueOnlineNum) {
		this.queueOnlineNum = queueOnlineNum;
	}

	public int getQueueNum() {
		return queueNum;
	}

	public void setQueueNum(int queueNum) {
		this.queueNum = queueNum;
	}

	public String getChatConfig() {
		return chatConfig;
	}

	public void setChatConfig(String chatConfig) {
		this.chatConfig = chatConfig;
	}

	public String getChatServerUri() {
		return chatServerUri;
	}

	public void setChatServerUri(String chatServerUri) {
		this.chatServerUri = chatServerUri;
	}

	public boolean isBattleCheck() {
		return battleCheck == 1;
	}

	public int getBattleCheck() {
		return battleCheck;
	}

	public void setBattleCheck(int battleCheck) {
		this.battleCheck = battleCheck;
	}

	public String getPvpConfig() {
		return pvpConfig;
	}

	public void setPvpConfig(String pvpConfig) {
		this.pvpConfig = pvpConfig;
	}

	public String getVoiceConfig() {
		return voiceConfig;
	}

	public void setVoiceConfig(String voiceConfig) {
		this.voiceConfig = voiceConfig;
	}

	public void setKofSecurityOpen(int kofSecurityOpen) {
		this.kofSecurityOpen = kofSecurityOpen;
	}

	public int getKofSecurityOpen() {
		return kofSecurityOpen;
	}

	public boolean isKofSecurityOpen() {
		return kofSecurityOpen == 1;
	}

	public long getQueuePushInterval() {
		return queuePushInterval;
	}

	public void setQueuePushInterval(long queuePushInterval) {
		this.queuePushInterval = queuePushInterval;
	}

	public String getConfigDbCluster() {
		return configDbCluster;
	}

	public void setConfigDbCluster(String configDbCluster) {
		this.configDbCluster = configDbCluster;
	}

	public String getConfigDbReplicaSet() {
		return configDbReplicaSet;
	}

	public void setConfigDbReplicaSet(String configDbReplicaSet) {
		this.configDbReplicaSet = configDbReplicaSet;
	}

	public String getGameDbPassword() {
		return gameDbPassword;
	}

	public void setGameDbPassword(String gameDbPassword) {
		this.gameDbPassword = gameDbPassword;
	}

	public String getGameDbUser() {
		return gameDbUser;
	}

	public void setGameDbUser(String gameDbUser) {
		this.gameDbUser = gameDbUser;
	}

	public String getRedisGlobalHost() {
		return redisGlobalHost;
	}

	public void setRedisGlobalHost(String redisGlobalHost) {
		this.redisGlobalHost = redisGlobalHost;
	}

	public int getRedisGlobalMaxIdle() {
		return redisGlobalMaxIdle;
	}

	public void setRedisGlobalMaxIdle(int redisGlobalMaxIdle) {
		this.redisGlobalMaxIdle = redisGlobalMaxIdle;
	}

	public int getRedisGlobalMaxTotal() {
		return redisGlobalMaxTotal;
	}

	public void setRedisGlobalMaxTotal(int redisGlobalMaxTotal) {
		this.redisGlobalMaxTotal = redisGlobalMaxTotal;
	}

	public int getRedisGlobalPort() {
		return redisGlobalPort;
	}

	public void setRedisGlobalPort(int redisGlobalPort) {
		this.redisGlobalPort = redisGlobalPort;
	}

	public JSONArray getTlogCluster() {
		return tlogCluster;
	}

	public void setTlogCluster(JSONArray tlogCluster) {
		this.tlogCluster = tlogCluster;
	}

	public String getHttpListenHost() {
		return httpListenHost;
	}

	public void setHttpListenHost(String httpListenHost) {
		this.httpListenHost = httpListenHost;
	}

	public JSONObject getConfigObj() {
		return configObj;
	}

	public void setConfigObj(JSONObject configObj) {
		this.configObj = configObj;
	}

	public List<String> getBannedOps() {
		return bannedOps;
	}

	public void setBannedOps(List<String> bannedOps) {
		this.bannedOps = bannedOps;
	}

	public int getAttrCheck() {
		return attrCheck;
	}

	public void setAttrCheck(int attrCheck) {
		this.attrCheck = attrCheck;
	}

	public boolean isAttrCheck() {
		return this.attrCheck == 1;
	}

	public int getDisableCompetition() {
		return disableCompetition;
	}

	public void setDisableCompetition(int disableCompetition) {
		this.disableCompetition = disableCompetition;
	}

	public int getEnableBronze() {
		return enableBronze;
	}

	public void setEnableBronze(int enableBronze) {
		this.enableBronze = enableBronze;
	}

	public boolean isOpenUnionFight() {
		return openUnionFight;
	}

	public void setOpenUnionFight(boolean isOpenUnionFight) {
		this.openUnionFight = isOpenUnionFight;
	}

	public String getErrorDbCluster() {
		return errorDbCluster;
	}

	public void setErrorDbCluster(String errorDbCluster) {
		this.errorDbCluster = errorDbCluster;
	}

	public String getErrorDbReplicaSet() {
		return errorDbReplicaSet;
	}

	public void setErrorDbReplicaSet(String errorDbReplicaSet) {
		this.errorDbReplicaSet = errorDbReplicaSet;
	}

	public String getErrorDbName() {
		return errorDbName;
	}

	public void setErrorDbName(String errorDbName) {
		this.errorDbName = errorDbName;
	}

	public int getEnableUnlockSystem() {
		return enableUnlockSystem;
	}

	public void setEnableUnlockSystem(int enableUnlockSystem) {
		this.enableUnlockSystem = enableUnlockSystem;
	}

	public int getEnableExternalPush() {
		return enableExternalPush;
	}

	public void setEnableExternalPush(int enableExternalPush) {
		this.enableExternalPush = enableExternalPush;
	}

	public boolean isOpenUnionInstance() {
		return openUnionInstance;
	}

	public void setOpenUnionInstance(boolean isOpenUnionInstance) {
		this.openUnionInstance = isOpenUnionInstance;
	}

	public boolean isOpenCSArena() {
		return openCSArena;
	}

	public void setOpenCSArena(boolean isOpenCSArena) {
		this.openCSArena = isOpenCSArena;
	}

	public int getEnableUnionInsCheck() {
		return enableUnionInsCheck;
	}

	public void setEnableUnionInsCheck(int enableUnionInsCheck) {
		this.enableUnionInsCheck = enableUnionInsCheck;
	}
	
    public int getEnableRealTimeTitle() {
        return enableRealTimeTitle;
    }
    
    public void setEnableRealTimeTitle(int enableRealTimeTitle) {
        this.enableRealTimeTitle = enableRealTimeTitle;
    }

    public int getEnableMainPVECheck() {
		return enableMainPVECheck;
	}

	public void setEnableMainPVECheck(int enableMainPVECheck) {
		this.enableMainPVECheck = enableMainPVECheck;
	}

	public int getEnableBossCheck() {
		return enableBossCheck;
	}

	public void setEnableBossCheck(int enableBossCheck) {
		this.enableBossCheck = enableBossCheck;
	}

	public int getEnableEliteCheck() {
		return enableEliteCheck;
	}

	public void setEnableEliteCheck(int enableEliteCheck) {
		this.enableEliteCheck = enableEliteCheck;
	}

	public int getEnableNightMareCheck() {
		return enableNightMareCheck;
	}

	public void setEnableNightMareCheck(int enableNightMareCheck) {
		this.enableNightMareCheck = enableNightMareCheck;
	}

	public int getEnableSpecialCheck() {
		return enableSpecialCheck;
	}

	public void setEnableSpecialCheck(int enableSpecialCheck) {
		this.enableSpecialCheck = enableSpecialCheck;
	}

	public int getEnableTeamCheck() {
		return enableTeamCheck;
	}

	public void setEnableTeamCheck(int enableTeamCheck) {
		this.enableTeamCheck = enableTeamCheck;
	}

	public int getEnableTowerCheck() {
		return enableTowerCheck;
	}

	public void setEnableTowerCheck(int enableTowerCheck) {
		this.enableTowerCheck = enableTowerCheck;
	}

	public String getRedisRealTimeHost() {
		return redisRealTimeHost;
	}

	public void setRedisRealTimeHost(String redisRealTimeHost) {
		this.redisRealTimeHost = redisRealTimeHost;
	}

	public int getRedisRealTimePort() {
		return redisRealTimePort;
	}

	public void setRedisRealTimePort(int redisRealTimePort) {
		this.redisRealTimePort = redisRealTimePort;
	}

	public String getRedisRealTimePassword() {
		return redisRealTimePassword;
	}

	public void setRedisRealTimePassword(String redisRealTimePassword) {
		this.redisRealTimePassword = redisRealTimePassword;
	}

	public int getRedisRealTimeMaxIdle() {
		return redisRealTimeMaxIdle;
	}

	public void setRedisRealTimeMaxIdle(int redisRealTimeMaxIdle) {
		this.redisRealTimeMaxIdle = redisRealTimeMaxIdle;
	}

	public int getRedisRealTimeMaxTotal() {
		return redisRealTimeMaxTotal;
	}

	public void setRedisRealTimeMaxTotal(int redisRealTimeMaxTotal) {
		this.redisRealTimeMaxTotal = redisRealTimeMaxTotal;
	}

	public int getRedisRealTimeMaxWaitMillis() {
		return RedisRealTimeMaxWaitMillis;
	}

	public void setRedisRealTimeMaxWaitMillis(int redisRealTimeMaxWaitMillis) {
		RedisRealTimeMaxWaitMillis = redisRealTimeMaxWaitMillis;
	}
	
    public String getRedisBattleReportHost() {
        return redisBattleReportHost;
    }
    
    public void setRedisBattleReportHost(String redisBattleReportHost) {
        this.redisBattleReportHost = redisBattleReportHost;
    }

    public int getRedisBattleReportPort() {
        return redisBattleReportPort;
    }
    
    public void setRedisBattleReportPort(int redisBattleReportPort) {
        this.redisBattleReportPort = redisBattleReportPort;
    }
    
    public String getRedisBattleReportPassword() {
        return redisBattleReportPassword;
    }
    
    public void setRedisBattleReportPassword(String redisBattleReportPassword) {
        this.redisBattleReportPassword = redisBattleReportPassword;
    }
    
    public int getRedisBattleReportMaxIdle() {
        return redisBattleReportMaxIdle;
    }
    
    public void setRedisBattleReportMaxIdle(int redisBattleReportMaxIdle) {
        this.redisBattleReportMaxIdle = redisBattleReportMaxIdle;
    }

    public int getRedisBattleReportMaxTotal() {
        return redisBattleReportMaxTotal;
    }

    public void setRedisBattleReportMaxTotal(int redisBattleReportMaxTotal) {
        this.redisBattleReportMaxTotal = redisBattleReportMaxTotal;
    }
    
    public int getRedisBattleReportMaxWaitMillis() {
        return redisBattleReportMaxWaitMillis;
    }
    
    public void setRedisBattleReportMaxWaitMillis(int redisBattleReportMaxWaitMillis) {
        this.redisBattleReportMaxWaitMillis = redisBattleReportMaxWaitMillis;
    }

    public int getRpcListenPort() {
		return rpcListenPort;
	}

	public void setRpcListenPort(int rpcListenPort) {
		this.rpcListenPort = rpcListenPort;
	}

	public String getCsRedisHost() {
		return csRedisHost;
	}

	public void setCsRedisHost(String csRedisHost) {
		this.csRedisHost = csRedisHost;
	}

	public int getCsRedisPort() {
		return csRedisPort;
	}

	public void setCsRedisPort(int csRedisPort) {
		this.csRedisPort = csRedisPort;
	}

	public String getCsDbReplicaSet() {
		return csDbReplicaSet;
	}

	public void setCsDbReplicaSet(String csDbReplicaSet) {
		this.csDbReplicaSet = csDbReplicaSet;
	}

	public String getCsDbCluster() {
		return csDbCluster;
	}

	public void setCsDbCluster(String csDbCluster) {
		this.csDbCluster = csDbCluster;
	}

	public String getCsDbName() {
		return csDbName;
	}

	public void setCsDbName(String csDbName) {
		this.csDbName = csDbName;
	}

	public int getEnableEsoteric() {
		return enableEsoteric;
	}

	public void setEnableEsoteric(int enableEsoteric) {
		this.enableEsoteric = enableEsoteric;
	}

	public int getEnableHolidayCheck() {
		return enableHolidayCheck;
	}

	public void setEnableHolidayCheck(int enableHolidayCheck) {
		this.enableHolidayCheck = enableHolidayCheck;
	}
	
    public int getEnableReincarnationCheck() {
        return enableReincarnationCheck;
    }
    
    public void setEnableReincarnationCheck(int enableReincarnationCheck) {
        this.enableReincarnationCheck = enableReincarnationCheck;
    }

    public int getRpcMaxConns() {
		return rpcMaxConns;
	}

	public void setRpcMaxConns(int rpcMaxConns) {
		this.rpcMaxConns = rpcMaxConns;
	}

	public int getEnableSkin() {
		return enableSkin;
	}

	public void setEnableSkin(int enableSkin) {
		this.enableSkin = enableSkin;
	}

	public boolean isEnableBiography() {
		return enableBiography;
	}

	public void setEnableBiography(boolean enableBiography) {
		this.enableBiography = enableBiography;
	}
	
    public boolean isEnableEightDoor() {
		return enableEightDoor;
	}

	public void setEnableEightDoor(boolean enableEightDoor) {
		this.enableEightDoor = enableEightDoor;
	}

	public int getQueuePushNum() {
        return queuePushNum;
    }

	public void setQueuePushNum(int queuePushNum) {
		this.queuePushNum = queuePushNum;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public static List<String> getSpecialAnno() {
		return specialAnno;
	}

	public static void setSpecialAnno(List<String> specialAnno) {
		GSRuntimeConfig.specialAnno = specialAnno;
	}

	public boolean isOpenCSCom() {
		return openCSCom;
	}

	public void setOpenCSCom(boolean openCSCom) {
		this.openCSCom = openCSCom;
	}

    public boolean isOpenCSSports() {
        return openCSSports;
    }

    public void setOpenCSSports(boolean openCSSports) {
        this.openCSSports = openCSSports;
    }

    public int getServerChatPort() {
        return serverChatPort;
    }

    public void setServerChatPort(int serverChatPort) {
        this.serverChatPort = serverChatPort;
    }

    public boolean isOpenCSClub() {
        return openCSClub;
    }

    public void setOpenCSClub(boolean openCSClub) {
        this.openCSClub = openCSClub;
    }

	public String getWorldServerIp() {
		return worldServerIp;
	}

	public void setWorldServerIp(String worldServerIp) {
		this.worldServerIp = worldServerIp;
	}

	public int getWorldServerPort() {
		return worldServerPort;
	}

	public void setWorldServerPort(int worldServerPort) {
		this.worldServerPort = worldServerPort;
	}

    public String getRealComKey() {
        return realComKey;
    }

    public void setRealComKey(String realComKey) {
        this.realComKey = realComKey;
    }

    public boolean isRealComRun() {
        return realComRun;
    }

    public void setRealComRun(boolean realComRun) {
        this.realComRun = realComRun;
    }

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public boolean isPayType() {
		return this.payType == 0;
	}

	public int getCombatSuppress() {
		return combatSuppress;
	}

	public void setCombatSuppress(int combatSuppress) {
		this.combatSuppress = combatSuppress;
	}

	public boolean isOpenFianlWar() {
		return openFianlWar;
	}

	public void setOpenFianlWar(boolean openFianlWar) {
		this.openFianlWar = openFianlWar;
	}

	public List<String> fetchAllMergeSections() {
		List<String> secs = Lists.newArrayList();

		//TODO 没有合服
		//JSONArray mergeSections = GSRuntimeConfig.instance.getMergeSections();
        //
		//for (int i = 0; i < mergeSections.size(); i++)
		//	secs.add(mergeSections.getString(i));

		if (secs.isEmpty()) {
			secs.add(GSRuntimeConfig.instance.getServerId());
		}

		return secs;
	}

	public int getEnablePeakSports() {
		return enablePeakSports;
	}

	public void setEnablePeakSports(int enablePeakSports) {
		this.enablePeakSports = enablePeakSports;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public boolean isEnableLaboratory() {
		return enableLaboratory;
	}

	public void setEnableLaboratory(boolean enableLaboratory) {
		this.enableLaboratory = enableLaboratory;
	}

	public boolean isEnableIntervene() {
		return enableIntervene;
	}

	public void setEnableIntervene(boolean enableIntervene) {
		this.enableIntervene = enableIntervene;
	}
}

