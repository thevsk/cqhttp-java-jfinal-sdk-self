package cc.thevsk.services;

import cc.thevsk.interceptor.MasterInterceptor;
import top.thevsk.annotation.BotMessage;
import top.thevsk.annotation.BotService;
import top.thevsk.annotation.BotServiceAop;
import top.thevsk.api.ApiGet;
import top.thevsk.api.ApiSet;
import top.thevsk.api.ApiSystem;
import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.entity.ReturnJson;
import top.thevsk.enums.MessageType;
import top.thevsk.utils.CQUtils;
import top.thevsk.utils.MessageUtils;

import java.util.Map;

@BotService
@BotServiceAop(MasterInterceptor.class)
public class ManageService {

    @BotMessage(filter = "eq:!groupList")
    public void groupList(ApiRequest request, ApiResponse response) {
        try {
            ReturnJson returnJson = ApiGet.getGroupList();
            if (returnJson.getRetcode() == 0) {
                StringBuilder sbf = new StringBuilder();
                sbf.append("Num:");
                sbf.append(returnJson.getDataList().size());
                sbf.append("\n");
                for (int i = 0; i < returnJson.getDataList().size(); i++) {
                    if (i != 0) {
                        sbf.append("\n");
                    }
                    sbf.append(returnJson.getDataList().getJSONObject(i).get("group_id"));
                    sbf.append(":");
                    sbf.append(returnJson.getDataList().getJSONObject(i).get("group_name"));
                }
                response.reply(sbf.toString());
            } else
                response.reply("retCode:" + returnJson.getRetcode());
        } catch (Exception e) {
            response.replyAt(e.getMessage());
        }
    }

    @BotMessage(filter = "eq:!clean")
    public void clean(ApiRequest request, ApiResponse response) {
        String[] cleans = {"image", "record", "show", "bface"};
        for (String clean : cleans) {
            response.reply(ApiSystem.cleanDataDir(clean).toString());
        }
    }

    @BotMessage(filter = "startWith:!outGroup")
    public void outGroup(ApiRequest request, ApiResponse response) {
        try {
            response.reply(ApiSet.setGroupLeave(Long.valueOf(request.getMessage().trim()), false).toString());
        } catch (Exception e) {
            response.replyAt(e.getMessage());
        }
    }

    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:!rename")
    public void reGroupName(ApiRequest request, ApiResponse response) {
        try {
            Long userId;
            Map<String, String> map = MessageUtils.parseMap(request.getMessage());
            String userIdMsg = MessageUtils.getOrEx(map, "user");
            if (userIdMsg.equals("self")) {
                userId = request.getSelfId();
            } else {
                userId = CQUtils.getUserIdInCqAtMessage(userIdMsg)[0];
            }
            response.reply(response.setCard(userId, MessageUtils.getOrEx(map, "name")).toString());
        } catch (Exception e) {
            response.replyAt(e.getMessage());
        }
    }

    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:!kick")
    public void kick(ApiRequest request, ApiResponse response) {
        try {
            Long userId = CQUtils.getUserIdInCqAtMessage(request.getMessage().trim())[0];
            if (userId == null) {
                throw new Exception("no user");
            }
            response.reply(response.kick(userId).toString());
        } catch (Exception e) {
            response.replyAt(e.getMessage());
        }
    }

    /*
    @BotMessage(messageType = MessageType.GROUP, filter = "startWith:!ipGetter")
    public void ipGetter(ApiRequest request, ApiResponse response) {
        if (Constants.cacheOfIp != null) {
            response.replyAt("being used...");
            return;
        }
        int time;
        try {
            Map<String, String> map = MessageUtils.parseMap(request.getMessage());
            time = Integer.valueOf(map.get("time"));
            if (time > 60) {
                response.replyAt("too long time!");
            } else if (time <= 0) {
                response.replyAt("set time 5");
                time = 5;
            }
        } catch (Exception e) {
            response.replyAt("set time 5");
            time = 5;
        }
        Constants.cacheOfIp = new HashSet<>();
        String image = "http://thevsk.cc:7500/image/qrCode/" + System.currentTimeMillis() + "?t=qrCode.jpg";
        response.reply(CQUtils.share("https://www.baidu.com", "ipGetter", "by kd", image));
        final int finalTime = time * 1000;
        new Thread(() -> {
            try {
                Thread.sleep(finalTime);
                StringBuilder stringBuilder = new StringBuilder("ips:");
                for (String str : Constants.cacheOfIp) {
                    stringBuilder.append("\r\n");
                    stringBuilder.append(str);
                }
                response.reply(stringBuilder.toString());
                Constants.cacheOfIp.clear();
                Constants.cacheOfIp = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    */
}
