package com.handle.client.module;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.handle.client.HandleClientApp;
import com.handle.util.MsgBase;
import com.handle.util.RequestResponseCodeMapping;
import com.handle.util.domin.User;
import com.handle.util.request.LoginRequest;
import com.handle.util.response.LonginResponse;


/**
* @ClassName: LonginModule
* @Description: 登录模块，用于登录操作
*
*/

public class LonginModule {
    /**
    * Logger for this class
    */
    private static final Logger logger = Logger.getLogger(LonginModule.class);

    private HandleClientApp write;

    public LonginModule(HandleClientApp write) {
        super();
        this.write = write;
    }

    public LonginResponse login(String userId, String password) {
        if (logger.isInfoEnabled()) {
            logger.info("login(String, String) - userId=" + userId + ", password=" + password);
        }
        LoginRequest request = new LoginRequest();
        User user = new User();
        user.setUserId(userId);
        user.setPassWord(password);
        request.setUser(user);
        write.sendMessage(request);
        String returnMsg = write.retrieveResponse();
        Gson gson = new Gson();
        MsgBase msg = gson.fromJson(returnMsg, MsgBase.class);
        if (msg.getType() == RequestResponseCodeMapping.LOGIN_RESPONSECODE) {
            return gson.fromJson(returnMsg, LonginResponse.class);
        }
        return null;
    }

}
