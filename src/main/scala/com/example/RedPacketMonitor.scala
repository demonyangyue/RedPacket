/**
 * Statistics the total red packet amount
 */

package com.example

import akka.actor.{Actor, ActorRef, ActorLogging, Props}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random

class RedPacketMonitor extends Actor with ActorLogging {
  import RedPacketClient._
  //safe to have variable in actor as long as we change it through messages
  var sum = 0

  // using system event stream to receive certain message, instead of directly talking to the actor.
  // In a production envrionment, we may need to use RabbitMQ or Kafka to implement the message queue.
  override def preStart = context.system.eventStream.subscribe(self, classOf[Money])

  def receive = {
    case Money(amount: Int) => {
      sum += amount
      //TODO we may use akka agent to do the statistics in the future
      log.info("Send out $%d red packet in total!!!".format(sum))
    }
  }   

  override def postStop = context.system.eventStream.unsubscribe(self, classOf[Money])
}   


