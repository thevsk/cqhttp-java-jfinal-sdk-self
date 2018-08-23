package cc.thevsk.services;

import cc.thevsk.interceptor.MasterInterceptor;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.StrKit;
import top.thevsk.annotation.BotMessage;
import top.thevsk.annotation.BotService;
import top.thevsk.annotation.BotServiceAop;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.enums.MessageType;
import top.thevsk.utils.MessageUtils;

import java.util.HashMap;
import java.util.Map;

import static cc.thevsk.entity.Constants.dbCache;

/**
 * @author thevsk
 * @Title: RestfulService
 * @ProjectName cqhttp-java-jfinal-sdk
 * @date 2018-07-25 16:57
 */
@BotService
public class RestfulService {

    private static final String jFinalRestfulUrl = "http://119.27.170.163:7510";

    private static final HashMap<String, String> headers = new HashMap<String, String>() {
        {
            put("x-author", "thevsk");
            put("x-version", "1.0.0");
            put("x-user", "song");
            put("Content-Type", "application/x-www-form-urlencoded");
        }
    };

    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:-,—,―,￣,＿")
    public void shipSearch(ApiRequest request, ApiResponse response) {
        try {
            Object objectMap = dbCache.executeQueryMap("select * from blShipGroupIds where groupId = ? ", request.getGroupId().toString());
            String result = HttpKit.post(jFinalRestfulUrl + "/ship/search/" + request.getMessage().replace(" ", ""), null, headers);
            JSONObject jsonObject = JSON.parseObject(result);
            if (jsonObject.getInteger("code") == 200) {
                if (objectMap == null) {
                    response.reply("当前群没有碧蓝航线资料查询权限，若要添加权限请输入「添加碧蓝航线查询权限」");
                } else {
                    response.reply(jsonObject.getString("data"));
                }
            } else {
                if (objectMap != null) {
                    response.reply(jsonObject.getString("data"));
                }
            }
        } catch (Exception e) {
            response.reply(e.getMessage());
        }
    }

    @BotServiceAop(MasterInterceptor.class)
    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:!read")
    public void shipRead(ApiRequest request, ApiResponse response) {
        String result = HttpKit.post(jFinalRestfulUrl + "/ship/read/" + request.getMessage().trim(), null, null, headers);
        JSONObject jsonObject = JSON.parseObject(result);
        if (jsonObject.getInteger("code") != 200) {
            response.reply("请求接口返回错误，CODE：" + jsonObject.getInteger("code"));
        }
        response.reply(jsonObject.getString("data"));
    }

    @BotServiceAop(MasterInterceptor.class)
    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:!edit")
    public void shipEdit(ApiRequest request, ApiResponse response) {
        String[] str = request.getMessage().split("\r\n");
        String _ = "";
        for (int i = 0; i < str.length; i++) {
            if (i == 0) continue;
            if (i > 1) {
                _ += "\r\n";
            }
            _ += str[i];
        }
        String content = _;
        String result = HttpKit.post(jFinalRestfulUrl + "/ship/edit/" + str[0].trim(), new HashMap<String, String>() {
            {
                put("content", content);
            }
        }, null, headers);
        JSONObject jsonObject = JSON.parseObject(result);
        if (jsonObject.getInteger("code") != 200) {
            response.reply("请求接口返回错误，CODE：" + jsonObject.getInteger("code"));
        }
        response.reply(jsonObject.getString("data"));
    }

    @BotMessage(messageType = MessageType.PRIVATE, filter = "startWith:绑定禅道")
    public void qcBind(ApiRequest request, ApiResponse response) {
        try {
            Map<String, String> map = MessageUtils.parseMap(request.getMessage());
            String result = HttpKit.post(jFinalRestfulUrl + "/qc/bind/" + request.getUserId(), new HashMap<String, String>() {
                {
                    put("account", MessageUtils.getOrEx(map, "账号"));
                    put("password", MessageUtils.getOrEx(map, "密码"));
                }
            }, null, headers);
            JSONObject jsonObject = JSON.parseObject(result);
            if (jsonObject.getInteger("code") != 200) {
                response.reply("请求接口返回错误，CODE：" + jsonObject.getInteger("code"));
            }
            response.reply(jsonObject.getString("data"));
        } catch (Exception e) {
            response.reply("绑定失败，正确格式：「绑定禅道 账号:zhangsan 密码:123456」");
        }
    }

    @BotMessage(messageType = MessageType.PRIVATE, filter = "startWith:写日志")
    public void qcLog(ApiRequest request, ApiResponse response) {
        try {
            String[] strArray = request.getMessage().split("\r\n");
            if (strArray.length != 3) {
                response.reply("日志添加失败，正确格式：「\r\n写日志\r\n今天做了什么什么\r\n3\r\n」");
                return;
            }
            String work = strArray[1];
            String consumed = strArray[2];
            if (StrKit.isBlank(work) || StrKit.isBlank(consumed)) {
                response.reply("日志添加失败，正确格式：「\r\n写日志\r\n今天做了什么什么\r\n3\r\n」");
                return;
            }
            try {
                Integer.valueOf(consumed);
            } catch (NumberFormatException e) {
                response.reply("日志添加失败，第三行必须为整数");
                return;
            }
            String result = HttpKit.post(jFinalRestfulUrl + "/qc/addLog/" + request.getUserId(), new HashMap<String, String>() {
                {
                    put("work", work);
                    put("consumed", consumed);
                }
            }, null, headers);
            JSONObject jsonObject = JSON.parseObject(result);
            if (jsonObject.getInteger("code") != 200) {
                response.reply("请求接口返回错误，CODE：" + jsonObject.getInteger("code"));
            }
            response.reply(jsonObject.getString("data"));
        } catch (Exception e) {
            response.reply("日志添加失败，正确格式：「\r\n写日志\r\n今天做了什么什么\r\n3\r\n」");
        }
    }
}
