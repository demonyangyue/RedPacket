package com.example

import akka.actor.{ActorSystem, Actor, Props}
import akka.testkit.{ TestActorRef, TestKit, ImplicitSender, TestProbe }
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll
import akka.testkit.EventFilter
import com.typesafe.config.ConfigFactory

class RedPacketClientSpec() extends TestKit(ActorSystem("RedPacketClientSpec", ConfigFactory.parseString("""akka.loggers = ["akka.testkit.TestEventListener"]"""))) with ImplicitSender
with WordSpecLike with Matchers with BeforeAndAfterAll {

  import RedPacketGenerator._
  import RedPacketClient._


  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "A red packet Client" must {
    val redPacketClient = TestActorRef(Props(classOf[RedPacketClient], testActor))

    "receive a RedPacket after Shake" in {
      redPacketClient.receive(Shake)
      expectMsgPF() {
        case RedPacket =>
      }
    }

    "output the amount after open" in {
      val amount = 100
      EventFilter.info(pattern = "Open a red packet, get \\$" + amount, occurrences = 1) intercept {
        redPacketClient.receive(OpenPacket(amount))
      }
    }

  }
}
