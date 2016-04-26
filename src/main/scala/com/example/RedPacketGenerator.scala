package com.example

import akka.actor.{Actor, ActorLogging, Props}

import scala.util.Random

class RedPacketGenerator extends Actor with ActorLogging {
  import RedPacketGenerator._
  import RedPacketShaker._
  
  def receive = {
  	case Shake => 
        val amount = Random.nextInt(100)
        log.info("Generate a $%d red packet".format(amount))
        sender ! RedPacket(amount)
  }	

}

object RedPacketGenerator {
    case object Shake
}

