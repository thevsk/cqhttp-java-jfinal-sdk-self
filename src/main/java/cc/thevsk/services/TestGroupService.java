package cc.thevsk.services;

import cc.thevsk.interceptor.TestGroupInterceptor;
import top.thevsk.annotation.BotService;
import top.thevsk.annotation.BotServiceAop;

@BotService
@BotServiceAop(TestGroupInterceptor.class)
public class TestGroupService {


}
