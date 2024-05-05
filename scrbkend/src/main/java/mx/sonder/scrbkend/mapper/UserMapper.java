package mx.sonder.scrbkend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;

import mx.sonder.scrbkend.entity.Users;
import mx.sonder.scrbkend.entity.vo.PageVo;

@Mapper
public interface UserMapper {

    Page<Users> pageQuery(PageVo pageVo);

    @Select("select count(*) from users where email = #{email}")
    public long countByEmail(String email);

    @Select("select * from users where email = #{email}")
    public Users findByEmail(String email);

    public Boolean insertUser(Users users);

}
