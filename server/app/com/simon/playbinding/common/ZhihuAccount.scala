package com.simon.playbinding.common

import java.io._
import java.net.HttpCookie
import java.util.Base64

import play.api.libs.json.Json
import play.api.libs.ws.WSClient

import scala.collection.mutable

/**
  * TODO
  *
  * author: xieshuang8
  * time: 2019/4/28 16:40
  */
class ZhihuAccount(username: String, password: String, ws: WSClient) {
  val loginData = mutable.Map[String, String]("client_id" -> "c3cef7c66a1843f8b3a9e6a1e3160e20",
    "grant_type" -> "password",
    "source" -> "com.zhihu.web",
    "username" -> "",
    "password" -> "",
    "lang" -> "en",
    "ref_source" -> "homepage",
    "utm_source" -> "")

  val headers = mutable.Map[String, String]("accept-encoding" -> "gzip, deflate, br",
    "Host" -> "www.zhihu.com",
    "Referer" -> "https://www.zhihu.com/",
    "User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36"
  )
  val cookies = mutable.Map[String, HttpCookie]()

  def login(captcha:String): Unit = {
    loginData.updated("username", username).updated("password", password)

  }

  /**
    * 请求验证码的 API 接口，无论是否需要验证码都需要请求一次
    * 如果需要验证码会返回图片的 base64 编码
    * 根据 lang 参数匹配验证码，需要人工输入
    *
    * @param lang lang: 返回验证码的语言(en/cn)
    * @return 验证码的 POST 参数
    */
  def getCaptcha(): String = {
    var api = ""
    api = "https://www.zhihu.com/api/v3/oauth/captcha?lang=en"
    val response = requests.get(api, headers = headers)
    if (response.text().contains("true")) {
      val putRes = requests.put(api, headers = headers)
      val jsonData = Json.parse(putRes.text())
      val imgBase64 = jsonData("img_base64").toString().replace("\n", "")
      val bytes = Base64.getDecoder.decode(imgBase64)
      val stream = new FileOutputStream("./captcha.jpg")
      stream.write(bytes)
      stream.close()
    }
    null
  }

  def inputCaptcha(api: String, captcha: String): Unit = {
    requests.post(api, data = captcha)
  }


}

object test {
  def main(args: Array[String]): Unit = {
    val str = "bcadefg"
    println(str.contains("a"))
  }
}
