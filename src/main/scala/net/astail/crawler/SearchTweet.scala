package net.astail.crawler

import com.danielasfregola.twitter4s.TwitterRestClient

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global


object SearchTweet {

  def search = {
    val client = TwitterRestClient()
    val searchTw = client.searchTweet("モンエナてすとほげ", count = 10)


    searchTw.onComplete {
      case Success(msg) => for (tweet <- msg.data.statuses) {

        //println(s"${tweet.entities}")
        //println(s"${tweet.extended_entities}")
        //tweet.entities.get.media.foreach(x => println(x.media_url))

        //画像urlだけを取ってくる
        //tweet.extended_entities.get.media.foreach(x => println(x.media_url))

        val imageGet = tweet.extended_entities.get.media.map(_.media_url)

        ////println(s"${tweet.entities.get.media.head.media_url}")

        println(s"${tweet.id}, ${tweet.user.get.name}, @${tweet.user.get.screen_name}, ${tweet.text}, ${imageGet.mkString(",")}")

      }
      case Failure(t) => println(t.getMessage())
    }

    Await.ready(searchTw, Duration.Inf)
    client.shutdown()
  }
}
