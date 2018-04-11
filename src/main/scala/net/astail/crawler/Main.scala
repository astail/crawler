package net.astail.crawler

import java.io.File
import java.net.URL
import java.nio.file.{Files, Paths}

import javax.imageio.ImageIO

import collection.JavaConverters._
import scala.util.control.NonFatal
import org.jsoup._
import com.typesafe.config.ConfigFactory


object Main {
  def main(args: Array[String]): Unit = {
    val CONF = ConfigFactory.load
    val URL = CONF.getString("set_url")

    jsoup(URL)
  }

  def download(url: String, name: String, dir: String) = {
    try {
      val f = new File(dir + name)
      val u = new URL(url)
      val r = ImageIO.read(u)
      ImageIO.write(r, "jpg", f)
    } catch {
      case NonFatal(t) => println(t)
    }
  }

  def jsoup(url: String) = {
    val result = Jsoup.connect(url).get
    val dir = "./get_files/"
    val mkdir = Paths.get(dir)
    if (Files.notExists(mkdir)) Files.createDirectories(mkdir)

    for (t <- result.select("img").asScala) {
      val x = t.attr("src")
      if (x.split('.').last == "jpg") {
        val url = x
        val name = x.split('/').last
        download(url, name, dir)
      }
    }
  }
}
