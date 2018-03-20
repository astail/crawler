package net.astail.crawler

import java.io.File
import java.net.URL
import javax.imageio.ImageIO

import collection.JavaConverters._
import org.jsoup._

object Main {
  def main(args: Array[String]): Unit = {
    val doc = Jsoup.connect("http://satlog.blog119.fc2.com/blog-entry-2943.html").get

    for (t <- doc.select("img").asScala) {
      val x = t.attr("src")
      if (x.split('.').last == "jpg") {
        val url = x
        val name = x.split('/').last
        val dir = "/tmp/astel/"
        download(url, name, dir)
      }
    }
  }

  def download(url: String, name: String, dir: String) = {
    val f = new File(dir + name)
    val u = new URL(url)
    val r = ImageIO.read(u)
    ImageIO.write(r, "jpg", f)
  }
}