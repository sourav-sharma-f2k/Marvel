package com.sourav1.marvel.Util

import java.math.BigInteger
import java.security.MessageDigest

class Constants {
    companion object {
        const val BASE_URL = "https://gateway.marvel.com"
        const val API_KEY = "f461e195aea359574315b346d68d2d77"
        private const val PRIVATE_KEY = "3d35f3030a4a95513d5622a2b81fac5ec9a7c734"

        val timeStamp: String = System.currentTimeMillis().toString()
        private val input = "$timeStamp$PRIVATE_KEY$API_KEY"
        private val md = MessageDigest.getInstance("MD5")

        val HASH = BigInteger(
            1, md.digest(input.toByteArray())
        ).toString(16).padStart(32, '0')

        fun convertHttpToHttps(url: String): String {
            var temp: String = ""
            val size = url.length
            var idx = 0
            while (idx <= 3) {
                temp += url[idx]
                idx++
            }

            temp += 's'

            while (idx < size) {
                temp += url[idx]
                idx++
            }

            return temp
        }
    }
}