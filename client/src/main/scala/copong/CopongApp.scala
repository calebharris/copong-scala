package copong

import com.outr.pixijs.PIXI.Sprite.fromImage
import com.outr.pixijs.PIXI.{Container, Sprite, autoDetectRenderer}
import com.outr.pixijs.RendererOptions
import copong.facades.faye.Faye
import org.scalajs.dom.html.Canvas
import org.scalajs.dom.raw.KeyboardEvent
import org.scalajs.dom.{document, window}
import rxscalajs.Observable

/**
  * @author Caleb Harris
  */

case class Dimension2D(width: Int, height: Int)

case class SpriteState(ball: Sprite, paddle: Sprite) {
  def handle(event: GameStateEvent): Unit = {
    ball.position.set(event.state.ball.pos.x, event.state.ball.pos.y)
    paddle.position.set(event.state.paddle.pos.x, paddle.y)
  }
}

object PlayingField {

  private val gameSize = Dimension2D(640, 480)

  private val renderer = autoDetectRenderer(new RendererOptions {
    width = gameSize.width
    height = gameSize.height
  })

  private val stage = new Container()

  private val createSprite = (image: String, initX: Double, initY: Double) => {
    val sprite = fromImage(image)
    sprite.position.set(initX, initY)
    sprite
  }

  def setup(ballImage: String,
            paddleImage: String,
            attachView: (Canvas) => Unit) = {
    val ballSprite = createSprite(ballImage, 0, gameSize.height / 2)
    val paddleSprite = createSprite(paddleImage, gameSize.width / 2, gameSize.height - 31)
    stage.addChild(ballSprite)
    stage.addChild(paddleSprite)

    val state = SpriteState(ballSprite, paddleSprite)

    val client = new Faye.Client("/topics")
    Observable.create[GameStateEvent]((obs) => {
      client.subscribe[GameStateEvent]("/topics/game/state", obs.next(_))
      ()
    }).subscribe(state.handle _)

    val keyDownObs = Observable.create[KeyboardEvent](
      obs => window.addEventListener[KeyboardEvent]("keydown", obs.next(_))
    )
    val keyUpObs = Observable.create[KeyboardEvent](
      obs => window.addEventListener[KeyboardEvent]("keyup", obs.next(_))
    )

    keyDownObs.merge(keyUpObs)
      .filter(evt => Seq("ArrowLeft", "Left", "ArrowRight", "Right").contains(evt.key))
      .distinctUntilChanged((l, r) => l.key == r.key && l.`type` == r.`type`)
      .map(evt => new GameInputEvent(
        name = if (evt.`type` == "keyup") "keyUp" else "keyDown",
        payload = new GameInputPayload(
          key = if (Seq("Left", "ArrowLeft").contains(evt.key)) "cursorLeft" else "cursorRight"
        )
      ))
      .subscribe(client.publish("/topics/game/controls", _))

    attachView(renderer.view)
  }

  def renderFrame(time: Double) = {
      renderer.render(stage)
  }
}

object CopongApp {

  def main(args: Array[String]): Unit = {
    PlayingField.setup(
      "public/assets/aqua_ball.png",
      "public/assets/phaser.png",
      canvas => document.body.appendChild(canvas)
    )

    lazy val update: (Double) => Unit = (time) => {
      PlayingField.renderFrame(time)
      window.requestAnimationFrame(update)
    }

    window.requestAnimationFrame(update)
  }
}
