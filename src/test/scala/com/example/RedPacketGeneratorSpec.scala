package com.example

import akka.actor.{ActorSystem, Actor, Props}
import akka.testkit.{ TestActorRef, TestKit, ImplicitSender }
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll

class RedPacketGeneratorSpec() extends TestKit(ActorSystem("RedPacketGeneratorSpec")) with ImplicitSender
with WordSpecLike with Matchers with BeforeAndAfterAll {
    
    import RedPacketGenerator._
    import RedPacketShaker._


    override def afterAll {
        TestKit.shutdownActorSystem(system)
    }

    "A red packet shaker" must {
        "Shake  after initialized" in {
            val redPacketShaker = TestActorRef(Props(classOf[RedPacketShaker], testActor))
            redPacketShaker.receive(Initialize)
            expectMsgPF() {
                case Shake =>
            }
        }
    }
    
    "A red packet generator" must {
        "send out a red packet after shaking" in {
            val redPacketGenerator = system.actorOf(Props[RedPacketGenerator])
            redPacketGenerator ! Shake
            expectMsgPF() {
                case RedPacket(amount: Int) => (amount > 0 && amount < 100)
            }
        }
    }
}
