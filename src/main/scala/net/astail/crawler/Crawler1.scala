package net.astail.crawler

import java.io.File
import java.net.URL
import java.nio.file.{Files, Paths}

import javax.imageio.ImageIO

import collection.JavaConverters._
import scala.util.control.NonFatal
import org.jsoup._
import com.typesafe.config.ConfigFactory
import scalikejdbc._
import scalikejdbc.config.DBs

object Crawler1 {
  def main {
    val URL = ConfigFactory.load.getString("crawler1_url")
    val DIR = ConfigFactory.load.getString("crawler1_dir")

    val mkdir = Paths.get(DIR)
    if (Files.notExists(mkdir)) Files.createDirectories(mkdir)
    jsoup(URL,DIR)
  }

  def urlIdOf(url: String)(implicit session: DBSession): Option[Int] = {
    sql"""select id from urls where url = $url""".map(_.int("id")).single.apply()
  }

  def insert(url: String, name: String)(implicit session: DBSession): Int = {
    sql"""INSERT INTO urls(url, name) VALUES ($url, $name)""".updateAndReturnGeneratedKey.apply().toInt
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
    DBs.setup('default)
    DB.autoCommit { implicit session => insert(url,name) }
  }

  def jsoup(url: String, dir: String) = {
    val result = Jsoup.connect(url).get

    for (t <- result.select("img").asScala) {
      val x = t.attr("src")
      if (x.split('.').last == "jpg") {
        val url = x
        val name = x.split('/').last

        DBs.setup('default)
        val hoge = DB.readOnly { implicit session =>
          urlIdOf(url)
        }

        hoge match {
          case Some(s) => println(s"すでに結果がある, id: $s")
          case None => download(url, name, dir)
        }
      }
    }
  }
}
