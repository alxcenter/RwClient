package io.bot.controllers;

import io.bot.uz.RequestNtw;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class CaptchaController {

    @Autowired
    RequestNtw requestNtw;

    @GetMapping(
            value = "/captcha",
            produces = MediaType.IMAGE_GIF_VALUE
    )
    public @ResponseBody
    byte[] getImageWithMediaType() throws IOException {
        InputStream captcha = requestNtw.getCaptcha("captcha/", requestNtw.getSessionCookies());
        return IOUtils.toByteArray(captcha);
    }

}
