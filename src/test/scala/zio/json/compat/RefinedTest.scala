package testzio.json.compat

import zio.json
import zio.json._
import testzio.json.TestUtils._
import eu.timepit.refined.api.{ Refined }

import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.auto._

import utest._
import zio.json.compat.refined._

// testOnly *RefinedTest
object RefinedTest extends TestSuite {

  case class Person(name: String Refined NonEmpty)

  object Person {
    implicit val decoder: JsonDecoder[Person] = DeriveJsonDecoder.gen[Person]
    implicit val encoder: JsonEncoder[Person] = DeriveJsonEncoder.gen[Person]
  }

  val tests = Tests {
    test("Refined") {
      """{"name":""}""".fromJson[Person] ==> Left(".name(Predicate isEmpty() did not fail.)")
      """{"name":"fommil"}""".fromJson[Person] ==> Right(Person("fommil"))

      Person("fommil").toJson ==> """{"name":"fommil"}"""
    }
  }

}
