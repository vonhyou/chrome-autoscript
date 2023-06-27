import java.net.URL
import java.nio.file.Path
import scala.io.Source
import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try, Using}

@main def main(): Unit =
  print("Enter the path to the file containing the URLs: ")
  val urls: List[URL] = getURLs(Path.of(readLine()))
  val intervals: List[Int] = BrowsingTime.of()
  val totalTime: Int = intervals.sum

  confirmation(urls.length, totalTime, intervals.length + 1)

  readLine().trim().toLowerCase() match
    case "y" =>
      intervals.indices.foreach { i =>
        printLog(i, urls.length, intervals(i), urls(i))
        runTestOn(urls(i), intervals(i))
      }
    case _ => println("Aborted")

def getURLs(path: Path): List[URL] =
  var urls = List[URL]()
  Using.resource(Source.fromFile(path.toString)) { source =>
    source.getLines().foreach { line =>
      Try(new URL(line)) match
        case Success(value) => urls = urls :+ value // or urls ::: value :: Nil
        case Failure(_) => println(s"[Warning]: `$line` is not a valid URL")
    }
  }
  urls

def runTestOn(url: URL, interval: Int): Unit =
  Emulator.openTab(url)
  Thread.sleep(interval * 1000)
  Emulator.closeTab()

def printLog(i: Int, length: Int, interval: Int, url: URL): Unit =
  println(s"[${i + 1}/$length]: Visiting ${
    if url.toString.length > 80 then url.toString.take(80) + "..."
    else url
  } for $interval seconds")

def confirmation(urlNumber: Int, totalTime: Int, totalVisiting: Int): Unit =
  print(
    s"""Found $urlNumber URLs
       |Expected to run $totalTime seconds
       |For visiting $totalVisiting sites
       |proceed? [y/N]""".stripMargin)