/**
 * Entry file of the RedPacket program
 */

package com.example

import akka.actor.{ActorSystem, Props}
import akka.routing.RoundRobinPool

object RedPacketMain extends App {
    val system = ActorSystem("RedPacket")

    //default router supervision strategy is "Escalate", here we use "Resume" instead since we want generator not impacted by error.
    val generatorRouter = system.actorOf(RoundRobinPool(100, supervisorStrategy = ResumeSupervisor()).props(Props[RedPacketGenerator]), "generatorRouter")
    val clientRouter = system.actorOf(RoundRobinPool(100).props(Props(classOf[RedPacketClient], generatorRouter)), "clientRouter")
    val monitor = system.actorOf(Props[RedPacketMonitor], "redPacketMonitor")
    1 to 1000 foreach {
      _ => clientRouter ! RedPacketClient.Shake    
    } 
    //TODO we may need to refer akka's shutdown pattern(http://doc.akka.io/docs/akka/snapshot/scala/howto.html#Shutdown_Patterns_in_Akka_2) to stop the system gracefully.
    //system.shutdown()
}
