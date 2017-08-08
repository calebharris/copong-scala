enablePlugins(ScalaJSPlugin)
enablePlugins(ScalaJSBundlerPlugin)

name := "Copong Scala Client"
scalaVersion := "2.12.2" // or any other Scala version >= 2.10.2

resolvers += Resolver.jcenterRepo

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.3"
libraryDependencies += "com.outr" %%% "scalajs-pixijs" % "4.5.3"
libraryDependencies += "com.lihaoyi" %%% "utest" % "0.4.4" % "test"

testFrameworks += new TestFramework("utest.runner.Framework")

//skip in packageJSDependencies := false
npmDependencies in Compile ++= Seq(
  "faye" -> "1.2.4",
  "pixi.js" -> "4.5.3"
)
npmDevDependencies in Compile += "expose-loader" -> "0.7.1"

webpackConfigFile in fastOptJS := Some(baseDirectory.value / "copong.webpack.config.js")
