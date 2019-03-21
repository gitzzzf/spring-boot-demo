package com.example.demo.controller;

import com.example.demo.bean.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-15 下午2:26
 **/
@RestController
@RequestMapping(value = "users")
public class UserController {
    public Map<Long, User> users = new ConcurrentHashMap<>();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Object getUserList(){
        List<User> res = new ArrayList<>(users.values());
        return res;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Object postUser(@ModelAttribute User user){
        if (user != null){
            users.put(user.getId(), user);
        }
        return "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object getUser(@PathVariable Long id){
        return users.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute User user){
        if (user != null){
            User u = users.get(id);
            u.setName(user.getName());
            u.setAge(user.getAge());
            users.put(id, u);
        }
        return "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Object deleteUser(@PathVariable Long id){
        users.remove(id);
        return "success";
    }
}
