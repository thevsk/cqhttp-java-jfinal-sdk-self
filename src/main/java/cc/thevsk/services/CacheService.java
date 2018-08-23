package cc.thevsk.services;

import cc.thevsk.utils.SQLiteUtils;
import top.thevsk.annotation.BotMessage;
import top.thevsk.annotation.BotService;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.enums.MessageType;

import java.util.ArrayList;
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

    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:创建ROLL房间")
    public void rollRoomCreate(ApiRequest request, ApiResponse response) {
        try {
            String[] text = request.getMessage().split("\r\n");
            Object isNull = dbCache.executeQueryMap("select * from RollRoom where groupId = ? and roomName = ? ", request.getGroupId(), text[1].trim());
            if (isNull != null) {
                response.reply("此房间已存在，请尝试换个名字");
                return;
            }
            dbCache.execute("insert into RollRoom(id, groupId, roomName, createTime, masterUser, rollText) values (?, ?, ?, ?, ?, ?);", new ArrayList<Object>() {
                {
                    add(SQLiteUtils.uuid());
                    add(request.getGroupId());
                    add(text[1].trim());
                    add(System.currentTimeMillis());
                    add(request.getUserId());
                    add(text[2].trim());
                }
            }.toArray(new Object[0]));
            response.reply("ROLL房间创建成功，输入「参加ROLL房间 " + text[1] + "」来参加");
        } catch (Exception e) {
            response.reply("格式错误，正确格式：\r\n创建ROLL房间\r\n「房间名称」\r\n「ROLL内容」");
        }
    }

    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:参加ROLL房间")
    public void rollRoomAddUser(ApiRequest request, ApiResponse response) {
        try {
            Map<String, Object> room = dbCache.executeQueryMap("select * from RollRoom where groupId = ? and roomName = ? ", request.getGroupId(), request.getMessage().trim());
            if (room == null) {
                response.reply("此房间不存在");
                return;
            }
            if (request.getUserId().equals(room.get("masterUser"))) {
                response.reply("创建者不可参与");
                return;
            }
            Object isNull = dbCache.executeQueryMap("select * from RollRoomUser where roomId = ? and userId = ?", room.get("id"), request.getUserId());
            if (isNull != null) {
                response.reply("已经参加过不能重复参加");
                return;
            }
            dbCache.execute("insert into RollRoomUser(roomId, userId, createTime) values (?, ?, ?);", new ArrayList<Object>() {
                {
                    add(room.get("id"));
                    add(request.getUserId());
                    add(System.currentTimeMillis());
                }
            }.toArray(new Object[0]));
            response.reply("参与成功");
        } catch (Exception e) {
            response.reply("未知错误：" + e.getMessage());
        }
    }

    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:ROLL房间开奖")
    public void rollRoomOver(ApiRequest request, ApiResponse response) {
        try {
            Map<String, Object> room = dbCache.executeQueryMap("select * from RollRoom where groupId = ? and roomName = ? ", request.getGroupId(), request.getMessage().trim());
            if (room == null) {
                response.reply("此房间不存在");
                return;
            }
            response.reply("开奖！（还没做）");
        } catch (Exception e) {
            response.reply("未知错误：" + e.getMessage());
        }
    }
}
