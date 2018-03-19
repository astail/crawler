package net.astail.crawler

import java.io.File
import java.net.URL
import javax.imageio.ImageIO

object Main {
  def main(args: Array[String]): Unit = {
    val url = "https://astail.net/wp-content/uploads/2017/07/IMG_0909-e1499686509121.jpg"
    val name = "img.jpg"
    val dir = "/tmp/"
    download(url, name, dir)
  }

  def download(url: String, name: String, dir: String ) = {
    val f = new File(dir + name)
    val u = new URL(url)
    val r = ImageIO.read(u)
    ImageIO.write(r,"jpg",f)
  }
}