package vocabreminder

import akka.actor.{Actor, Props}
import info.mukel.telegrambot4s.api.declarative.Messages
import info.mukel.telegrambot4s.models.Message
import vocabreminder.VocabReminderUserSessionActor.userSessionActor

class VocabReminderBotActor(messages: Messages) extends Actor {

  import context._

  override def receive: Receive = {
    case msg: Message =>
      msg.from.map(_.id.toString)
        .foreach { userId =>
          child(userId)
            .getOrElse(actorOf(userSessionActor(messages, msg.source), userId)) ! msg
      }
  }
}

object VocabReminderBotActor {

  def botActor(messages: Messages) = Props(new VocabReminderBotActor(messages))
}
