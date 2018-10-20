package cc.thevsk.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
import top.thevsk.annotation.BotMessage;
import top.thevsk.annotation.BotService;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.enums.MessageType;

import java.util.HashMap;
import java.util.Map;

import static cc.thevsk.entity.Constants.dbCache;

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

    @BotMessage(messageType = MessageType.GROUP)
    public void godSearch(ApiRequest request, ApiResponse response) {
        if (!request.getMessage().startsWith("-")) {
            return;
        }
        try {
            Map<String, Object> objectMap = dbCache.executeQueryMap("select * from searchGroupId where groupId = ? ", request.getGroupId().toString());
            String godName = request.getMessage().substring(1);
            String result = HttpKit.post(jsoupUrl + "/arkOrder/search", new HashMap<String, String>() {
                {
                    put("name", godName);
                }
            }, null, headers);
            JSONObject jsonObject = JSON.parseObject(result);
            if (jsonObject.getInteger("code") == 200) {
                if (objectMap != null) {
                    if (objectMap.get("type").toString().equals("fzzl")) {
                        response.replyList(jsonObject.getString("data"));
                    }
                }
            } else {
                if (objectMap != null) {
                    if (objectMap.get("type").toString().equals("fzzl")) {
                        response.reply(jsonObject.getString("data"));
                    }
                }
            }
        } catch (Exception e) {
            response.replyText("error");
        }
    }
}
