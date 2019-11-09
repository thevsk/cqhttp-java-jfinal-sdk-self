package cc.thevsk.services;

import top.thevsk.annotation.BotMessage;
import top.thevsk.annotation.BotService;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.enums.MessageType;
import top.thevsk.utils.CQUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

@BotService
public class TestService {

    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:ROLL,roll")
    public void roll(ApiRequest request, ApiResponse response) {
        int min = 1;
        int max = Integer.parseInt(request.getMessage().trim()) + 1;
        int res = new Random().nextInt(max - min) + min;
        response.replyAt(res + "");
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

    @BotMessage(messageType = MessageType.GROUP, filter = "userId:1000000")
    public void banTest(ApiRequest request, ApiResponse response) {
        String msg = request.getMessage();
        if (msg.contains(request.getSelfId().toString()) && msg.contains("被管理员禁言") && (msg.contains("天"))) {
            response.leave();
            response.replyPrivate("由于被禁言而退群，群:" + request.getGroupId(), 2522534416L);
        }
    }

    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:#")
    public void check(ApiRequest request, ApiResponse response) {
        if (request.getMessage().equals("查询二次元浓度")) {
            response.reply("二次元浓度：6657%");
        }
    }
}