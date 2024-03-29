import play.api.{Application, GlobalSettings}
import play.api.db.DB
import org.squeryl.adapters.H2Adapter
import org.squeryl.{Session, SessionFactory}

object Global extends GlobalSettings {
  override def onStart(app: Application) {
    SessionFactory.concreteFactory = Some(() =>
      Session.create(DB.getConnection()(app), new H2Adapter))
  }
}