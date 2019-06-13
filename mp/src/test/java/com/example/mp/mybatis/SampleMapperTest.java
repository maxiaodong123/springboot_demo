package com.example.mp.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mp.mapper.UserMapper;
import com.example.mp.model.User;
import org.apache.ibatis.session.RowBounds;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: mxd
 * @Date: 2019/6/12 11:00
 * @Desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        System.out.println(("----- insert method test ------"));
        User user =new User();
        user.setName("李四");
        user.setAge(22);
        user.setEmail("11@qq.com");
        user.setId(9L);
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }

    @Test
    public void testDeleteById() {
        System.out.println(("----- DeleteById method test ------"));

        int delete = userMapper.deleteById(6);
        System.out.println(delete);
    }

    @Test
    public void testDeleteByMap(){
        System.out.println(("----- deleteByMap method test ------"));

        Map map = new HashMap();
        map.put("name","李四");
//        map.put("sum",12); //com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column 'sum' in 'where clause'
        int id = userMapper.deleteByMap(map);
        System.out.println(id);
    }

    @Test
    public void testDelete(){
        System.out.println(("----- delete method test ------"));
        int id = userMapper.delete(new QueryWrapper<User>().eq("id", 2));
        System.out.println(id);
    }

    @Test
    public void testDeleteBatchIds(){
        System.out.println(("----- deleteBatchIds method test ------"));

        List list =new ArrayList();
        list.add(3);
        list.add(4);
        int id = userMapper.deleteBatchIds(list);
        System.out.println(id);
    }

    @Test
    public void testUpdateById(){
        System.out.println(("----- updateById method test ------"));

        User user =new User();
        user.setName("李四123");
        user.setAge(29);
        user.setEmail("1122312312@qq.com");
        user.setId(1L);
        int update = userMapper.updateById(user);
        System.out.println(update);
    }

    @Test
    public void testUpdate(){
        System.out.println(("----- update method test ------"));

        User user =new User();
        user.setName("李四");
        user.setAge(22);
        user.setEmail("11@qq.com");
        user.setId(1L);
        int update = userMapper.update(user, new UpdateWrapper<User>().eq("name", "Jone"));
        System.out.println(update);
    }

    @Test
    public void testSelectById() {
        System.out.println(("----- selectBy method test ------"));
        User user = userMapper.selectById("1");
        System.out.println(user.getName());
    }

    @Test
    public void testSelectBatchIds() {
        System.out.println(("----- selectBatchIds method test ------"));
        List list =new ArrayList();
        list.add(3);
        list.add(4);
        List list2 = userMapper.selectBatchIds(list);
        System.out.println(list2);
    }

    @Test
    public void testSelectByMap() {
        System.out.println(("----- selectByMap method test ------"));
        Map map = new HashMap();
        map.put("name","张三");
        List list2 = userMapper.selectByMap(map);
        System.out.println(list2);
    }

    @Test
    public void testSelectOne() {
        System.out.println(("----- selectOne method test ------"));

        User user = userMapper.selectOne(new QueryWrapper<User>().eq("name", "Jone"));
        System.out.println(user.getAge() );
    }

    @Test
    public void testSelectCount() {
        System.out.println(("----- selectCount method test ------"));

        Integer integer = userMapper.selectCount(new QueryWrapper<User>(null));
        System.out.println(integer);

        Integer integer2 = userMapper.selectCount(new QueryWrapper<User>());
        System.out.println(integer2);


        Integer integer3 = userMapper.selectCount(new QueryWrapper<User>().eq("name","张三"));
        System.out.println(integer3);

        User user =new User();
        user.setName("张三");
        Integer integer4 = userMapper.selectCount(new QueryWrapper<User>(user));
        System.out.println(integer4);
    }

    @Test
    public void testSelectAllList() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }


    @Test
    public void testSelectList() {
        System.out.println(("----- selectList method test ------"));

//        User user = userMapper.selectOne(new QueryWrapper<User>().eq("name", "Jone"));
        // id > 1的User集合
        List<User> userList = userMapper.selectList(new QueryWrapper<User>().gt("id", 1));
        System.out.println(userList.size() );

        Map map = new HashMap();
        map.put("id",1);
        map.put("name","Jone");
        map.put("age",18);
        List<User> list = userMapper.selectList(new QueryWrapper<User>().allEq(map));
        System.out.println(list.size() );

    }

    @Test
    public void testSelectMaps() {
        System.out.println(("----- selectMaps method test ------"));
        List<Map<String, Object>> lists = userMapper.selectMaps(null);
        System.out.println(lists);

        List<Map<String, Object>> maps = userMapper.selectMaps(new QueryWrapper<User>().gt("id", 1));
        System.out.println(maps);
    }

    /**
     * @auther: mxd
     * @Param: []
     * @return: void
     * @date: 2019/6/12 16:44
     * @Description: 根据 Wrapper 条件，查询全部记录
     *  * 注意： 只返回第一个字段的值
     */
    @Test
    public void testSelectObjs() {
        System.out.println(("----- selectMaps method test ------"));
        List<Object> objects = userMapper.selectObjs(null);
        System.out.println(objects);

        List<Object> list = userMapper.selectObjs(new QueryWrapper<User>().gt("id", 1));
        System.out.println(list);
    }

    @Test
    public void testSelectPage() {
        System.out.println(("----- selectPage method test ------"));
        Page<User> userPage = new Page<>();
        userPage.setCurrent(2);
        userPage.setSize(1);
        userMapper.selectPage(userPage, new QueryWrapper<User>().gt("id", 1));
        List<User> userList = userPage.getRecords();
        System.out.println(userList);

    }

    @Test
    public void testSelectMapsPage() {
        System.out.println(("----- selectMapsPage method test ------"));

        Page<User> userPage = new Page<>();
        userPage.setCurrent(2);
        userPage.setSize(1);
        userMapper.selectMapsPage(userPage, new QueryWrapper<User>().gt("id", 1));
        System.out.println(userPage.getTotal());
        List<User> userList = userPage.getRecords();
        System.out.println(userList);

    }
}