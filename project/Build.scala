import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "bugtracker"
    val appVersion      = "1.0"

    val appDependencies = Seq(
      "org.squeryl" % "squeryl_2.9.1" % "0.9.5-2",
      "org.mindrot" % "jbcrypt" % "0.3m" //,
//      "postgresql" % "postgresql" % "9.2-1002.jdbc4"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
//      resolvers += Resolver.url("jdbc 9.2 repo", url(""))
    )

}
