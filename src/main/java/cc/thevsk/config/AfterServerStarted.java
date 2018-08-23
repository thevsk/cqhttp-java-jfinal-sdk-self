package cc.thevsk.config;

import cc.thevsk.entity.Constants;
import cc.thevsk.utils.SQLiteUtils;
import com.jfinal.kit.LogKit;

import java.util.ArrayList;
import java.util.HashMap;

import static cc.thevsk.entity.Constants.dbCache;

/**
 * @author thevsk
 * @Title: AfterServerStarted
 * @ProjectName cqhttp-java-jfinal-sdk
 * @date 2018-08-16 13:41
 */
public class AfterServerStarted {

    public static void todo() {
        Constants.repeat2Last = new HashMap<>();
        dbCache();
    }

    private static void dbCache() {
        try {
            LogKit.info("[数据库] [cache] 加载");
            dbCache = new SQLiteUtils(Constants.dbPathForCache);
            LogKit.info("[数据库] [cache] 加载完成");
        } catch (Exception e) {
            e.printStackTrace();
            LogKit.error("[数据库] [cache] 加载失败");
            return;
        }
        try {
            dbCache.createTable("blShipGroupIds", new ArrayList<SQLiteUtils.TableColumn>() {
                {
                    add(new SQLiteUtils.TableColumn("groupId"));
                }
            });
            LogKit.info("[数据库] [cache] 创建表 blShipGroupIds");
        } catch (Exception e) {
            LogKit.info("[数据库] [cache] 已经创建过表 blShipGroupIds");
        }
        try {
            dbCache.createTable("RollRoom", new ArrayList<SQLiteUtils.TableColumn>() {
                {
                    add(new SQLiteUtils.TableColumn("id"));
                    add(new SQLiteUtils.TableColumn("groupId"));
                    add(new SQLiteUtils.TableColumn("roomName"));
                    add(new SQLiteUtils.TableColumn("createTime"));
                    add(new SQLiteUtils.TableColumn("masterUser"));
                    add(new SQLiteUtils.TableColumn("rollText"));
                    add(new SQLiteUtils.TableColumn("rollTime"));
                    add(new SQLiteUtils.TableColumn("getUser"));
                }
            });
            LogKit.info("[数据库] [cache] 创建表 RollRoom");
        } catch (Exception e) {
            LogKit.info("[数据库] [cache] 已经创建过表 RollRoom");
        }
        try {
            dbCache.createTable("RollRoomUser", new ArrayList<SQLiteUtils.TableColumn>() {
                {
                    add(new SQLiteUtils.TableColumn("roomId"));
                    add(new SQLiteUtils.TableColumn("userId"));
                    add(new SQLiteUtils.TableColumn("createTime"));
                }
            });
            LogKit.info("[数据库] [cache] 创建表 RollRoomUser");
        } catch (Exception e) {
            LogKit.info("[数据库] [cache] 已经创建过表 RollRoomUser");
        }
    }
}
