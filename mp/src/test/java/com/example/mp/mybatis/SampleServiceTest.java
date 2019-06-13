package com.example.mp.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mp.mapper.UserMapper;
import com.example.mp.model.User;
import com.example.mp.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @Auther: mxd
 * @Date: 2019/6/12 16:33
 * @Desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testSave() {
        System.out.println(("----- save method test ------"));
        User user = new User();
        user.setName("十里河");
        user.setAge(22);
        user.setEmail("11@qq.com");
        user.setId(10L);
        boolean save = userService.save(user);
        System.out.println(save);
    }

    @Test
    public void testSaveBatch() {
        System.out.println(("----- saveBatch method test ------"));
        List<User> list =new ArrayList<User>();
        User user1 = new User();
        user1.setName("十里河");
        user1.setAge(22);
        user1.setEmail("11@qq.com");
        user1.setId(11L);
        User user2 = new User();
        user2.setName("十里河");
        user2.setAge(22);
        user2.setEmail("11@qq.com");
        user2.setId(12L);
        User user3 = new User();
        user3.setName("十里河");
        user3.setAge(22);
        user3.setEmail("11@qq.com");
        user3.setId(13L);
        User user4 = new User();
        user4.setName("十里河");
        user4.setAge(22);
        user4.setEmail("11@qq.com");
        user4.setId(14L);
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        boolean save = userService.saveBatch(list);
        System.out.println(save);
    }

    @Test
    public void testRemoveById() {
        System.out.println(("----- removeById method test ------"));
        boolean b = userService.removeById(11);
        System.out.println(b);
    }

    @Test
    public void testRemoveByMap() {
        System.out.println(("----- removeByMap method test ------"));
        Map map = new HashMap();
        map.put("id",12);
        map.put("name","十里河");
        map.put("age",22);
        boolean b = userService.removeByMap(map);
        System.out.println(b);
    }

    @Test
    public void testRemove() {
        System.out.println(("----- remove method test ------"));

        boolean b = userService.remove(new QueryWrapper<User>().eq("name","张三"));
        System.out.println(b);
    }

    @Test
    public void testRemoveByIds() {
        System.out.println(("----- removeByIds method test ------"));
        List list =new ArrayList();
        list.add(13);
        list.add(14);
        boolean b = userService.removeByIds(list);
        System.out.println(b);
    }

    @Test
    public void testUpdateById() {
        System.out.println(("----- UpdateById method test ------"));
        User user1 = new User();
        user1.setName("十里河");
        user1.setAge(22);
        user1.setEmail("11@qq.com");
        user1.setId(2l);
        boolean b = userService.updateById(user1);
        System.out.println(b);
    }

    @Test
    public void testUpdate() {
        System.out.println(("----- Update method test ------"));
        User user1 = new User();
        user1.setName("李纯罡");
        user1.setAge(13);
        user1.setEmail("11@qq.com");
        boolean b = userService.update(user1,new QueryWrapper<User>().eq("id","1"));
        System.out.println(b);
    }

    @Test
    public void testUpdateBatchById() {
        System.out.println(("----- UpdateBatchById method test ------"));
        List<User> list =new ArrayList<User>();
        User user1 = new User();
        user1.setName("李纯罡");
        user1.setAge(77);
        user1.setEmail("11@qq.com");
        user1.setId(11L);
        User user2 = new User();
        user2.setName("李逍遥");
        user2.setAge(98);
        user2.setEmail("11@qq.com");
        user2.setId(12L);
        User user3 = new User();
        user3.setName("李靖");
        user3.setAge(500);
        user3.setEmail("11@qq.com");
        user3.setId(13L);
        User user4 = new User();
        user4.setName("李自成");
        user4.setAge(33);
        user4.setEmail("11@qq.com");
        user4.setId(14L);
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        boolean b = userService.updateBatchById(list,2);
        System.out.println(b);
    }

    @Test
    public void testSaveOrUpdate() {
        System.out.println(("----- saveOrUpdate method test ------"));
        User user1 = new User();
        user1.setName("小布丁");
        user1.setAge(77);
        user1.setEmail("11@qq.com");
        user1.setId(11L);
        boolean b1 = userService.saveOrUpdate(user1);
        System.out.println(b1);

        User user2 = new User();
        user2.setName("李逍遥");
        user2.setAge(98);
        user2.setEmail("11@qq.com");
        user2.setId(16L);
        boolean b2 = userService.saveOrUpdate(user2);
        System.out.println(b2);

    }

    @Test
    public void testGetById() {
        System.out.println(("----- getById method test ------"));

        User user = userService.getById(16);
        System.out.println(user);

    }

    @Test
    public void testListByIds() {
        System.out.println(("----- listByIds method test ------"));
        List list =new ArrayList();
        list.add(13);
        list.add(14);
        Collection collection = userService.listByIds(list);
        System.out.println(collection);

    }

    @Test
    public void testListByMap() {
        System.out.println(("----- listByMap method test ------"));
        Map map = new HashMap();
        map.put("name","十里河");
        Collection collection = userService.listByMap(map);
        System.out.println(collection);

    }

    @Test
    public void testGetOne() {
        System.out.println(("----- getOne method test ------"));
        User user = userService.getOne(new QueryWrapper<User>().eq("id", "1"));
        System.out.println(user);

        User user2 = userService.getOne(new QueryWrapper<User>().eq("name", "十里河"));
        System.out.println(user2);

        User user3 = userService.getOne(new QueryWrapper<User>().eq("name", "十里河"),false);
        System.out.println(user3);
    }

    @Test
    public void testGetMap() {
        System.out.println(("----- getMap method test ------"));
        Map<String, Object> map = userService.getMap(new QueryWrapper<User>().eq("id", "1"));
        System.out.println(map);
    }


    @Test
    public void testCount() {
        System.out.println(("----- Count method test ------"));
        int count = userService.count(new QueryWrapper<User>().eq("name", "十里河"));
        System.out.println(count);
    }

    @Test
    public void testList() {
        System.out.println(("----- list method test ------"));
        List<User> list = userService.list(new QueryWrapper<User>().eq("name", "十里河"));
        System.out.println(list);
    }

    @Test
    public void testPage() {
        System.out.println(("----- page method test ------"));
        Page<User> userPage = new Page<>();
        userPage.setCurrent(2);
        userPage.setSize(1);
        IPage<User> page = userService.page(userPage, new QueryWrapper<User>().eq("name", "十里河"));
        List<User> records = page.getRecords();
        System.out.println(records);
    }

    @Test
    public void testListMaps() {
        System.out.println(("----- listMaps method test ------"));
        List<Map<String, Object>> maps = userService.listMaps(new QueryWrapper<User>().eq("name", "十里河"));
        System.out.println(maps);
    }

    @Test
    public void testListObjs() {
        System.out.println(("----- listObjs method test ------"));
        List<Object> objects = userService.listObjs(new QueryWrapper<User>().eq("name", "十里河"));
        System.out.println(objects);
    }

    @Test
    public void testPageMaps() {
        System.out.println(("----- pageMaps method test ------"));
        Page<User> userPage = new Page<>();
        userPage.setCurrent(1);
        userPage.setSize(3);
        IPage<Map<String, Object>> mapIPage = userService.pageMaps(userPage, new QueryWrapper<User>().eq("name", "十里河"));
        List<Map<String, Object>> records = mapIPage.getRecords();
        System.out.println(records);
    }

}
