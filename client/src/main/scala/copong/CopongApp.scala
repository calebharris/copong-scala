package copong

import com.outr.pixijs.PIXI.{Container, Sprite, autoDetectRenderer}
import com.outr.pixijs.{PIXI, RendererOptions}
import org.scalajs.dom
import dom.{document, window}

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
  }

  def update(time: Double): Unit = {
    renderer.render(stage)
    window.requestAnimationFrame(update)
  }

}
