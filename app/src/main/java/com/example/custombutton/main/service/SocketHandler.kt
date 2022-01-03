package com.example.custombutton.main.service

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

/**
 * object represents a connector above socket.io including necessary methods for
 * socket.io connection
 */
object SocketHandler {

    lateinit var mSocket: Socket
    private var PORT : String = "3000"
    private var IP_ADDRESS = "10.7.10.59"
    const val httpPreString = "http://"
    const val twoDotsToken = ":"
    private var BASE_URL : String = "http://10.10.2.2:3000"

    /************************* Setters and Getters ******************/

    @Synchronized
    fun setSocket() {
        try {
            mSocket = IO.socket(BASE_URL)
        } catch (e: URISyntaxException) {

        }
    }

    @Synchronized
    fun getSocket(): Socket {
        return mSocket
    }

    @Synchronized
    fun setPort(_PORT : String){
        PORT = _PORT
        updateBaseUrl()
    }

    @Synchronized
    fun setIpAddress(ipAddress : String){
        IP_ADDRESS = ipAddress
        updateBaseUrl()
    }

    @Synchronized
    fun getIpAddress() : String{
        return IP_ADDRESS
    }

    @Synchronized
    fun setBaseUrl(baseUrl : String){
        // TODO: check here if valid url
        BASE_URL = baseUrl
    }

    @Synchronized
    fun getBaseUrl() : String{
        return BASE_URL
    }

    @Synchronized
    fun updateBaseUrl(){
        BASE_URL = httpPreString + IP_ADDRESS + twoDotsToken + PORT
    }

    /****************************** Functions *************************/

    @Synchronized
    fun establishConnection() {
        mSocket.connect()
    }

    @Synchronized
    fun closeConnection() {
        if (mSocket.isActive){
            mSocket.disconnect()
        }
    }
}