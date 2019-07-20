package cc.thevsk.interceptor;

import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.interceptor.interfaces.BotServiceInterceptor;


public class TestGroupInterceptor implements BotServiceInterceptor {

    @Override
    public boolean intercept(ApiRequest request, ApiResponse response) {
        return request.getGroupId() != null && request.getGroupId().equals(648866427L);
    }
}
