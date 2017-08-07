enablePlugins(ScalaJSPlugin)

name := "Copong Scala Client"
scalaVersion := "2.12.2" // or any other Scala version >= 2.10.2

resolvers += Resolver.jcenterRepo

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

libraryDependencies += "com.outr" %%% "scalajs-pixijs" % "4.5.3"
libraryDependencies += "com.lihaoyi" %%% "utest" % "0.4.4" % "test"

testFrameworks += new TestFramework("utest.runner.Framework")

skip in packageJSDependencies := false
jsDependencies += RuntimeDOM
jsDependencies += "org.webjars.npm" % "faye" % "1.2.4" / "cient/faye-browser.js"
