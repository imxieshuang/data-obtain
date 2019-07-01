package com.simon.playbinding.controllers

import javax.inject.Inject
import play.api.libs.ws._
import play.api.mvc._

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * TODO
  *
  * author: xieshuang8
  * time: 2019/4/28 13:55
  */
class WxController @Inject()(ws: WSClient, val controllerComponents: ControllerComponents) extends BaseController {
  def test() = Action {
    val loadData = Map[String, String]("client_id" -> "c3cef7c66a1843f8b3a9e6a1e3160e20",
      "grant_type" -> "password",
      "source" -> "com.zhihu.web",
      "username" -> "",
      "password" -> "",
      "lang" -> "en",
      "ref_source" -> "homepage",
      "utm_source" -> "")
    val headers = Map[String, String]("accept-encoding" -> "gzip, deflate, br",
      "Host" -> "www.zhihu.com",
      "Referer" -> "https://www.zhihu.com/",
      "User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
    val request = ws.url("https://www.zhihu.com/api/v3/oauth/captcha?lang=en")
    val eventualResponse = request.get()

    val response = Await.result(eventualResponse, 10 seconds)
    Ok(response.body)
  }
}
