import java.net.Socket
import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.InetSocketAddress

object Main extends App {
  val arguments = args.toList
  val sourceIp :: sourcePortStr :: destIp :: destPortStr :: Nil = arguments
  val sourcePort = Integer.parseInt(sourcePortStr)
  val destPort = Integer.parseInt(destPortStr)

  val sourceAddress = new InetSocketAddress(sourceIp, sourcePort)
  val destAddress = new InetSocketAddress(destIp, destPort)

  val readSock = new Socket
  readSock.bind(sourceAddress)

  val writeSock = new Socket
  writeSock.connect(destAddress, 1000)

  val reader = new BufferedReader(new InputStreamReader(readSock.getInputStream))
  val writer = new OutputStreamWriter(writeSock.getOutputStream)

  def nextInt: Int = {
    val line = reader.readLine
    val parsedInt = Integer.parseInt(line)
    parsedInt
  }

  def sendIncremented(incomingInt: Int) {
    val intToSend = incomingInt + 1
    val serializedInt = intToSend.toString()
    val length = serializedInt.length
    writer.write(serializedInt, 0, length)
    writer.write('\n')
    writer.flush()
  }

  val incomingInts = Stream.continually(nextInt)

  incomingInts foreach sendIncremented
}
