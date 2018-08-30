package cc.thevsk.services;

import cc.thevsk.interceptor.NoCqInterceptor;
import cc.thevsk.interceptor.NoSudoInterceptor;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
import top.thevsk.annotation.BotMessage;
import top.thevsk.annotation.BotService;
import top.thevsk.annotation.BotServiceAop;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.enums.MessageType;
import top.thevsk.utils.CQUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@BotService
public class TestService {

    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:!echo")
    @BotServiceAop({NoCqInterceptor.class, NoSudoInterceptor.class})
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
        try {
            String content = request.getMessage().trim();
            if (content.contains("CQ:image")) {
                content = CQUtils.getUrlInCqImage(content)[0];
            } else if (content.contains("CQ:")) {
                response.replyAt("不支持的格式");
                return;
            }
            String path = "http://thevsk.cc:7500/qr?content=" + URLEncoder.encode(content, "utf-8") + "&t=_.jpg";
            response.reply(CQUtils.image(path));
        } catch (UnsupportedEncodingException e) {
            response.replyAt(e.getMessage());
        }
    }
}