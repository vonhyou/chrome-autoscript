import java.net.URL
import scala.util.Random

object BrowsingTime {
  def of(time: Int = 60, range: Range = 2 to 5 * 60, lambda: Double = 1.0 / 30): List[Int] =
    var timeLeftInSecond = time * 60
    var browsingTimes = List[Int]()
    while timeLeftInSecond > range.start do
      val browsingTimeInSecond = truncatedExponentialRandom(lambda, range.start, range.end)
      timeLeftInSecond -= browsingTimeInSecond
      browsingTimes = browsingTimeInSecond :: browsingTimes
    end while
    browsingTimes

  private def truncatedExponentialRandom(lambda: Double, min: Int, max: Int): Int =
    var random = exponentialRandom(lambda)
    while random < min || random > max do
      random = exponentialRandom(lambda)
    end while
    random.toInt

  private def exponentialRandom(lambda: Double): Double = -math.log(1.0 - Random.nextDouble()) / lambda

}
