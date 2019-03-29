import sbtcrossproject.{crossProject, CrossType}

lazy val scalaV = "2.12.4"
lazy val jQueryV = "2.2.4"
lazy val semanticV = "2.2.10"

lazy val server = (project in file("server")).settings(commonSettings).settings(
  scalaVersion := scalaV,
  scalaJSProjects := Seq(client),
  pipelineStages in Assets := Seq(scalaJSPipeline),
  pipelineStages := Seq(digest, gzip),
  // triggers scalaJSPipeline when using compile or continuous compilation
  compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
  libraryDependencies ++= Seq(
    "com.vmunier" %% "scalajs-scripts" % "1.1.2",
    guice,
    specs2 % Test,
    // webjars for Semantic-UI
     "org.webjars" %% "webjars-play" % "2.6.1",
     "org.webjars" % "Semantic-UI" % semanticV,
     "org.webjars" % "jquery" % jQueryV,
  ),
  // to have routing also in ScalaJS
  // Create a map of versioned assets, replacing the empty versioned.js
  DigestKeys.indexPath := Some("javascripts/versioned.js"),
  // Assign the asset index to a global versioned var
  DigestKeys.indexWriter ~= { writer => index => s"var versioned = ${writer(index)};" },
  // Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
  EclipseKeys.preTasks := Seq(compile in Compile)
).enablePlugins(PlayScala).
  dependsOn(sharedJvm)

lazy val client = (project in file("client")).settings(commonSettings).settings(
  scalaVersion := scalaV,
  scalaJSUseMainModuleInitializer := true,
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
  jsDependencies ++= Seq(
    "org.webjars" % "jquery" % jQueryV / "jquery.js" minified "jquery.min.js",
    "org.webjars" % "Semantic-UI" % semanticV / "semantic.js" minified "semantic.min.js" dependsOn "jquery.js"
  ),
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.5",
    "com.thoughtworks.binding" %%% "dom" % "latest.release",
    "com.thoughtworks.binding" %% "futurebinding" % "latest.release"
  )
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
  dependsOn(sharedJs)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(commonSettings)
lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val commonSettings = Seq(
  scalaVersion := "2.12.5",
  organization := "com.simon"
)

// loads the server project at sbt startup
onLoad in Global := (onLoad in Global).value andThen {s: State => "project server" :: s}
