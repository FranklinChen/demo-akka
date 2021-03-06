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

import akka.actor.{ Actor, ActorLogging, ActorRef, Props, Terminated }
import akka.io.Tcp
import java.net.InetSocketAddress
import spray.http.{ HttpRequest, HttpResponse }
import spray.http.HttpMethods._

/**
 * Messages and `akka.actor.Props` factories for the [[EchoConnectionHandler]] actor.
 */
object EchoConnectionHandler {

  /**
   * Factory for `akka.actor.Props` for [[EchoConnectionHandler]].
   */
  def props(remote: InetSocketAddress, connection: ActorRef): Props =
    Props(new EchoConnectionHandler(remote, connection))
}

/**
 * Handles a single HTTP connection by simply echoing the local path of the URI for a GET request.
 * In the real world, better use spray-routing instead of pattern matching on the HTTP model.
 */
class EchoConnectionHandler(remote: InetSocketAddress, connection: ActorRef) extends Actor with ActorLogging {

  // We need to know when the connection dies without sending a `Tcp.ConnectionClosed`
  context.watch(connection)

  def receive: Receive = {
    case HttpRequest(GET, uri, _, _, _) =>
      sender ! HttpResponse(entity = uri.path.toString)
    case _: Tcp.ConnectionClosed =>
      log.debug("Stopping, because connection for remote address {} closed", remote)
      context.stop(self)
    case Terminated(`connection`) =>
      log.debug("Stopping, because connection for remote address {} died", remote)
      context.stop(self)
  }
}
