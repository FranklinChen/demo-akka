/*
 * Copyright 2013 Heiko Seeberger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package name.heikoseeberger.demoakka
package io
package http

import akka.actor.ActorSystem
import scala.util.Properties.{ lineSeparator => newLine }

/**
 * Very simple Akka I/O based HTTP server listening on port `8080`.
 */
object EchoServiceApp extends App {

  val system = ActorSystem("demo-system")
  system.actorOf(EchoService.props("localhost", 8080), "echo-service")

  readLine(s"Hit ENTER to exit ...$newLine")
  system.shutdown()
}
