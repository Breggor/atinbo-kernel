package com.atinbo.security.controller;

import cn.hutool.core.map.MapUtil;
import com.atinbo.common.VerifyCodes;
import com.atinbo.core.exception.BizException;
import com.atinbo.core.utils.Base64Util;
import com.atinbo.core.utils.StringUtil;
import com.atinbo.model.Outcome;
import com.atinbo.redis.RedisOpsCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 *
 * @author breggor
 */
@Api(tags = "验证码")
@RestController
@Slf4j
@RequestMapping("/captcha")
public class CaptchaController {
    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    @Autowired
    private RedisOpsCache redisOpsCache;

    /**
     * 生成验证码
     */
    @ApiOperation(value = "验证码接口", notes = "验证码接口")
    @GetMapping("/image")
    public Outcome<Map<String, Object>> getCode(String username) throws IOException {
        if (StringUtil.isBlank(username)) {
            throw new BizException("用户名不能为空，根据用户名生成验证码");
        }
        // 生成随机字串
        String verifyCode = VerifyCodes.generateVerifyCode(4);
        String verifyKey = CAPTCHA_CODE_KEY + username;
        redisOpsCache.setCacheObject(verifyKey, verifyCode, CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

        // 生成图片
        int w = 111, h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodes.outputImage(w, h, stream, verifyCode);
        try {
            log.info("username: {}, 生成验证码: {}", username, verifyCode);
            return Outcome.success(MapUtil.<String, Object>builder().put("img", Base64Util.encodeToString(stream.toByteArray())).build());
        } catch (Exception e) {
            e.printStackTrace();
            return Outcome.failure(e.getMessage());
        } finally {
            stream.close();
        }
    }
}
