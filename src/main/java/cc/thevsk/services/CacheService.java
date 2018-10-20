package cc.thevsk.services;

import top.thevsk.annotation.BotMessage;
import top.thevsk.annotation.BotService;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.enums.MessageType;

import java.util.Map;

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
            Map<String, Object> result = dbCache.executeQueryMap("select * from searchGroupId where groupId = ? ", request.getGroupId().toString());
            if (result != null) {
                if (result.get("type").toString().equals("fzzl")) {
                    response.replyText("god.authority.on");
                    return;
                }
                response.replyText("ship.authority.on");
                return;
            }
            dbCache.execute("insert into searchGroupId values (?, ?);", request.getGroupId().toString(), "blhx");
            response.replyText("ship.authority.on");
        } catch (Exception e) {
            response.replyText("error");
        }
    }

    @BotMessage(messageType = MessageType.GROUP, filter = "eq:解除碧蓝航线查询权限")
    public void blShipGroupIdDelete(ApiRequest request, ApiResponse response) {
        try {
            Map<String, Object> result = dbCache.executeQueryMap("select * from searchGroupId where groupId = ? ", request.getGroupId().toString());
            if (result != null) {
                if (result.get("type").toString().equals("fzzl")) {
                    response.replyText("god.authority.on");
                    return;
                }
                dbCache.execute("delete from searchGroupId where groupId = ?", request.getGroupId().toString());
                response.replyText("ship.authority.off");
            }
        } catch (Exception e) {
            response.reply(e.getMessage());
        }
    }

    @BotMessage(messageType = MessageType.GROUP, filter = "eq:添加方舟指令查询权限")
    public void fzGodGroupIdAdd(ApiRequest request, ApiResponse response) {
        try {
            Map<String, Object> result = dbCache.executeQueryMap("select * from searchGroupId where groupId = ? ", request.getGroupId().toString());
            if (result != null) {
                if (result.get("type").toString().equals("blhx")) {
                    response.replyText("ship.authority.on");
                    return;
                }
                response.replyText("god.authority.on");
                return;
            }
            dbCache.execute("insert into searchGroupId values (?, ?);", request.getGroupId().toString(), "fzzl");
            response.replyText("god.authority.on");
        } catch (Exception e) {
            response.replyText("error");
        }
    }

    @BotMessage(messageType = MessageType.GROUP, filter = "eq:解除方舟指令查询权限")
    public void fzGodGroupIdDelete(ApiRequest request, ApiResponse response) {
        try {
            Map<String, Object> result = dbCache.executeQueryMap("select * from searchGroupId where groupId = ? ", request.getGroupId().toString());
            if (result != null) {
                if (result.get("type").toString().equals("blhx")) {
                    response.replyText("ship.authority.on");
                    return;
                }
                dbCache.execute("delete from searchGroupId where groupId = ?", request.getGroupId().toString());
                response.replyText("god.authority.off");
            }
        } catch (Exception e) {
            response.reply(e.getMessage());
        }
    }
}
