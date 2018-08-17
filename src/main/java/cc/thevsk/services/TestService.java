package cc.thevsk.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
import top.thevsk.annotation.BotMessage;
import top.thevsk.annotation.BotService;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.enums.MessageType;
import top.thevsk.utils.CQUtils;

@BotService
public class TestService {

    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:!echo")
    public void say(ApiRequest request, ApiResponse response) {
        response.reply(request.getMessage().trim());
    }

    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:!R")
    public void acg(ApiRequest request, ApiResponse response) {
        JSONObject jsonObject = JSON.parseObject((HttpKit.get("http://acg.bakayun.cn/randbg.php?Type=json")));
        response.reply(CQUtils.image(jsonObject.getString("ImgUrl")));
    }

    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:!U")
    public void getUrl(ApiRequest request, ApiResponse response) {
        try {
            response.reply(CQUtils.getUrlInCqImage(request.getMessage().trim())[0]);
        } catch (Exception e) {
            response.replyAt(e.getMessage());
        }
    }

    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:!qr")
    public void qr(ApiRequest request, ApiResponse response) {
        String path = "http://119.27.170.163:7500/qr/" + request.getMessage().trim() + "?t=_.jpg";
        response.reply(CQUtils.image(path));
    }
}