package cc.thevsk.services;

import top.thevsk.annotation.BotMessage;
import top.thevsk.annotation.BotService;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.enums.MessageType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@BotService
public class PrivateService {

    @BotMessage(messageType = MessageType.PRIVATE)
    public void menu(ApiRequest request, ApiResponse response) {
        String message = request.getMessage();
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(message);
        if (isNum.matches()) {
            return;
        }
        String menu = "";
        menu += "你找小歌有什么事呢？";
        menu += "\r\n";
        menu += "联系小歌的主人请回复1";
        menu += "\r\n";
        menu += "想加小歌好友请回复2";
        menu += "\r\n";
        menu += "想拉小歌到别的QQ群请回复3";
        menu += "\r\n";
        menu += "想查看小歌源码请回复4";
        menu += "\r\n";
        menu += "想查看游戏信息查询功能源码请回复5";
        response.reply(menu);
    }

    @BotMessage(messageType = MessageType.PRIVATE, filter = "eq:1")
    public void action1(ApiRequest request, ApiResponse response) {
        String msg = "";
        msg += "小歌的主人QQ号： 2522534416";
        response.reply(msg);
    }

    @BotMessage(messageType = MessageType.PRIVATE, filter = "eq:2")
    public void action2(ApiRequest request, ApiResponse response) {
        String msg = "";
        msg += "功能未开发，请回复1";
        response.reply(msg);
    }

    @BotMessage(messageType = MessageType.PRIVATE, filter = "eq:3")
    public void action3(ApiRequest request, ApiResponse response) {
        String msg = "";
        msg += "功能未开发，请回复1";
        response.reply(msg);
    }

    @BotMessage(messageType = MessageType.PRIVATE, filter = "eq:4")
    public void action4(ApiRequest request, ApiResponse response) {
        String msg = "";
        msg += "GitHub：[ https://github.com/thevsk/cqhttp-java-jfinal-sdk-self ]";
        response.reply(msg);
    }

    @BotMessage(messageType = MessageType.PRIVATE, filter = "eq:5")
    public void action5(ApiRequest request, ApiResponse response) {
        String msg = "";
        msg += "GitHub：[ https://github.com/thevsk/thevsk-jsoup ]";
        response.reply(msg);
    }
}
