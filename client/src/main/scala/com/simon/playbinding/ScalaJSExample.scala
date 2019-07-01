package com.simon.playbinding

import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.html.{Button, Div}
import org.scalajs.dom.{Event, document}
import org.querki.jquery._

object ScalaJSExample {

  def main(args: Array[String]): Unit = {
//    dom.render(document.body, bindingButton)
//    dom.render(document.body, bindingDiv)
    dom.render(document.body,dataObtain)
  }

  @dom
  def bindingButton(): Binding[Button] = {
    <button class="ui orange button"
            onclick={event: Event =>
              org.scalajs.dom.window.alert("hello world!")
            $.get("http:baidu.com")}>
      Modify the name
    </button>
  }

  @dom
  def bindingDiv(): Binding[Div] = {
    <div class="row">
      <div class="col-md-1">.col-md-1</div>
      <div class="col-md-1">.col-md-1</div>
      <div class="col-md-1">.col-md-1</div>
      <div class="col-md-1">.col-md-1</div>
      <div class="col-md-1">.col-md-1</div>
      <div class="col-md-1">.col-md-1</div>
      <div class="col-md-1">.col-md-1</div>
      <div class="col-md-1">.col-md-1</div>
      <div class="col-md-1">.col-md-1</div>
      <div class="col-md-1">.col-md-1</div>
      <div class="col-md-1">.col-md-1</div>
      <div class="col-md-1">.col-md-1</div>
    </div>
  }

  @dom
  def dataObtain():Binding[Div]={
    <div class="container">
      <div class="row">
        <div class="col-sm">
          三分之一空间占位
        </div>
        <div class="col-sm">
          三分之一空间占位
        </div>
        <div class="col-sm">
          三分之一空间占位
        </div>
      </div>
    </div>
  }
}
