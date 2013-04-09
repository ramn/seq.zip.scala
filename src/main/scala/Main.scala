import java.net.Socket
import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.OutputStreamWriter

object Main extends App {
  val readSock = new Socket("localhost", 8000)
  val writeSock = new Socket("localhost", 8001)

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
