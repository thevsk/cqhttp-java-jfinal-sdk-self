package cc.thevsk.services;

import top.thevsk.annotation.BotMessage;
import top.thevsk.annotation.BotService;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.enums.MessageType;

@BotService
public class PrivateService {

    @BotMessage(messageType = MessageType.PRIVATE)
    public void menu(ApiRequest request, ApiResponse response) {
        /*String message = request.getMessage();
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
        */
        String menu = "";
        menu += "使用帮助：";
        menu += "\r\n";
        menu += "-舰娘名称查询舰娘 例：-新月（请尽量使用和谐前名称）";
        menu += "\r\n";
        menu += "-关卡查询关卡掉落 例：-3-4";
        menu += "\r\n";
        menu += "暂无其他功能";
        menu += "\r\n";
        menu += "注：小歌主要功能碧蓝航线游戏资料查询 主人随缘上线同意加群请求 有事请在QQ空间说说下留言 不定时查看";
        response.reply(menu);
    }

    /*@BotMessage(messageType = MessageType.PRIVATE, filter = "eq:1")
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
    }*/
}
