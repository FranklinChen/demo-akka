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

import akka.actor.{ Actor, ActorLogging, Props }
import akka.io.IO
import spray.can.Http

/**
 * Messages and `akka.actor.Props` factories for the [[EchoService]] actor.
 */
object EchoService {

  /**
   * Factory for `akka.actor.Props` for [[EchoService]].
   */
  def props(host: String, port: Int): Props =
    Props(new EchoService(host, port))
}

/**
 * Creates an I/O manager for HTTP and registers itself as listener,
 * creating a new [[EchoConnectionHandler]] for each HTTP connection.
 */
class EchoService(host: String, port: Int) extends Actor with ActorLogging {

  import context.system

  IO(Http) ! Http.Bind(self, host, port)

  override def receive: Receive = {
    case Http.Connected(remote, _) =>
      log.debug("Remote address {} connected", remote)
      sender ! Http.Register(context.actorOf(EchoConnectionHandler.props(remote, sender)))
  }
}
