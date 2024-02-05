import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

fun main() {
    val server = ServerSocket(8080)
    val client = server.accept()
    println("client is connected")
    val output = PrintWriter(client.getOutputStream(),true)
    val input = BufferedReader(InputStreamReader(client.getInputStream()))
    while (client.isConnected()) {
        output.println("hello")
        println(input.readLine())
    }
}