package cc.thevsk.config;

import cc.thevsk.entity.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author thevsk
 * @Title: AfterServerStarted
 * @ProjectName cqhttp-java-jfinal-sdk
 * @date 2018-08-16 13:41
 */
public class AfterServerStarted {

    public static void todo() {
        Constants.repeat2Last = new HashMap<>();
        Constants.talks = new LinkedList<>();
    }
}
