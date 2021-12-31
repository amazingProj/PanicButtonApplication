package com.example.custombutton.main.service

import io.socket.client.Socket
import org.json.JSONObject

/**
 * class represents a socket io sender of information
 */
object SocketSender {

    @Synchronized
    fun createConnection(){
        // makes a connection with certain server with socket.io
        SocketHandler.setSocket()
        SocketHandler.establishConnection()
    }

    @Synchronized
    fun sendDataToServer(event : String, argument : String) : Boolean{
        val mSocket = SocketHandler.getSocket()
        mSocket.emit(event, argument)
        return true
    }

    @Synchronized
    fun newServerConnection(){
        // close the former connection
        SocketHandler.closeConnection()
        // makes a connection with certain server with socket.io
        SocketHandler.setSocket()
        SocketHandler.establishConnection()
    }
}