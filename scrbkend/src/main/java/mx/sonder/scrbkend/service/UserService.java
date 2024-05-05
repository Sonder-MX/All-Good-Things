package mx.sonder.scrbkend.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import mx.sonder.scrbkend.entity.Users;
import mx.sonder.scrbkend.entity.vo.PageVo;
import mx.sonder.scrbkend.entity.vo.RegOrLoginVO;
import mx.sonder.scrbkend.entity.vo.UserInfoVO;
import mx.sonder.scrbkend.mapper.UserMapper;
import mx.sonder.scrbkend.utils.PageResult;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public PageResult<UserInfoVO> pageUsers(PageVo pageVo) {
        PageHelper.startPage(pageVo.getCurrent(), pageVo.getPageSize());
        Page<Users> pgUsers = userMapper.pageQuery(pageVo);

        @SuppressWarnings("null")
        List<UserInfoVO> uvo = pgUsers.getResult().stream().map(user -> {
            UserInfoVO userInfoVO = new UserInfoVO();
            BeanUtils.copyProperties(user, userInfoVO);
            return userInfoVO;
        }).collect(Collectors.toList());

        PageResult<UserInfoVO> result = new PageResult<UserInfoVO>();
        result.setCurrent(pgUsers.getPageNum());
        result.setPageSize(pgUsers.getPageSize());
        result.setTotal(pgUsers.getTotal());
        result.setResult(uvo);
        return result;
    }

    public String registerUser(RegOrLoginVO user) {
        if (!user.isValid()) {
            return "请输入正确的邮箱和密码";
        }
        if (userMapper.countByEmail(user.getEmail()) > 0) {
            return "该邮箱已被注册，请更换邮箱";
        }
        Users newUser = Users.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(DigestUtils.sha256Hex(user.getPassword()))
                .isActive(true)
                .created(new Timestamp(System.currentTimeMillis()))
                .build();
        if (userMapper.insertUser(newUser)) {
            return null;
        } else {
            return "注册失败，请稍后再试";
        }
    }

    public Users findByEmail(String email) {
        return userMapper.findByEmail(email);
    }
}
