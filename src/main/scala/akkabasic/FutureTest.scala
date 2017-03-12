package akkabasic

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.concurrent.duration._

object FutureTest extends App {

  println("This is first.")

  val f = Future {
    println("Printing in the future")
  }

  Thread.sleep(2)
  println("This is last. ")

  val f2 = Future {
    for(i <- 1 to 30) yield i * i
    //throw new RuntimeException("Bad.")
  }

  //non-blocking
 /* f2.onComplete {
    case Success(n) => println(n)
    case Failure(ex) => println("Something went wrong. " + ex)
  }*/

  // non-blocking call
  // f2.foreach(println)

 // Thread.sleep(5000)

  //Blocking call/Not a best practice
  println(Await.result(f2, 5 seconds))

  val page1 = Future {
    "Google "+io.Source.fromURL("http://www.google.com").take(100).mkString
  }

  val page2 = Future {
   "Facebook "+io.Source.fromURL("http://www.facebook.com").take(100).mkString
  }

  val page3 = Future {
    "Youtube "+io.Source.fromURL("http://www.youtube.com").take(100).mkString
  }

  val pages = List(page1, page2, page3)

  // Returns one future - the one which completed first
/*  val firstPage = Future.firstCompletedOf(pages)
  firstPage.foreach(println)*/

  // Returns all Futures from the list
  val allPages = Future.sequence(pages)
  allPages.foreach(println)

  Thread.sleep(5000)
}
