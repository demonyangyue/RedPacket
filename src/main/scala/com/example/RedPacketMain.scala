package com.example

import akka.actor.{ActorSystem, Props}

object RedPacketMain extends App {
    val system = ActorSystem("RedPacket")
    val redPacketGenerator = system.actorOf(Props[RedPacketGenerator], "redPacketGenerator")
    val redPacketShaker = system.actorOf(Props(classOf[RedPacketShaker], redPacketGenerator), "redPacketShaker")
    redPacketShaker ! RedPacketShaker.Initialize
}
