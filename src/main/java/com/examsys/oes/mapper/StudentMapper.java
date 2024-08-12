package com.examsys.oes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examsys.oes.entity.Student;
import com.examsys.oes.entity.Studentwrong;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StudentMapper {

    /**
     * 分页查询所有学生
     * @param page
     * @return List<Student>
     */

    IPage<Student> findAll(Page page, @Param("name") String name, @Param("grade") String grade,
                           @Param("tel") String tel,  @Param("institute") String institute,
                           @Param("major")String major, @Param("clazz") String clazz);


    Student findById(Integer studentId);


    int deleteById(Integer studentId);

    /**
     *更新所有学生信息
     * @param student 传递一个对象
     * @return 受影响的记录条数
     */

    int update(Student student);

    /**
     *更新错题本
     * @param student 传递一个对象
     * @return 受影响的记录条数
     */

    int updatewrong(Studentwrong studentwrong);

    /**
     * 更新密码
     * @param student
     * @return 受影响的记录条数
     */

    int updatePwd(Student student);



    int add(Student student);
}
