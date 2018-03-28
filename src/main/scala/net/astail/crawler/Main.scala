package net.astail.crawler

import java.io.File
import java.net.URL
import java.nio.file.{Files, Paths}

import javax.imageio.ImageIO

import collection.JavaConverters._
import scala.util.control.NonFatal
import org.jsoup._
import skinny.http._


object Main {
  def main(args: Array[String]): Unit = {
    jsoup
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

  def jsoup = {
    val result = Jsoup.connect("http://satlog.blog119.fc2.com/blog-entry-2943.html").get
    val dir = "/tmp/astel/"
    val mkdir = Paths.get(dir)
    if(Files.notExists(mkdir)) Files.createDirectories(mkdir)

    for (t <- result.select("img").asScala) {
      val x = t.attr("src")
      if (x.split('.').last == "jpg") {
        val url = x
        val name = x.split('/').last
        download(url, name, dir)
      }
    }
  }

  def skinny = {
    val response: Response = HTTP.get("http://satlog.blog119.fc2.com/blog-entry-2943.html")
    print(response.textBody)
//    if(response.textBody.contains("img")) println(response.textBody)
  }


  def scalaiosource: Unit = {
    val url = "http://satlog.blog119.fc2.com/blog-entry-2943.html"
    val result = scala.io.Source.fromURL(url).mkString
//    println(result contains "img")
  }
}
