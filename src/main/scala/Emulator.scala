import java.net.URL
import sys.process._
import scala.util.chaining._

object Emulator {
  private val BASE = "adb shell input"

  def openTab(url: URL): Unit = {
    send(230, 880).pipe(Command.apply)
    send(url.toString).pipe(Command.apply)
    send(1330, 2820).pipe(Command.apply)
  }

  def closeTab(): Unit = {
    send(1200, 185).pipe(Command.apply)
    send(623, 373).pipe(Command.apply)
    send(96, 184).pipe(Command.apply)
  }

  /**
   * Send a command to the emulator via ADB, currently supports text and tap
   * @param args the command arguments, a string or a tuple of two integers
   * @tparam T possible types are String or (Int, Int)
   * @return the command to be executed
   */
  private def send[T](args: T) = args match
    case args: String => s"$BASE text \"$args\""
    case (x: Int, y: Int) => s"$BASE tap $x $y"
}

object Command {
  private val DELAY_MS: Int = 500

  def apply(command: String): Unit =
    command.!
    Thread.sleep(DELAY_MS)
}