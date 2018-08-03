package top.thevsk.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import org.lionsoul.ip2region.DataBlock;
import top.thevsk.entity.Constants;

import java.io.IOException;

@Clear
public class ImageController extends Controller {

    public void index() {
        renderNull();
    }

    public void qrCode() {
        String ip = getIP();
        String msg = "IP::ip\r\n地区::address";
        msg = msg.replace(":ip", ip);
        try {
            DataBlock dataBlock = Constants.ip2regionSearcher.btreeSearch(ip);
            msg = msg.replace(":address", dataBlock.getRegion());
        } catch (IOException e) {
            msg = msg.replace(":address", "unKnow");
        }
        if (Constants.cacheOfIp != null) {
            Constants.cacheOfIp.add(msg);
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.println(msg);
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        renderQrCode("别扫了，里面没东西", 120, 120);
    }

    private String getIP() {
        if (getHeader("x-forwarded-for") == null) {
            return getRequest().getRemoteAddr();
        }
        return getHeader("x-forwarded-for");
    }
}