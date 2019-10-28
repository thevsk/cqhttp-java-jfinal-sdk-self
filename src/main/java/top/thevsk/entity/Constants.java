package top.thevsk.entity;

import org.lionsoul.ip2region.DbSearcher;
import top.thevsk.utils.SQLiteUtils;

import java.util.Set;

public class Constants {
    public static final String nextMsg = "NEXT_MSG";
    public static final String POST_TYPE_NOTICE = "notice";
    public static final String POST_TYPE_REQUEST = "request";
    public static final String POST_TYPE_MESSAGE = "message";
    public static Set<String> cacheOfIp = null;
    public static DbSearcher ip2regionSearcher;
    public static SQLiteUtils database;

    public static void doNothing() {
    }
}