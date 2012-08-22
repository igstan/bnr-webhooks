package ro.igstan.bnr

import org.scalamock.ProxyMockFactory
import org.scalamock.scalatest.MockFactory
import org.scalatest.FunSpec
import org.scalatest.matchers.MustMatchers
import org.scalatra.scalate.ScalateSupport
import org.scalatra.ScalatraServlet
import org.scalatra.test.scalatest.ScalatraFunSuite

trait UrlStore

class LearningScalaTest extends ScalatraFunSuite with MustMatchers with MockFactory with ProxyMockFactory {

  var mockUrlStore: UrlStore = _

  addServlet(new MainServlet {
    val urlStore = mockUrlStore
  }, "/*")

  test("GET /hooks renders a form with the required action URL") {
    get("/hooks") {
      body must include("""<form action="/hooks" """)
    }
  }

  test("GET /hooks sends text/html content type headers") {
    get("/hooks") {
      header("Content-Type") must include("text/html")
    }
  }

  test("POST /hooks registers given URL") {
    val url = "http://www.example.com/bnr-webhook"
    mockUrlStore = mock[UrlStore]
    mockUrlStore expects 'store withArgs (url)

    post("/hooks", List("url" -> url)) {

    }
  }
}
