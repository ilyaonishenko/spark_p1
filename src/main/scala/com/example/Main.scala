package com.example

import eu.bitwalker.useragentutils.UserAgent
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

import scala.util.{Failure, Success, Try}

object Main {

  def main(args: Array[String]) = {

    if (args.length != 3) {
      println(s"Wrong number of arguments: ${args.length}.\n Should be 3!")
    }

    val spark = SparkSession.builder
      .appName("BytesCounter")
      .getOrCreate()

    spark.sparkContext.hadoopConfiguration
      .set("mapreduce.input.fileinputformat.input.dir.recursive", "true")

    val file = spark.sparkContext.textFile(args(0)).map(_.split(" "))


    file
      .map(arr => (arr(0), parseDouble(arr(9))))
      .groupBy(_._1)
      .mapValues(countStats)
      .saveAsTextFile(args(1))

    file
      .map(arr => detectBrowser(arr(11)))
      .groupBy((browser: String) => browser)
      .mapValues(iter => iter.size)
//      .map(browsers => (browsers._1, browsers._2.size))
      .saveAsTextFile(args(2))

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

  def detectBrowser(str: String): String = {
    val browser = UserAgent.parseUserAgentString(str).getBrowser
    browser.getName
  }
}
