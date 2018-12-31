package cc.thevsk.services;

import top.thevsk.annotation.BotMessage;
import top.thevsk.annotation.BotService;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.enums.MessageType;
import top.thevsk.utils.CQUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@BotService
public class TestService {

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