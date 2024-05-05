package mx.sonder.scrbkend.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;

import mx.sonder.scrbkend.annotation.ApiLog;
import mx.sonder.scrbkend.entity.Users;
import mx.sonder.scrbkend.entity.vo.PageVo;
import mx.sonder.scrbkend.entity.vo.UserInfoVO;
import mx.sonder.scrbkend.service.UserService;
import mx.sonder.scrbkend.utils.JwtUtils;
import mx.sonder.scrbkend.utils.PageResult;
import mx.sonder.scrbkend.utils.Result;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @ApiLog
    @GetMapping("/info")
    public Result<UserInfoVO> info(@RequestHeader("Authorization") String token) {
        DecodedJWT verifyJwt = jwtUtils.verifyJwt(token);
        if (verifyJwt == null) {
            return Result.error(401, "Token无效，请重新登录");
        }
        String email = verifyJwt.getClaim("email").asString();
        Users user = userService.findByEmail(email);
        if (user == null) {
            return Result.error(404, "用户不存在");
        }
        UserInfoVO userVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userVO);
        return Result.ok(userVO);
    }

    @ApiLog
    @PostMapping("/list")
    public Result<PageResult<UserInfoVO>> getUserList(@RequestBody PageVo pageVo) {
        PageResult<UserInfoVO> data = userService.pageUsers(pageVo);
        return Result.ok(data);
    }

}
