package vocabreminder

import akka.actor.{Actor, Props}
import info.mukel.telegrambot4s.api.declarative.Messages
import info.mukel.telegrambot4s.methods.SendMessage
import info.mukel.telegrambot4s.models.Message

import scala.concurrent.duration._
import scala.util.Random

class VocabReminderUserSessionActor(messages: Messages, chatId: Long) extends Actor {

  var list: Vector[String] = Vector[String]()
  val random: Random.type = Random

  private def scheduleNextItem(): Unit = {
    import context.dispatcher
    context.system.scheduler.scheduleOnce(30 seconds, self, "sendNextItem")
  }

  scheduleNextItem()

  override def receive: Receive = {
    case msg: Message =>
      println(s"Received ${msg.text} (${msg.from})")
      list = msg.text.map(_ +: list).getOrElse(list)

    case "sendNextItem" =>
      if (list.nonEmpty) {
        messages.request(
          SendMessage(chatId, list(random.nextInt(list.size - 1))))

        scheduleNextItem()
      }
  }
}

object VocabReminderUserSessionActor {

  def userSessionActor(messages: Messages, chatId: Long) = Props(new VocabReminderUserSessionActor(messages, chatId))

}
