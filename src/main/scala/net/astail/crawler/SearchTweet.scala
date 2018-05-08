package net.astail.crawler

import com.danielasfregola.twitter4s.TwitterRestClient

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global


object SearchTweet {

  def search = {
    val client = TwitterRestClient()
    val searchTw = client.searchTweet("HHKB lang:ja", count = 10)

    searchTw.onComplete {
      case Success(msg) => for (tweet <- msg.data.statuses) {
        println(s"${tweet.id}, ${tweet.user.get.name}, @${tweet.user.get.screen_name}, ${tweet.text}")
      }
      case Failure(t) => println(t.getMessage())
    }

    Await.ready(searchTw, Duration.Inf)
    client.shutdown()
  }
}
