package com.example

import akka.actor.{Actor, ActorLogging, Props}

import scala.util.Random

class RedPacketGenerator extends Actor with ActorLogging {
    import RedPacketGenerator._
    import RedPacketClient._

    def receive = {
        
        case RedPacket => 
            val amount = Random.nextInt(100)
            log.info("Generate a new red packet!")
            sender ! UnopenedRedPacket(amount)
    }	

}

object RedPacketGenerator {
    case object RedPacket
}

