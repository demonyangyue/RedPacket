package com.example

import akka.actor.{Actor, ActorRef, ActorLogging, Props}

class RedPacketShaker(val redPackerGenerator: ActorRef) extends Actor with ActorLogging {
  import RedPacketShaker._
  import RedPacketGenerator._
  

  def receive = {
      case Initialize =>
          redPackerGenerator ! Shake

      case RedPacket(amount: Int) => 
          log.info("Receive a $%d red packet".format(amount))
  }	

}

object RedPacketShaker {
    case object Initialize
    case class RedPacket(amount: Int)
}
