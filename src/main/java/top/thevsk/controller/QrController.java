package top.thevsk.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

/**
 * @author thevsk
 * @Title: QrController
 * @ProjectName cqhttp-java-jfinal-sdk
 * @date 2018-08-17 12:03
 */
@Clear
public class QrController extends Controller {

    public void index() {
        String msg = getPara();
        if (StrKit.isBlank(msg)) {
            renderQrCode("https://www.google.com", 260, 260);
        } else {
            renderQrCode(msg, 260, 260);
        }
    }
}
