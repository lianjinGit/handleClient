package com.handle.test;

import java.util.Date;

import com.handle.client.HandleClientApp;
import com.handle.util.domin.User;
import com.handle.util.response.LonginResponse;
import com.handle.util.response.RegisterResponse;

public class Test {

    public static void main(String[] args) throws Exception {

        String address = "localhost";
        int port = 7777;
        HandleClientApp app = new HandleClientApp(address, port);
        app.openConnection();
        User user = new User();
        user.setUserId("admin2");
        user.setUserName("admin");
        user.setUserTypeId(2);
        user.setSexId(1);
        user.setPassWord("123456");
        user.setRigisterDate(new Date());
        RegisterResponse registerResponse = app.getRegisterModule().register(user);
//        LonginResponse response = app.getLoginModule().login("admin", "admin");
        System.out.println(registerResponse.getReturCode());

        Thread.sleep(5000000);

    }

}
