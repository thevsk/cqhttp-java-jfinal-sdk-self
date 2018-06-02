package top.thevsk.services;

import top.thevsk.annotation.BotEvent;
import top.thevsk.annotation.BotService;
import top.thevsk.api.ApiGet;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.entity.ReturnJson;
import top.thevsk.enums.EventType;

@BotService
public class WelcomeService {

    @BotEvent(eventType = EventType.GROUP_INCREASE)
    public void welcome(ApiRequest request, ApiResponse response) {
        if (request.isSelf()) return;
        response.reply("欢迎 [CQ:at,qq=" + request.getUserId() + "] 加入本群！");
        if (request.getGroupId().equals(615524453L)) {
            ask(response);
        }
    }

    @BotEvent(eventType = EventType.GROUP_DECREASE)
    public void leave(ApiRequest request, ApiResponse response) {
        if (request.isSelf()) return;
        ReturnJson returnJson = ApiGet.getStrangerInfo(request.getUserId(), false);
        response.reply("成员 " + returnJson.getData().get("nickname") + " 「" + request.getUserId() + "」 离开本群");
    }

    private void ask(ApiResponse response) {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                response.replyAt("你是萌新嘛？");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}