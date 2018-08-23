package cc.thevsk.services;

import cc.thevsk.interceptor.MasterInterceptor;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;
import top.thevsk.annotation.BotMessage;
import top.thevsk.annotation.BotRequest;
import top.thevsk.annotation.BotService;
import top.thevsk.annotation.BotServiceAop;
import top.thevsk.api.ApiGet;
import top.thevsk.api.ApiSet;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.enums.MessageType;
import top.thevsk.enums.RequestType;
import top.thevsk.utils.CQUtils;

@BotService
public class RequestService {

    @BotRequest(requestType = RequestType.GROUP)
    public void group(ApiRequest request, ApiResponse response) {
        if (request.getSubType().equals("invite")) {
            String groupImage = CQUtils.image("https://p.qlogo.cn/gh/" + request.getGroupId().toString() + "/" + request.getGroupId().toString() + "/100");
            String userImage = CQUtils.image("https://q1.qlogo.cn/g?b=qq&nk=" + request.getUserId().toString() + "&s=100");
            StringBuilder string = new StringBuilder();
            string.append("收到加群请求");
            string.append("\n");
            string.append("群号：");
            string.append(request.getGroupId().toString());
            string.append("\n");
            string.append("群头像：");
            string.append(groupImage);
            response.replyPrivate(string.toString(), 2522534416L);
            JSONObject userInfo = ApiGet.getStrangerInfo(request.getUserId(), true).getData();
            StringBuilder string2 = new StringBuilder();
            string2.append("邀请人信息");
            string2.append("\n");
            string2.append("QQ号：");
            string2.append(request.getUserId().toString());
            string2.append("\n");
            string2.append("昵称：");
            string2.append(userInfo.get("nickname"));
            string2.append("\n");
            string2.append("性别：");
            string2.append(userInfo.get("sex"));
            string2.append("\n");
            string2.append("头像：");
            string2.append(userImage);
            response.replyPrivate(string2.toString(), 2522534416L);
            response.replyPrivate("FLAG：", 2522534416L);
            response.replyPrivate(request.getFlag(), 2522534416L);
        }
    }

    @BotServiceAop(MasterInterceptor.class)
    @BotMessage(messageType = MessageType.PRIVATE, filter = "startWith:FLAG")
    public void addGroup(ApiRequest request, ApiResponse response) {
        String flag = request.getMessage().trim();
        if (StrKit.isBlank(flag)) {
            response.reply("空FLAG");
            return;
        }
        response.reply(ApiSet.setGroupAddRequest(flag, "invite", true, null).toString());
    }
}