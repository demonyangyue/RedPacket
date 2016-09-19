package com.example

import akka.actor.{ActorSystem, Actor, Props}
import akka.testkit.{ TestActorRef, TestKit, ImplicitSender }
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll

class RedPacketGeneratorSpec() extends TestKit(ActorSystem("RedPacketGeneratorSpec")) with ImplicitSender
with WordSpecLike with Matchers with BeforeAndAfterAll {

  import RedPacketGenerator._
  import RedPacketClient._


  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }


  "A red packet generator" must {
    val redPacketGenerator = TestActorRef(Props[RedPacketGenerator])

    "send out a RedPacket after shake" in {
      redPacketGenerator ! RedPacket
      expectMsgPF() {
        case UnopenedRedPacket (amount: Int) => (amount > 0 && amount < 100)
      }
    }

  }
}
