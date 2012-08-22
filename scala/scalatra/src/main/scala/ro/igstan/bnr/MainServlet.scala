package ro.igstan.bnr

import org.scalatra.ScalatraServlet
import org.scalatra.scalate.ScalateSupport

class MainServlet extends ScalatraServlet with ScalateSupport {
  get("/hooks") {
    contentType = "text/html"
    templateEngine.layout("/WEB-INF/index.mustache", Map("postUrl" -> "/hooks"))
  }
}
