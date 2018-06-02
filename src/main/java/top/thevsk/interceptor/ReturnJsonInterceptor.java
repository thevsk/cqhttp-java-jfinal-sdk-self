package top.thevsk.interceptor;

import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.LogKit;
import top.thevsk.entity.ReturnJson;

public class ReturnJsonInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation invocation) {
        try {
            LogKit.info("[调用API] url:" + invocation.getArg(0));
            if (invocation.getArg(1) != null) {
                LogKit.info("[调用API] 参数:" + JSON.toJSONString(invocation.getArg(1)));
            }
            invocation.invoke();
            ReturnJson returnObject = invocation.getReturnValue();
            if (returnObject.getRetcode() != 0 && returnObject.getRetcode() != 1) {
                LogKit.error("[拦截器] ReturnJson 插件返回错误, code:" + returnObject.getRetcode());
                onError(invocation);
            }
        } catch (Exception e) {
            LogKit.error("[拦截器] ReturnJson 插件错误", e);
            onError(invocation);
        }
    }

    private void onError(Invocation invocation) {
        LogKit.error("[拦截器] ReturnJson 发生错误的url:" + invocation.getArg(0));
        if (invocation.getArg(1) != null) {
            LogKit.error("[拦截器] ReturnJson 发送错误的参数:" + JSON.toJSONString(invocation.getArg(1)));
        }
    }
}