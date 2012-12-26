package controllers

import play.api.mvc.{Controller}
import views.html
import models.Bug


/**
 *
 * User: Alexandros Bantis
 * Date: 12/19/12
 * Time: 9:00 AM
 */
object Application extends Controller with Secured {

  def index = withAuth {username => implicit request =>
    Redirect(routes.Auth.login())
  }

  def welcome = withAuth { username => implicit request =>
    Ok("hello")
  }

  def list = withAuth { username => implicit request =>
    val bugs = Bug.getAllBugs
    Ok(html.list(bugs))
  }



//  def user = withUser { user => implicit request =>
//    val username = user.id
//    Ok(html.user(user))
//  }

}



