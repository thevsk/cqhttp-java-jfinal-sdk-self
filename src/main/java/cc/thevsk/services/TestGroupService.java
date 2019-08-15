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

    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:addt")
    public void addt(ApiRequest request, ApiResponse response) {
        try {
            ParamsUtils paramsUtils = new ParamsUtils(request.getMessage());
            String help = paramsUtils.getValueTrim("help", "h");
            if (help != null) {
                String reply = "";
                reply += "addt 添加对话";
                reply += "\r\n";
                reply += "    -message -m [必填] #触发回复的内容";
                reply += "\r\n";
                reply += "    -type -t [默认值:e] #触发回复的方式，可选 contains/c equals/e startsWith/sw";
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
                response.replyAt("必填项为空，帮助：addt -h");
                return;
            }
            if (!StrKit.isBlank(privateUser)) {
                try {
                    privateUserLong = Long.valueOf(privateUser);
                } catch (Exception e) {
                    response.replyAt("privateUser 转换 Long 失败，帮助：addt -h");
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
                default:
                    response.replyAt("type 转换 Enum 失败，帮助：addt -h");
                    return;
            }
            Talk talk = new Talk();
            talk.setMessage(message);
            talk.setReply(reply);
            talk.setPrivateUser(privateUserLong);
            talk.setType(typeEnum);
            talk.setAuthor(request.getUserId());
            Constants.talks.add(talk);
            response.reply("success");
        } catch (Exception e) {
            response.replyAt("发生错误，帮助：addt -h");
        }
    }

    @BotMessage(messageType = MessageType.GROUP, filter = "eq:listt")
    public void listt(ApiRequest request, ApiResponse response) {
        StringBuilder reply = new StringBuilder();
        for (Talk talk : Constants.talks) {
            if (talk == null) continue;
            reply.append("\r\n");
            reply.append(" m:");
            reply.append(talk.getMessage());
            reply.append(" r:");
            reply.append(talk.getReply());
            reply.append(" t:");
            reply.append(talk.getType().toString());
            reply.append(" a:");
            reply.append(talk.getAuthor().toString());
        }
        response.reply(reply.toString());
    }

    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:deletet")
    public void deletet(ApiRequest request, ApiResponse response) {
        try {
            int count = 0;
            ParamsUtils paramsUtils = new ParamsUtils(request.getMessage());
            String help = paramsUtils.getValueTrim("help", "h");
            if (help != null) {
                String reply = "";
                reply += "deletet 删除对话";
                reply += "\r\n";
                reply += "    -message -m #模糊匹配 触发内容";
                reply += "\r\n";
                reply += "    -reply -r #模糊匹配 回复";
                reply += "\r\n";
                reply += "    -author -a #精准匹配 创建人";
                reply += "\r\n";
                reply += "    -help -h #帮助";
                response.reply(reply);
                return;
            }
            String message = paramsUtils.getValueTrim("message", "m");
            String reply = paramsUtils.getValueTrim("reply", "r");
            String author = paramsUtils.getValueTrim("author", "a");
            for (int i = 0; i < Constants.talks.size(); i++) {
                if (Constants.talks.get(i) == null) continue;
                Talk talk = Constants.talks.get(i);
                if (message != null && talk.getMessage().contains(message)) {
                    Constants.talks.remove(i);
                    count++;
                    continue;
                }
                if (reply != null && talk.getReply().contains(reply)) {
                    Constants.talks.remove(i);
                    count++;
                    continue;
                }
                if (author != null && talk.getAuthor().equals(Long.valueOf(author))) {
                    Constants.talks.remove(i);
                    count++;
                }
            }
            response.reply("delete count " + count);
        } catch (Exception e) {
            response.replyAt("发生错误，帮助：deletet -h");
        }
    }
}
