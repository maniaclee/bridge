package com.lvbby.bridge.http.tool;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.lvbby.bridge.annotation.BridgeMethod;
import com.lvbby.bridge.http.util.RandomGenerator;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by lipeng on 16/10/30.
 */
public class HttpKaptchaService {

    private Producer captchaProducer = kaptcha();

    @BridgeMethod("code")
    public void code(String key, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession session = httpServletRequest.getSession();
        if (session != null) {
            generate(httpServletRequest, httpServletResponse, key, genCode());
        }
    }


    protected String genCode() {
        return RandomGenerator.instance.gen();
    }

    private Producer kaptcha() {
        DefaultKaptcha re = new DefaultKaptcha();
        Properties p = new Properties();
        p.setProperty("kaptcha.border", "no");
        p.setProperty("kaptcha.border.color", "105,179,90");
        p.setProperty("kaptcha.textproducer.font.color", "red");
        p.setProperty("kaptcha.textproducer.font.size", "90");
        p.setProperty("kaptcha.image.width", "300");
        p.setProperty("kaptcha.image.height", "100");
        p.setProperty("kaptcha.session.key", "code");
        p.setProperty("kaptcha.textproducer.char.string", "0123456789");
        p.setProperty("kaptcha.textproducer.char.length", "4");
        re.setConfig(new Config(p));
        return re;
    }

    private void generate(HttpServletRequest request, HttpServletResponse response, String sessionKey, String code) {
        request.getSession().setAttribute(sessionKey, code);
        // Set to expire far in the past.
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        // return a jpeg
        response.setContentType("image/jpeg");

        // create the text for the image
        String capText = captchaProducer.createText();

        // store the text in the session
        request.getSession().setAttribute(sessionKey, capText);

        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);

        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            // write the data out
            ImageIO.write(bi, "jpg", out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
