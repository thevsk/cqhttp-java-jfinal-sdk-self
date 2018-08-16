package cc.thevsk.entity;

import cc.thevsk.utils.SQLiteUtils;
import top.thevsk.start.JettyStart;

/**
 * @author thevsk
 * @Title: Constants
 * @ProjectName cqhttp-java-jfinal-sdk
 * @date 2018-08-16 13:44
 */
public class Constants {

    private static final String dbPath = JettyStart.getStartPath() + JettyStart.separator + "database" + JettyStart.separator;

    public static final String dbPathForCache = dbPath + "cache.db";

    public static SQLiteUtils dbCache;
}
