import java.net.URL
import java.nio.file.Path
import scala.io.Source
import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try, Using}

@main def main(): Unit =
  print("Enter the path to the file containing the URLs: ")
  val urls: List[URL] = getURLs(Path.of(readLine()))
  print(s"Found ${urls.length} URLs, proceed? [y/N]")

  readLine().trim().toLowerCase() match
    case "y" =>
      urls.indices.foreach { i =>
        runTestOn(urls(i))
        printLog(i, urls.length, urls(i))
      }
    case _ => println("Aborted")



def getURLs(path: Path): List[URL] =
  var urls = List[URL]()
  Using.resource(Source.fromFile(path.toString)) { source =>
    source.getLines().foreach { line =>
      Try(new URL(line)) match
        case Success(value) => urls = urls :+ value
        case Failure(_) => println(s"`$line` is not a valid URL")
    }
  }
  urls

def runTestOn(url: URL): Unit =
  Emulator.openTab(url)
  Thread.sleep(2000)
  Emulator.closeTab()

def printLog(i: Int, length: Int, url: URL): Unit =
  println(s"[${i + 1}/$length]: ${
    if url.toString.length > 80 then url.toString.take(80) + "..."
    else url
  }")