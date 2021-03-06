import java.net.Socket
import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.InetSocketAddress
import java.net.ServerSocket

object Main extends App {
  val arguments = args.toList
  val sourceIp :: sourcePortStr :: destIp :: destPortStr :: Nil = arguments
  val sourcePort = Integer.parseInt(sourcePortStr)
  val destPort = Integer.parseInt(destPortStr)

  val sourceAddress = new InetSocketAddress(sourceIp, sourcePort)
  val destAddress = new InetSocketAddress(destIp, destPort)

  val serverSocket = new ServerSocket
  serverSocket.bind(sourceAddress)
  val readSock = serverSocket.accept

  val writeSock = new Socket
  val timeoutMillis = 10000
  writeSock.connect(destAddress, timeoutMillis)

  val reader = new BufferedReader(new InputStreamReader(readSock.getInputStream))
  val writer = new OutputStreamWriter(writeSock.getOutputStream)

  def nextInt: Int = {
    val line = reader.readLine
    val parsedInt = line.toInt
    parsedInt
  }

  def sendIncremented(incomingInt: Int): Unit = {
    val intToSend = incomingInt + 1
    val serializedInt = intToSend.toString()
    println(s"Got $incomingInt, sending $intToSend")
    val length = serializedInt.length
    writer.write(serializedInt, 0, length)
    writer.write('\n')
    writer.flush()
  }

  val incomingInts = Stream.continually(nextInt)

  incomingInts foreach sendIncremented
}
