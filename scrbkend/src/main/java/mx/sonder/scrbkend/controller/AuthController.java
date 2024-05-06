package mx.sonder.scrbkend.controller;

import java.io.IOException;
import java.util.Base64;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import mx.sonder.scrbkend.annotation.ApiLog;
import mx.sonder.scrbkend.entity.Users;
import mx.sonder.scrbkend.entity.vo.RegOrLoginVO;
import mx.sonder.scrbkend.entity.vo.TokenVO;
import mx.sonder.scrbkend.service.UserService;
import mx.sonder.scrbkend.utils.JwtUtils;
import mx.sonder.scrbkend.utils.Result;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/captcha")
    public Result<String> getCaptcha(HttpSession session) throws IOException {
        LineCaptcha lineCp = CaptchaUtil.createLineCaptcha(90, 38, 4, 10);
        String code = lineCp.getCode();
        session.setAttribute("code", code);
        log.info("Captcha Code: " + code);
        String base64Img = Base64.getEncoder().encodeToString(lineCp.getImageBytes());
        base64Img = "data:image/png;base64," + base64Img;
        return Result.ok(base64Img);
    }

    @ApiLog
    @PostMapping("/login")
    public Result<TokenVO> login(HttpSession session, @RequestBody RegOrLoginVO user) {
        String code = (String) session.getAttribute("code");
        if (code == null || !code.equalsIgnoreCase(user.getCode())) {
            return Result.error(401, "验证码错误");
        }
        Users u = userService.findByEmail(user.getEmail());
        String hashPwd = DigestUtils.sha256Hex(user.getPassword());
        if (u == null || !u.getPassword().equals(hashPwd)) {
            return Result.error(401, "用户名或密码错误");
        }
        TokenVO token = jwtUtils.createJwt(u);
        return Result.ok(token);
    }

    @ApiLog
    @PostMapping("/register")
    public Result<Void> register(@RequestBody RegOrLoginVO user) {
        Boolean isSignUp = jwtUtils.getIsSignUp();
        if (!isSignUp) {
            return Result.error(403, "注册接口已关闭");
        }

        String msg = userService.registerUser(user);
        if (msg != null) {
            return Result.error(msg);
        }
        return Result.ok();
    }

}
