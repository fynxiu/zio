package zio
package system

import scala.reflect.io.File

import zio.test.Assertion._
import zio.test._
import zio.test.environment.live

object SystemSpec extends ZIOBaseSpec {

  def spec = suite("SystemSpec")(
    suite("Fetch an environment variable and check that")(
      testM("If it exists, return a reasonable value") {
        assertM(live(system.env("PATH")))(isSome(containsString(File.separator + "bin")))
      },
      testM("If it does not exist, return None") {
        assertM(live(system.env("QWERTY")))(isNone)
      }
    ),
    suite("Fetch a VM property and check that")(
      testM("If it exists, return a reasonable value") {
        assertM(live(property("java.vm.name")))(isSome(containsString("VM")))
      },
      testM("If it does not exist, return None") {
        assertM(live(property("qwerty")))(isNone)
      }
    ),
    suite("Fetch the system's line separator and check that")(
      testM("it is identical to System.lineSeparator") {
        assertM(live(lineSeparator))(equalTo(java.lang.System.lineSeparator))
      }
    )
  )
}
