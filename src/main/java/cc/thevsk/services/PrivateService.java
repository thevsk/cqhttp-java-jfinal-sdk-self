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
        menu += "使用帮助和其他说明：";
        menu += "\r\n";
        menu += "1. -舰娘名称查询舰娘 例：-新月（请尽量使用和谐前名称）";
        menu += "\r\n";
        menu += "2. -关卡查询关卡掉落 例：-3-4";
        menu += "\r\n";
        menu += "3. 所有功能仅在QQ群内起作用";
        menu += "\r\n";
        menu += "4. 主要功能碧蓝航线游戏资料查询";
        menu += "\r\n";
        menu += "5. 如何让小歌进群：加小歌为好友，然后在群内邀请好友入群";
        menu += "\r\n";
        menu += "6. 加群并不是即时同意的，还需要master筛选";
        menu += "\r\n";
        menu += "7. 意义不明的群不会同意";
        menu += "\r\n";
        menu += "8. 5人以下的群不会同意";
        menu += "\r\n";
        menu += "9. 碧蓝航线游戏群或舰队群必定同意";
        menu += "\r\n";
        menu += "10. 其他游戏交流群/综合游戏交流群酌情考虑";
        menu += "\r\n";
        menu += "11. master随缘上线同意加群请求";
        menu += "\r\n";
        menu += "12. 随缘的意思就是随缘，但不会很慢，周末休息";
        menu += "\r\n";
        menu += "13. 免费";
        menu += "\r\n";
        menu += "14. 禁言小歌会导致小歌主动退群";
        menu += "\r\n";
        menu += "15. master：2522534416，提交BUG，建议，意见可加";
        menu += "\r\n";
        menu += "16. 想到再补";
        response.reply(menu);
    }
}
