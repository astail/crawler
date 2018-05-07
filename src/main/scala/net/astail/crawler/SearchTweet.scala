package net.astail.crawler

import com.danielasfregola.twitter4s.TwitterRestClient

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global


object SearchTweet {

  def search = {
    val client = TwitterRestClient()
    val searchTw = client.searchTweet("HHKB lang:ja", count = 3)

    searchTw.onComplete {
      case Success(msg) => for (tweet <- msg.data.statuses) {
        //println(s"${msg.data.statuses(0).text}")
        println(s"${tweet.id}, ${tweet.text}")
      }
      case Failure(t) => println(t.getMessage())
    }

    Await.ready(searchTw, Duration.Inf)
    client.shutdown()
  }
}


