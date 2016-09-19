package com.example

import akka.actor.{Actor, ActorRef, ActorLogging, Props}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random

class RedPacketClient(val generator: ActorRef) extends Actor with ActorLogging {
  import RedPacketClient._
  import RedPacketGenerator._

  implicit val askTimeout = Timeout(1.second)
  def receive = {

    case Shake =>
      (generator ? RedPacket) onSuccess {
        case UnopenedRedPacket(amount) => self ! OpenPacket(amount)
      }

        case OpenPacket(amount: Int) => 
          context.system.eventStream.publish(Money(amount))
          log.info("Open a red packet, get $%d".format(amount))
  }
}

object RedPacketClient {
  case object Shake
  case class UnopenedRedPacket(amount: Int)
  case class OpenPacket(amount: Int)
  case class Money(amount: Int)
}
