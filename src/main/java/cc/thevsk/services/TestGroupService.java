package cc.thevsk.services;

import cc.thevsk.interceptor.TestGroupInterceptor;
import com.jfinal.ext.kit.DateKit;
import top.thevsk.annotation.BotMessage;
import top.thevsk.annotation.BotService;
import top.thevsk.annotation.BotServiceAop;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.enums.MessageType;

import java.util.Date;

@BotService
@BotServiceAop(TestGroupInterceptor.class)
public class TestGroupService {

    @BotMessage(messageType = MessageType.GROUP, filter = "eq:test|userId:2522534416")
    public void reply(ApiRequest request, ApiResponse response) {
        response.replyAt("reply,at:" + DateKit.toStr(new Date(), DateKit.timeStampPattern));
    }
}
