package org.kibanaLoadTest.helpers

class Version(var str: String) extends Comparable[Version] {
  if (str == null)
    throw new IllegalArgumentException("Version can not be null")
  var value = str.toUpperCase
  if (!value.matches("[0-9]+(\\.[0-9]+)*(-SNAPSHOT)?"))
    throw new IllegalArgumentException(
      "Invalid version format, supported formats: '7.11.0' or '7.11.0-SNAPSHOT'"
    )

  val postfix = if (value.indexOf("-SNAPSHOT") > 0) "SNAPSHOT" else ""
  val version = value.replace("-SNAPSHOT", "")

  def isAbove79x: Boolean = {
    this.compareTo(new Version("7.10")) != -1
  }

  override def compareTo(that: Version): Int = {
    if (that == null) return 1
    val thisParts = this.version.split("\\.")
    val thatParts = that.version.split("\\.")
    val length = Math.max(thisParts.length, thatParts.length)
    for (i <- 0 until length) {
      val thisPart =
        if (i < thisParts.length) thisParts(i).toInt
        else 0
      val thatPart =
        if (i < thatParts.length) thatParts(i).toInt
        else 0
      if (thisPart < thatPart) return -1
      if (thisPart > thatPart) return 1
    }
    0
  }

  final def get: String = this.value
}
