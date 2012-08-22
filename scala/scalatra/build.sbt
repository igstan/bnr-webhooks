name := "bnr-webhooks"

version := "1.0.0"

scalaVersion := "2.9.1"

net.virtualvoid.sbt.graph.Plugin.graphSettings

seq(webSettings :_*)

libraryDependencies ++= Seq(
  "org.scalatra"              % "scalatra"           % "2.1.0.M2",
  "org.scalatra"              % "scalatra-scalate"   % "2.1.0.M2",
  "org.scalamock"            %% "scalamock-scalatest-support"     % "latest.integration",
  "org.scalatra"              % "scalatra-scalatest" % "2.1.0.M2" % "test",
  "org.scalatest"            %% "scalatest"          % "1.8"      % "test",
  "org.hamcrest"              % "hamcrest-all"       % "1.3"      % "test",
  "junit"                     % "junit"              % "4.7"      % "test",
  "ch.qos.logback"            % "logback-classic"    % "1.0.0"    % "runtime",
  "javax.servlet"           % "javax.servlet-api" % "3.0.1"               % "provided",
  "org.eclipse.jetty.orbit" % "javax.servlet"     % "3.0.0.v201112011016" % "container" artifacts (
    Artifact("javax.servlet", "jar", "jar")),
  "org.eclipse.jetty"       % "jetty-webapp"      % "8.1.4.v20120524"     % "container" artifacts (
    Artifact("jetty-webapp", "jar", "jar"))
)
