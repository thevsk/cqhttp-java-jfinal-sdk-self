package cc.thevsk.services;

import top.thevsk.annotation.BotMessage;
import top.thevsk.annotation.BotService;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.enums.MessageType;

import static cc.thevsk.entity.Constants.dbCache;

/**
 * @author thevsk
 * @Title: CacheService
 * @ProjectName cqhttp-java-jfinal-sdk
 * @date 2018-08-16 13:58
 */
@BotService
public class CacheService {

    @BotMessage(messageType = MessageType.GROUP, filter = "eq:添加碧蓝航线查询权限")
    public void blShipGroupIdAdd(ApiRequest request, ApiResponse response) {
        try {
            Object result = dbCache.executeQueryMap("select * from blShipGroupIds where groupId = ? ", request.getGroupId().toString());
            if (result != null) {
                response.reply("当前群已存在查询权限，若要移除权限请输入「解除碧蓝航线查询权限」");
                return;
            }
            dbCache.execute("insert into blShipGroupIds values (?);", request.getGroupId().toString());
            response.reply("碧蓝航线查询权限已启用，若要移除权限请输入「解除碧蓝航线查询权限」");
        } catch (Exception e) {
            response.reply(e.getMessage());
        }
    }

    @BotMessage(messageType = MessageType.GROUP, filter = "eq:解除碧蓝航线查询权限")
    public void blShipGroupIdDelete(ApiRequest request, ApiResponse response) {
        try {
            dbCache.execute("delete from blShipGroupIds where groupId = ? ", request.getGroupId().toString());
            response.reply("解除成功");
        } catch (Exception e) {
            response.reply(e.getMessage());
        }
    }
}
