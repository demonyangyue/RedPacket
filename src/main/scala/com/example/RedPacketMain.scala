package com.example

import akka.actor.{ActorSystem, Props}
import akka.routing.RoundRobinPool

object RedPacketMain extends App {
    val system = ActorSystem("RedPacket")
    //val redPacketGenerator = system.actorOf(Props[RedPacketGenerator], "redPacketGenerator")
    //val redPacketClient = system.actorOf(Props(classOf[RedPacketClient], redPacketGenerator), "redPacketClient")
    val generatorRouter = system.actorOf(RoundRobinPool(10, supervisorStrategy = ResumeSupervisor()).props(Props[RedPacketGenerator]), "generatorRouter")
    val clientRouter = system.actorOf(RoundRobinPool(10).props(Props(classOf[RedPacketClient], generatorRouter)), "clientRouter")
    val monitor = system.actorOf(Props[RedPacketMonitor], "redPacketMonitor")
    1 to 3 foreach {
      _ => clientRouter ! RedPacketClient.Shake    
    } 
    //system.shutdown()
}
