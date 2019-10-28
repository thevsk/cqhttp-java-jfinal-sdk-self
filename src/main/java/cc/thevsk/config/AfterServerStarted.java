package cc.thevsk.config;

import top.thevsk.entity.Constants;
import top.thevsk.start.JettyStart;
import top.thevsk.utils.SQLiteUtils;

import java.util.ArrayList;

/**
 * @author thevsk
 * @Title: AfterServerStarted
 * @ProjectName cqhttp-java-jfinal-sdk
 * @date 2018-08-16 13:41
 */
public class AfterServerStarted {

    public static void todo() {
        try {
            Constants.database = new SQLiteUtils(JettyStart.getStartPath() + JettyStart.separator + "database.db");
            Constants.database.createTable("usage_statistics", new ArrayList<SQLiteUtils.TableColumn>() {
                {
                    add(new SQLiteUtils.TableColumn("group_id"));
                    add(new SQLiteUtils.TableColumn("user_id"));
                    add(new SQLiteUtils.TableColumn("command"));
                    add(new SQLiteUtils.TableColumn("create_time"));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
