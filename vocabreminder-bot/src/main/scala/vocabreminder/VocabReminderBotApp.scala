package vocabreminder

object VocabReminderBotApp extends App {
  import scala.util.Properties._

  val token = propOrNone("token")

  if (token.isEmpty) {
    println("No token provided in 'token' environment variable. Shutting down.")
  } else {
    val bot = new VocabReminderBot(token.get)
    bot.run()
  }
}
