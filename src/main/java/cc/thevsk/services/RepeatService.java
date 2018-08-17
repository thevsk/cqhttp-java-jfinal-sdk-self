package cc.thevsk.services;

import cc.thevsk.interceptor.NoCqInterceptor;
import top.thevsk.annotation.BotMessage;
import top.thevsk.annotation.BotService;
import top.thevsk.annotation.BotServiceAop;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.enums.MessageType;

import java.util.Random;

import static cc.thevsk.entity.Constants.repeat2Last;

/**
 * @author thevsk
 * @Title: RepeatService
 * @ProjectName cqhttp-java-jfinal-sdk
 * @date 2018-08-17 16:29
 */
@BotService
public class RepeatService {

    @BotServiceAop(NoCqInterceptor.class)
    @BotMessage(messageType = MessageType.GROUP)
    public void repeat1(ApiRequest request, ApiResponse response) {
        if (new Random().nextInt(100) == 0) {
            response.reply(request.getMessage());
        }
    }

    @BotMessage(messageType = MessageType.GROUP)
    public void repeat2(ApiRequest request, ApiResponse response) {
        if (repeat2Last.get(request.getGroupId()) == null) {
            repeat2Last.put(request.getGroupId(), request.getMessage());
        } else {
            if (request.getMessage().equals(repeat2Last.get(request.getGroupId()))) {
                response.reply(request.getMessage());
                repeat2Last.remove(request.getGroupId());
            } else {
                repeat2Last.put(request.getGroupId(), request.getMessage());
            }
        }
    }
}
