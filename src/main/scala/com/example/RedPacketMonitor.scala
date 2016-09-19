package com.example

import akka.actor.{Actor, ActorRef, ActorLogging, Props}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random

class RedPacketMonitor extends Actor with ActorLogging {
  import RedPacketClient._
  var sum = 0

  override def preStart = context.system.eventStream.subscribe(self, classOf[Money])

  def receive = {
    case Money(amount: Int) => {
      sum += amount
      log.info("Send out $%d red packet in total!!!".format(sum))
    }
  }   

  override def postStop = context.system.eventStream.unsubscribe(self, classOf[Money])
}   


