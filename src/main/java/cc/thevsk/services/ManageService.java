package cc.thevsk.services;

import cc.thevsk.interceptor.MasterInterceptor;
import top.thevsk.annotation.BotService;
import top.thevsk.annotation.BotServiceAop;

@BotService
@BotServiceAop(MasterInterceptor.class)
public class ManageService {
}
