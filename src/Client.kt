import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.*

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    while (true) {
        println("Enter the path of the file to send")
        val filePath = readln()
        val file = File(filePath)
        if (!file.exists()) {
            println("File doesn't exists try selecting different file...")
            continue
        }

        // connecting socket
        val socket = Socket("192.168.1.205", 9999)
        val output = PrintWriter(socket.getOutputStream(),true)
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))
        println("connected with server via socket")

        initializeSocket(output, file.length())
        println("initalized with init protocol")

        val initAck = verifyInitAck(input)
        println("verified acknowledgement")
        if (initAck == -1) {
            println("Error connecting to socket, trying to reconnect...")
            continue
        }

        sendFile(output, input, file, DataInputStream(socket.getInputStream()),DataOutputStream(socket.getOutputStream()))
        output.println("proto:endSend")
        println("Finished Sending the file ${filePath}")
    }
}

fun initializeSocket(output: PrintWriter, fileLength: Long) {
    output.write("proto:initSend;")
    output.write("fileName:sample.txt;")
    output.write("fileLength:${fileLength}")
    output.println()
}

fun verifyInitAck(input: BufferedReader): Int {
    val ack = input.readLine()
    return if (ack.startsWith("err:")) {
        -1
    } else {
        1
    }
}

fun sendFile(output: PrintWriter, input: BufferedReader, file: File, dataInputStream: DataInputStream,dataOutputStream: DataOutputStream) {
    var bytes = 0
//    val fileInputStream = FileInputStream(file)
    val scanner = Scanner(file)
//    val buffer = ByteArray(4 * 1024)
    while (scanner.hasNextLine()) {
//        bytes = fileInputStream.read(buffer)
//        if (bytes == -1) break

        output.println("proto:sendData;data:${scanner.nextLine()};fileName:sample.txt")

//        dataOutputStream.write(buffer,0,bytes)
//        dataOutputStream.flush()
//        var ack = verifySendAck(input)
//        while (ack == -1) {
//            dataOutputStream.write(buffer,0,bytes)
//            dataOutputStream.flush()
//            ack = verifySendAck(input)
//        }
    }
}

fun verifySendAck(input: BufferedReader) : Int {
//    val ack = input.readLine()
//    return if (ack.startsWith("err:")){
//        -1
//    } else {
        return 1
//    }
}