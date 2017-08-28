package com.example

import org.apache.spark.sql.SparkSession
import scala.util.{Failure, Success, Try}

object Main {

  def main(args: Array[String]) = {

    if (args.length != 2) {
      println(s"Wrong number of arguments: ${args.length}")
    }

    val spark = SparkSession.builder
      .appName("BytesCounter")
      .getOrCreate()

    spark.sparkContext.hadoopConfiguration
      .set("mapreduce.input.fileinputformat.input.dir.recursive", "true")

      spark.sparkContext.textFile(args(0))
        .map(_.split(" "))
        .map(arr => (arr(0), parseDouble(arr(9))))
        .groupBy(_._1)
        .mapValues(countStats)
        .saveAsTextFile(args(1))
  }

  def parseDouble(str: String): Double = Try(str.toDouble) match {
    case Success(num) => num
    case Failure(_) => 0
  }

  def countStats(iter: Iterable[(String, Double)]): String = {
    val byteList = iter.map(_._2)
    val sum = byteList.sum
    val avg = sum / byteList.size
    s"$avg, $sum"
  }
}
