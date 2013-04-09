import java.net.Socket
import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.OutputStreamWriter

object Main extends App {
  val arguments = args.toList
  val sourceIp :: sourcePort :: destIp :: destPort :: Nil = arguments

  val readSock = new Socket("localhost", Integer.parseInt(sourcePort))
  val writeSock = new Socket("localhost", Integer.parseInt(destPort))

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
