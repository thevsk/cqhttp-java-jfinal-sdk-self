package cc.thevsk.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.StrKit;
import top.thevsk.annotation.BotMessage;
import top.thevsk.annotation.BotService;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.enums.MessageType;

import java.util.HashMap;

/**
 * @author thevsk
 * @Title: CacheService
 * @ProjectName cqhttp-java-jfinal-sdk
 * @date 2018-08-16 13:58
 */
@BotService
public class JsoupService {

    private static final String jsoupUrl = "http://127.0.0.1:7651";

    private static final HashMap<String, String> headers = new HashMap<String, String>() {
        {
            put("Authorization", "unKnow:thevsk");
            put("Content-Type", "application/x-www-form-urlencoded");
        }
    };

    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:-,—,―")
    public void azurLaneSearch(ApiRequest request, ApiResponse response) {
        String result = HttpKit.post(jsoupUrl + "/azurLane/search", new HashMap<String, String>() {
            {
                put("name", request.getMessage().trim());
            }
        }, null, headers);
        JSONObject jsonObject = JSON.parseObject(result);
        if (jsonObject.getInteger("code") == 200 || jsonObject.getInteger("code") == 500) {
            if (!StrKit.isBlank(jsonObject.getString("data"))) {
                response.replyList(jsonObject.getString("data"));
            }
        }
    }
}
