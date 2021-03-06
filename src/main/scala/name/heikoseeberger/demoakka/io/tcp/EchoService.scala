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
package tcp

import akka.actor.{ Actor, ActorLogging, Props }
import akka.io.{ IO, Tcp }
import java.net.InetSocketAddress

/**
 * Messages and `akka.actor.Props` factories for the [[EchoService]] actor.
 */
object EchoService {

  /**
   * Factory for `akka.actor.Props` for [[EchoService]].
   */
  def props(endpoint: InetSocketAddress): Props =
    Props(new EchoService(endpoint))
}

/**
 * Creates an I/O manager for TCP and registers itself as listener,
 * creating a new [[EchoConnectionHandler]] for each TCP connection.
 */
class EchoService(endpoint: InetSocketAddress) extends Actor with ActorLogging {

  import context.system

  IO(Tcp) ! Tcp.Bind(self, endpoint)

  override def receive: Receive = {
    case Tcp.Connected(remote, _) =>
      log.debug("Remote address {} connected", remote)
      sender ! Tcp.Register(context.actorOf(EchoConnectionHandler.props(remote, sender)))
  }
}
