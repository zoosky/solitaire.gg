import sbt._

object Dependencies {
  object Play {
    val version = "2.5.15"
    val playLib = "com.typesafe.play" %% "play" % version
    val playFilters = play.sbt.PlayImport.filters
    val playWs = play.sbt.PlayImport.ws
    val playJson = play.sbt.PlayImport.json
    val playTest = "com.typesafe.play" %% "play-test" % version
    val mailer = "com.typesafe.play" %% "play-mailer" % "5.0.0"
  }

  object Akka {
    private[this] val version = "2.5.1"
    val actor = "com.typesafe.akka" %% "akka-actor" % version
    val cluster = "com.typesafe.akka" %% "akka-cluster" % version
    val contrib = "com.typesafe.akka" %% "akka-contrib" % version
    val persistence = "com.typesafe.akka" %% "akka-persistence-experimental" % version
    val remoting = "com.typesafe.akka" %% "akka-remote" % version
    val log4j = "com.typesafe.akka" %% "akka-slf4j" % version
    val testkit = "com.typesafe.akka" %% "akka-testkit" % version
  }

  object Database {
    val postgresAsync = "com.github.mauricio" %% "postgresql-async" % "0.2.21" exclude("io.netty", "netty-all")
  }

  object Cache {
    val ehCache = "net.sf.ehcache" % "ehcache-core" % "2.6.11"
  }

  object Spark {
    val core = "org.apache.spark" %% "spark-core" % "1.4.1" exclude("org.slf4j", "slf4j-log4j12")
  }

  object WebJars {
    val requireJs = "org.webjars" % "requirejs" % "2.3.3"
    val bootstrap = "org.webjars" % "bootstrap" % "3.3.7"
    val underscore = "org.webjars" % "underscorejs" % "1.8.3"
    val d3 = "org.webjars" % "d3js" % "3.5.17"
    val nvd3 = "org.webjars" % "nvd3-community" % "1.7.0"
    val jquery = "org.webjars" % "jquery" % "2.2.4"
    val materialize = "org.webjars" % "materializecss" % "0.98.2"
  }

  object Serialization {
    val circeVersion = "0.8.0"
  }

  object Metrics {
    private[this] val version = "3.2.2"
    val jvm = "io.dropwizard.metrics" % "metrics-jvm" % version withSources()
    val ehcache = "io.dropwizard.metrics" % "metrics-ehcache" % version withSources() intransitive()
    val healthChecks = "io.dropwizard.metrics" % "metrics-healthchecks" % version withSources() intransitive()
    val json = "io.dropwizard.metrics" % "metrics-json" % version withSources()
    val servlets = "io.dropwizard.metrics" % "metrics-servlets" % version withSources() intransitive()
    val graphite = "io.dropwizard.metrics" % "metrics-graphite" % version withSources() intransitive()
    val metrics = "nl.grons" %% "metrics-scala" % "3.5.6" withSources()
    val jettyServlet = "org.eclipse.jetty" % "jetty-servlet" % "9.4.5.v20170502" withSources()
  }

  object ScalaJS {
    val jQueryVersion = "0.9.1"
    val scalaTagsVersion = "0.6.5"
    val domVersion = "0.9.2"
    val definitelyScalaVersion = "1.0.0"
  }

  object Utils {
    val scapegoatVersion = "1.3.0"

    private[this] val twitterVersion = "6.43.0"
    val core = "com.twitter" %% "util-core" % twitterVersion
    val collection = "com.twitter" %% "util-collection" % twitterVersion

    val enumeratumVersion = "1.5.14"

    val betterFiles = "com.github.pathikrit" %% "better-files" % "2.17.1"
  }

  object Testing {
    val uTestVersion = "0.4.7"

    val gatlingVersion = "2.2.5"
    val gatlingCore = "io.gatling" % "gatling-test-framework" % gatlingVersion % "test"
    val gatlingCharts = "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion % "test"
  }
}
