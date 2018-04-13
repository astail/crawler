package net.astail.crawler

import twitter4j._

object Crawler2 {

  // TwitterFactory オブジェクトを生成
  val factory = new TwitterFactory()
  // Twitter オブジェクトを生成
  val twitter = factory.getInstance()

  val message = "test tweet1111."

  def status = {
    twitter.updateStatus(message)
    println(s"Successfully updated the status to [${message}].")
  }
}