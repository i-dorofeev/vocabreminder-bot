package vocabreminder

import info.mukel.telegrambot4s.api.declarative.Commands
import info.mukel.telegrambot4s.api.{Polling, TelegramBot}
import vocabreminder.VocabReminderBotActor.botActor

class VocabReminderBot(val token: String) extends TelegramBot with Polling with Commands {

  private val botActorRef = system.actorOf(botActor(this))

  onMessage { implicit msg => botActorRef ! msg }
}
