package cc.thevsk.interceptor;

import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.interceptor.interfaces.BotServiceInterceptor;

/**
 * @author thevsk
 * @Title: MasterInterceptor
 * @ProjectName cqhttp-java-jfinal-sdk
 * @date 2018-08-03 16:05
 */
public class MasterInterceptor implements BotServiceInterceptor {

    @Override
    public boolean intercept(ApiRequest request, ApiResponse response) {
        return request.getUserId() == null || request.getUserId().equals(2522534416L) || request.getUserId().equals(1916079648L);
    }
}
