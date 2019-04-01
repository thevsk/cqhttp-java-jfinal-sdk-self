package cc.thevsk.services;

import top.thevsk.annotation.BotService;

@BotService
public class RequestService {

    /*@BotRequest(requestType = RequestType.GROUP)
    public void group(ApiRequest request, ApiResponse response) {
        if (request.getSubType().equals("invite")) {
            String groupImage = CQUtils.image("https://p.qlogo.cn/gh/" + request.getGroupId().toString() + "/" + request.getGroupId().toString() + "/100");
            String userImage = CQUtils.image("https://q1.qlogo.cn/g?b=qq&nk=" + request.getUserId().toString() + "&s=100");
            JSONObject userInfo = ApiGet.getStrangerInfo(request.getUserId(), true).getData();
            StringBuilder string = new StringBuilder();
            string.append("收到加群请求");
            string.append("\n");
            string.append("群号：");
            string.append(request.getGroupId().toString());
            string.append("\n");
            string.append("群头像：");
            string.append(groupImage);
            string.append("\n");
            string.append("邀请人信息");
            string.append("\n");
            string.append("QQ号：");
            string.append(request.getUserId().toString());
            string.append("\n");
            string.append("昵称：");
            string.append(userInfo.get("nickname"));
            string.append("\n");
            string.append("性别：");
            string.append(userInfo.get("sex"));
            string.append("\n");
            string.append("头像：");
            string.append(userImage);
            string.append("\n");
            string.append("FLAG：");
            string.append(request.getFlag());
            response.replyPrivate(string.toString(), 2522534416L);
        }
    }

    @BotServiceAop(MasterInterceptor.class)
    @BotMessage(messageType = MessageType.PRIVATE, filter = "startWith:Y")
    public void addGroup(ApiRequest request, ApiResponse response) {
        String flag = request.getMessage().trim();
        if (StrKit.isBlank(flag)) {
            response.reply("空FLAG");
            return;
        }
        response.reply(ApiSet.setGroupAddRequest(flag, "invite", true, null).toString());
    }

    @BotServiceAop(MasterInterceptor.class)
    @BotMessage(messageType = MessageType.PRIVATE, filter = "startWith:N")
    public void noAddGroup(ApiRequest request, ApiResponse response) {
        String flag = request.getMessage().trim();
        if (StrKit.isBlank(flag)) {
            response.reply("空FLAG");
            return;
        }
        response.reply(ApiSet.setGroupAddRequest(flag, "invite", false, null).toString());
    }*/
}