package net.astail.crawler

import com.danielasfregola.twitter4s.TwitterRestClient


import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration.Duration
import scala.util.{ Success, Failure }

object Crawler3t {

  def astelTenGet = {
    val COUNT = 10
    val USER = "astel4696"
    val client = TwitterRestClient()
    val timeLine = client.userTimelineForUser(USER, count = COUNT)

    timeLine.onComplete {
      case Success(msg) => for (tweet <- msg.data) {
        println(s"${tweet.id}, ${tweet.text}")
      }
      case Failure(t) => println(t.getMessage())
    }

    Await.ready(timeLine, Duration.Inf)
    client.shutdown()
  }
}
