package cc.thevsk.utils;

import cc.thevsk.entity.Constants;
import com.jfinal.kit.StrKit;

/**
 * @author thevsk
 * @Title: ParamsUtils
 * @ProjectName cqhttp-java-jfinal-sdk
 * @date 2019-08-15 10:26
 */
public class ParamsUtils {

    private String[] params;

    public ParamsUtils(String string) throws Exception {
        if (StrKit.isBlank(string)) {
            throw new Exception("ParamsUtils: null string");
        }
        this.params = string.split(Constants.PARAM_SPLIT);
    }

    public String getValue(String p) {
        String value = null;
        for (String param : params) {
            if (param.startsWith(p)) {
                value = param;
                break;
            }
        }
        if (value == null) {
            return null;
        }
        return value.substring(p.length(), value.length());
    }

    public String getValueTrim(String p) {
        String value = getValue(p);
        if (value == null) {
            return null;
        }
        return value.trim();
    }

    public String getValueTrim(String p1, String p2) {
        String value = getValueTrim(p1);
        if (value == null) {
            value = getValueTrim(p2);
        }
        return value;
    }

    public String getValueTrim(String p1, String p2, String def) {
        String value = getValueTrim(p1, p2);
        if (value == null) {
            return def;
        }
        return value;
    }
}
