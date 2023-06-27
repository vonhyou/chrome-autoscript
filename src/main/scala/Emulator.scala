import java.net.URL
import sys.process._
import scala.util.chaining._

object Emulator {
  def openTab(url: URL): Unit =
    Command.apply(230, 880)
    Command.apply(url.toString)
    Command.apply(1330, 2820)

  def closeTab(): Unit =
    Command.apply(1200, 185)
    Command.apply(623, 373)
    Command.apply(96, 184)
}

object Command {
  private val BASE = "adb shell \"input"
  private val DELAY_MS: Int = 500

  def apply[T](args: T): Unit =
    val command = args match
      case args: String => s"$BASE text \'$args\'\""
      case (x: Int, y: Int) => s"$BASE tap $x $y\""

    command.!
    Thread.sleep(DELAY_MS)
}