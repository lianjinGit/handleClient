package com.handle.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;

import com.google.gson.Gson;
import com.handle.client.module.LonginModule;
import com.handle.client.module.RegisterModule;

public class HandleClientApp {

    SocketChannel mChannel = null;
    boolean mConnected = false;
    private int port;
    private String addressStr;

    private LonginModule loginModule;
    private RegisterModule registerModule;

    public HandleClientApp(String address, int port) {
        super();
        this.port = port;
        this.addressStr = address;
        loginModule = new LonginModule(this);
        registerModule = new RegisterModule(this);
    }

    public void openConnection() throws java.net.UnknownHostException, java.io.IOException, SecurityException {
        SocketAddress address = new InetSocketAddress(addressStr, port);
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        if (!channel.connect(address)) {
            for (int i = 0; (!channel.finishConnect()) && i < 4; i++) {
                try {
                    Thread.currentThread().sleep(250);
                }
                catch (Exception e) {
                }
            }
        }
        if (channel.isConnected()) {
            mChannel = channel;
            mChannel.configureBlocking(true);
            mConnected = true;
        }
        else {
            throw new SocketTimeoutException("Unable to connect to server " + addressStr + " at port " + port);
        }
    }

    public void closeConnection() {
        if (mConnected || mChannel != null) {
            try {
                mChannel.close();
            }
            catch (java.io.IOException ioe) {
            }
            finally {
                mChannel = null;
                mConnected = false;
            }
        }
    }

    /**
     * Sends a message.
     */
    public void sendMessage(String pMessage) {
        BufferedWriter writer = new BufferedWriter(Channels.newWriter(mChannel, "UTF-8"));
        try {
            writer.write(pMessage);
            writer.newLine();
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a Object.
     */
    public void sendMessage(Object pMessage) {
        Gson gson = new Gson();
        sendMessage(gson.toJson(pMessage));
    }

    /**
     * Gets a response from the (already open) connection.
     */
    public String retrieveResponse() {
        BufferedReader reader = new BufferedReader(Channels.newReader(mChannel, "UTF-8"));
        String returnValue = null;
        try {
            returnValue = reader.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();

        }
        return returnValue;
    }

    /**
     * Returns if this is connected or not.
     */
    public boolean isConnected() {
        return mConnected;
    }

    public LonginModule getLoginModule() {

        return loginModule;
    }

    public void setLoginModule(LonginModule loginModule) {

        this.loginModule = loginModule;
    }

    public RegisterModule getRegisterModule() {

        return registerModule;
    }

    public void setRegisterModule(RegisterModule registerModule) {

        this.registerModule = registerModule;
    }

}
