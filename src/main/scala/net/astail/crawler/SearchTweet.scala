package net.astail.crawler

import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.http.clients.rest.search

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object SearchTweet {

  def search = {
    val client = TwitterRestClient()
    val searchTw = client.searchTweet("test")

    Await.ready(searchTw, Duration.Inf)
    println(searchTw)
    client.shutdown()
  }
}
