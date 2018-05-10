package net.astail.crawler

import com.danielasfregola.twitter4s.TwitterRestClient

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import sys.process.urlToProcess
import java.net.URL
import java.io.File

object SearchTweet {

  def search = {
    val client = TwitterRestClient()
    val searchTw = client.searchTweet("HHKB lang:ja", count = 500)

    searchTw.onComplete {
      case Success(msg) => for (tweet <- msg.data.statuses) {

        val images = tweet.extended_entities.map { x =>
          x.media.map(_.media_url)
        }

        println(s"${tweet.id}, ${tweet.user.get.name}, @${tweet.user.get.screen_name}, ${tweet.text}, ${images.map(_.mkString(",")).getOrElse("NoData")}")

        images match  {
          case Some(x) => for(yy <- x) download(yy)
          case None => println("noImage")
        }
      }
      case Failure(t) => println(t.getMessage())
    }

    Await.ready(searchTw, Duration.Inf)
    client.shutdown()
  }

  def download(url: String): Future[String] = Future {
    val name = url.split("/").reverse.toList.headOption.get
    new URL(url) #> new File(s"./get_files/${name}") !!
  }
}
