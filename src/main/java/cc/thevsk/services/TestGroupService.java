package cc.thevsk.services;

import cc.thevsk.entity.Constants;
import cc.thevsk.entity.Talk;
import cc.thevsk.interceptor.TestGroupInterceptor;
import cc.thevsk.utils.ParamsUtils;
import com.jfinal.ext.kit.DateKit;
import com.jfinal.kit.StrKit;
import top.thevsk.annotation.BotMessage;
import top.thevsk.annotation.BotService;
import top.thevsk.annotation.BotServiceAop;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.enums.MessageType;
import top.thevsk.utils.CQUtils;

import java.util.Date;

@BotService
@BotServiceAop(TestGroupInterceptor.class)
public class TestGroupService {


    @BotMessage(messageType = MessageType.GROUP, filter = "eq:test|userId:2522534416")
    public void reply(ApiRequest request, ApiResponse response) {
        response.replyAt("reply,at:" + DateKit.toStr(new Date(), DateKit.timeStampPattern));
    }

    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:addTalk")
    public void addTalk(ApiRequest request, ApiResponse response) {
        try {
            ParamsUtils paramsUtils = new ParamsUtils(request.getMessage());
            String help = paramsUtils.getValueTrim("help", "h");
            if (help != null) {
                String reply = "";
                reply += "addTalk";
                reply += "\r\n";
                reply += "    -message -m [必填] #触发回复的内容";
                reply += "\r\n";
                reply += "    -type -t [默认值:e] #触发回复的方式，可选 contains/c equals/e startsWith/sw endsWith/ew";
                reply += "\r\n";
                reply += "    -reply -r [必填] #回复内容，用{@u}可以艾特正在交谈的用户";
                reply += "\r\n";
                reply += "    -privateUser -pu #QQ号，只回复这个QQ发出的消息";
                reply += "\r\n";
                reply += "    -help -h #帮助";
                response.reply(reply);
                return;
            }
            String message = paramsUtils.getValueTrim("message", "m");
            String type = paramsUtils.getValueTrim("type", "t", "e");
            Talk.Type typeEnum = null;
            String reply = paramsUtils.getValueTrim("reply", "r");
            String privateUser = paramsUtils.getValueTrim("privateUser", "pu");
            Long privateUserLong = null;
            if (StrKit.isBlank(message) || StrKit.isBlank(reply)) {
                response.replyAt("必填项为空，帮助：addTalk -h");
                return;
            }
            if (!StrKit.isBlank(privateUser)) {
                try {
                    privateUserLong = Long.valueOf(privateUser);
                } catch (Exception e) {
                    response.replyAt("privateUser 转换 Long 失败，帮助：addTalk -h");
                    return;
                }
            }
            switch (type) {
                case "contains":
                case "c":
                    typeEnum = Talk.Type.contains;
                    break;
                case "equals":
                case "e":
                    typeEnum = Talk.Type.equals;
                    break;
                case "startsWith":
                case "sw":
                    typeEnum = Talk.Type.startsWith;
                    break;
                case "endsWith":
                case "ew":
                    typeEnum = Talk.Type.endsWith;
                    break;
                default:
                    response.replyAt("type 转换 Enum 失败，帮助：addTalk -h");
                    return;
            }
            Talk talk = new Talk();
            talk.setMessage(message);
            talk.setReply(reply);
            talk.setPrivateUser(privateUserLong);
            talk.setType(typeEnum);
            Constants.talks.add(talk);
        } catch (Exception e) {
            response.replyAt("发生错误，帮助：addTalk -h");
        }
    }

    @BotMessage(messageType = MessageType.GROUP)
    public void withTalk(ApiRequest request, ApiResponse response) {
        for (Talk talk : Constants.talks) {
            switch (talk.getType()) {
                case equals:
                    if (request.getMessage().equals(talk.getMessage())) {
                        talkTo(talk, request, response);
                    }
                    break;
                case contains:
                    if (request.getMessage().contains(talk.getMessage())) {
                        talkTo(talk, request, response);
                    }
                    break;
                case endsWith:
                    if (request.getMessage().equals(talk.getMessage())) {
                        talkTo(talk, request, response);
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
