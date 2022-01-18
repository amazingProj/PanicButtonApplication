package com.example.custombutton.main.model

import java.lang.Exception
import java.net.NetworkInterface
import java.util.*
import kotlin.experimental.and

object DeviceInfo {
    private var macAddressDevice : String ?= null

    fun getMacAddress() : String {
        return findMacAddress()
    }

    private fun findMacAddress(): String {
        try {
            val networkInterfaces: List<NetworkInterface> =
                NetworkInterface.getNetworkInterfaces().toList()
            var macAddress: String = ""
            var macAddressByte: String = String()

            for (networkInterface: NetworkInterface in networkInterfaces) {
                if (networkInterface.name.equals("wlan0", ignoreCase = true)) {
                    for (byte in networkInterface.hardwareAddress) {
                        macAddressByte = Integer.toHexString((byte and 0xFF.toByte()).toInt())
                        if (macAddressByte.length == 1) {
                            macAddressByte = "0$macAddressByte"
                        }
                        macAddress = macAddress + macAddressByte.uppercase(Locale.getDefault()) + ":"
                    }
                }
            }
            macAddressDevice = macAddress
            return macAddress
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "0"
    }
}