package cc.thevsk.services;

import top.thevsk.annotation.BotNotice;
import top.thevsk.annotation.BotService;
import top.thevsk.api.ApiGet;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.entity.ReturnJson;
import top.thevsk.enums.NoticeType;
import top.thevsk.utils.CQUtils;

@BotService
public class WelcomeService {

    @BotNotice(noticeType = NoticeType.GROUP_INCREASE)
    public void welcome(ApiRequest request, ApiResponse response) {
        if (request.isSelf()) return;
        response.reply("欢迎 " + CQUtils.at(request.getUserId()) + " 加入本群！");
    }

    @BotNotice(noticeType = NoticeType.GROUP_DECREASE)
    public void leave(ApiRequest request, ApiResponse response) {
        if (request.isSelf()) return;
        ReturnJson returnJson = ApiGet.getStrangerInfo(request.getUserId(), false);
        response.reply("成员 " + returnJson.getData().get("nickname") + " 「" + request.getUserId() + "」 离开本群");
    }
}