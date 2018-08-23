package cc.thevsk.interceptor;

import top.thevsk.entity.ApiRequest;
import top.thevsk.entity.ApiResponse;
import top.thevsk.interceptor.interfaces.BotServiceInterceptor;

/**
 * @author thevsk
 * @Title: NoCqInterceptor
 * @ProjectName cqhttp-java-jfinal-sdk
 * @date 2018-08-17 16:32
 */
public class NoSudoInterceptor implements BotServiceInterceptor {

    @Override
    public boolean intercept(ApiRequest request, ApiResponse response) {
        if (request.getMessage().startsWith("!")) {
            return false;
        }
        if (request.getMessage().startsWith("-")) {
            return false;
        }
        if (request.getMessage().startsWith("—")) {
            return false;
        }
        if (request.getMessage().startsWith("―")) {
            return false;
        }
        if (request.getMessage().startsWith("￣")) {
            return false;
        }
        if (request.getMessage().startsWith("＿")) {
            return false;
        }
        return true;
    }
}
