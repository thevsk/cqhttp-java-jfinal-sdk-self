package cc.thevsk.services;

import cc.thevsk.interceptor.TestGroupInterceptor;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.ext.kit.DateKit;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.StrKit;
import top.thevsk.annotation.BotMessage;
import top.thevsk.annotation.BotService;
import top.thevsk.annotation.BotServiceAop;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.enums.MessageType;

import java.util.Date;
import java.util.HashMap;

@BotService
@BotServiceAop(TestGroupInterceptor.class)
public class TestGroupService {


    @BotMessage(messageType = MessageType.GROUP, filter = "eq:time|userId:2522534416")
    public void azurLaneSearch(ApiRequest request, ApiResponse response) {
        System.out.println("方法收到消息时间：" + DateKit.toStr(new Date(), DateKit.timeStampPattern));
        response.replyAt("reply");
        System.out.println("消息处理完成时间：" + DateKit.toStr(new Date(), DateKit.timeStampPattern));

    }
}
