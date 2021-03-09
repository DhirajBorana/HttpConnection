package com.example.httpconn

import com.example.httpconn.RequestMethod.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

interface IHttpHelper {
    suspend fun send(requestMethod: RequestMethod, url: String, response: String = ""): Response
}

object HttpHelper : IHttpHelper {
    override suspend fun send(requestMethod: RequestMethod, url: String, response: String): Response {
        return withContext(Dispatchers.IO) {
            val outputResponse = StringBuilder()
            var responseCode = 0
            val connection = URL(url).openConnection() as HttpsURLConnection
            try {
                connection.requestMethod = requestMethod.value
                if (requestMethod == GET) {
                    val inputStream = BufferedReader(InputStreamReader(connection.inputStream))
                    var line: String?
                    while (inputStream.readLine().also { line = it } != null) { outputResponse.append(line) }
                    inputStream.close()
                } else {
                    connection.apply {
                        doOutput = true
                        setRequestProperty("Content-Type", "application/json")
                        setRequestProperty("Accept", "application/json")
                        DataOutputStream(outputStream).apply {
                            writeBytes(response)
                            flush()
                            close()
                        }
                    }
                }
                responseCode = connection.responseCode
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection.disconnect()
            }
            Response(outputResponse.toString(), responseCode)
        }
    }
}

enum class RequestMethod(val value: String) {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE")
}