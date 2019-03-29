package com.simon.playbinding.controllers

import controllers.AssetsFinder
import javax.inject._
import play.api.mvc._

@Singleton
class Application @Inject()(cc: ControllerComponents, template: views.html.index
                            , assetsFinder: AssetsFinder) extends AbstractController(cc) {

  def index = Action { implicit request: Request[AnyContent] =>
    // uses the AssetsFinder API
    Ok(template(assetsFinder))
  }

}
