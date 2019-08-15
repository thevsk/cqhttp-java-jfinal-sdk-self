package cc.thevsk.services;

import cc.thevsk.entity.Constants;
import cc.thevsk.entity.Talk;
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
        try {
            int min = 1;
            int max = Integer.valueOf(request.getMessage().trim()) + 1;
            int res = new Random().nextInt(max - min) + min;
            response.replyAt(res + "");
        } catch (Exception e) {
            e.getMessage();
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

    @BotMessage(messageType = MessageType.GROUP, filter = "userId:1000000")
    public void banTest(ApiRequest request, ApiResponse response) {
        String msg = request.getMessage();
        if (msg.contains(request.getSelfId().toString()) && msg.contains("被管理员禁言") && (msg.contains("天") || msg.contains("小时"))) {
            response.leave();
            response.replyPrivate("由于被禁言而退群，群:" + request.getGroupId(), 2522534416L);
        }
    }

    @BotMessage(messageType = MessageType.GROUP)
    public void withTalk(ApiRequest request, ApiResponse response) {
        for (Talk talk : Constants.talks) {
            if (talk == null) continue;
            switch (talk.getType()) {
                case equals:
                    if (request.getMessage().equals(talk.getMessage())) {
                        talkTo(talk, request, response);
                    }
                    break;
                case contains:
                    if (request.getMessage().contains(talk.getMessage())) {
                        // talkTo(talk, request, response);
                    }
                    break;
                case startsWith:
                    if (request.getMessage().startsWith(talk.getMessage())) {
                        talkTo(talk, request, response);
                    }
                    break;
            }
        }
    }

    private void talkTo(Talk talk, ApiRequest request, ApiResponse response) {
        if (talk.getPrivateUser() != null) {
            if (talk.getPrivateUser().equals(request.getUserId())) {
                response.reply(talk.getReply().replace("{@u}", CQUtils.at(request.getUserId())));
            }
        } else {
            response.reply(talk.getReply().replace("{@u}", CQUtils.at(request.getUserId())));
        }
    }
}