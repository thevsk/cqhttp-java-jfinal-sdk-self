package cc.thevsk.utils;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import top.thevsk.start.JettyStart;

import java.io.File;

/**
 * @author thevsk
 * @Title: TextUtils
 * @ProjectName cqhttp-java-jfinal-sdk
 * @date 2018-08-30 13:38
 */
public class TextUtils {

    private static Prop textProp = null;

    static {
        if (JettyStart.isJar()) {
            textProp = PropKit.use(new File(JettyStart.getStartPath() + JettyStart.separator + "text.properties"));
        } else {
            textProp = PropKit.use("text.properties");
        }
    }

    public static String get(String name) {
        return textProp.get(name, "");
    }
}
