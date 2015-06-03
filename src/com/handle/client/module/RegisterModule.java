package com.handle.client.module;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.handle.client.HandleClientApp;
import com.handle.util.MsgBase;
import com.handle.util.RequestResponseCodeMapping;
import com.handle.util.domin.User;
import com.handle.util.request.RegisterRequest;
import com.handle.util.response.RegisterResponse;


/**
* @ClassName: RegisterModule
* @Description: ×¢²áÄ£¿é
*
*/

public class RegisterModule {
    /**
    * Logger for this class
    */
    private static final Logger logger = Logger.getLogger(RegisterModule.class);

    private HandleClientApp write;

    public RegisterModule(HandleClientApp write) {
        super();
        this.write = write;
    }

    public RegisterResponse register(User user) {
        RegisterRequest request = new RegisterRequest();
        request.setUser(user);
        write.sendMessage(request);
        String returnMsg = write.retrieveResponse();
        Gson gson = new Gson();
        MsgBase msg = gson.fromJson(returnMsg, MsgBase.class);
        if (msg.getType() == RequestResponseCodeMapping.REGISTER_RESPONSECODE) {
            return gson.fromJson(returnMsg, RegisterResponse.class);
        }
        return null;
    }
}
