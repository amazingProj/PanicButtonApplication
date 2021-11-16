package com.example.custombutton.main.service

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {

    lateinit var mSocket: Socket
    private const val BASE_URL : String = "http://10.7.10.18:3000"

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
    fun establishConnection() {
        mSocket.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }
}