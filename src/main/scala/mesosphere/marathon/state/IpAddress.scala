package mesosphere.marathon.state

import mesosphere.marathon.Protos

import scala.collection.immutable.Seq
import scala.collection.JavaConverters._
import org.apache.mesos.{ Protos => mesos }

case class IpAddress(
    groups: Seq[String] = Nil,
    labels: Map[String, String] = Map.empty[String, String]) {

  def toProto: Protos.IpAddress = {
    val builder = Protos.IpAddress.newBuilder
    groups.foreach(builder.addGroups)
    labels
      .map { case (key, value) => mesos.Label.newBuilder.setKey(key).setValue(value).build }
      .foreach(builder.addLabels)
    builder.build
  }
}

object IpAddress {

  object Empty extends IpAddress

  def fromProto(proto: Protos.IpAddress): IpAddress = {
    IpAddress(
      groups = proto.getGroupsList.asScala.toIndexedSeq,
      labels = proto.getLabelsList.asScala.map { p => p.getKey -> p.getValue }.toMap
    )
  }
}