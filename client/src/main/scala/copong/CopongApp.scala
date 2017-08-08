package copong

import com.outr.pixijs.PIXI.{Container, Sprite, autoDetectRenderer}
import com.outr.pixijs.RendererOptions
import org.scalajs.dom.{document, window}
import copong.facades.faye.Faye

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

/**
  * @author Caleb Harris
  */

object CopongApp {

  val GAME_W = 640
  val GAME_H = 480
  val BALL_FILE = "public/assets/aqua_ball.png"
  val PADDLE_FILE = "public/assets/phaser.png"

  val renderer = autoDetectRenderer(new RendererOptions {
    width = GAME_W
    height = GAME_H
  })
  val stage = new Container()

  def main(args: Array[String]): Unit = {
    document.body.appendChild(renderer.view)
    val ballSprite = Sprite.fromImage(BALL_FILE)
    ballSprite.x = GAME_W / 2
    ballSprite.y = GAME_H / 2

    stage.addChild(ballSprite)
    window.requestAnimationFrame(update)

    val client = new Faye.Client("/topics")
    client.subscribe("/topics/game/state", (event: GameStateEvent) => {
      ballSprite.x = event.state.ball.pos.x
      ballSprite.y = event.state.ball.pos.y
    })
  }

  def update(time: Double): Unit = {
    renderer.render(stage)
    window.requestAnimationFrame(update)
  }
}
