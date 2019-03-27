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
        String menu = "";
        menu += "使用帮助：";
        menu += "\r\n";
        menu += "-舰娘名称查询舰娘 例：-新月（请尽量使用和谐前名称）";
        menu += "\r\n";
        menu += "-关卡查询关卡掉落 例：-3-4";
        menu += "\r\n";
        menu += "暂无其他功能";
        menu += "\r\n";
        menu += "所有功能仅在群内生效";
        menu += "\r\n";
        menu += "注：小歌主要功能碧蓝航线游戏资料查询 开发者随缘上线同意加群请求";
        menu += "\r\n";
        menu += "开发者：2522534416，提交BUG，建议，意见可加";
        menu += "\r\n";
        menu += "请不要禁言小歌，这将导致小歌主动退群";
        response.reply(menu);
    }
}
