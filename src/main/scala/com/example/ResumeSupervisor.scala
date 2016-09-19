package com.example


import akka.actor.{SupervisorStrategy, OneForOneStrategy}
import akka.actor.{ActorKilledException, ActorInitializationException}
import akka.actor.SupervisorStrategy._

object ResumeSupervisor {
  def apply() = OneForOneStrategy() {
    case _: ActorInitializationException => Stop
    case _: ActorKilledException => Stop
    case _: Exception => Resume
    case _ => Escalate
  }
}
