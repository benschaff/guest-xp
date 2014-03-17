package com.mediapadtech.guestxp.controllers

import com.mediapadtech.guestxp.models.restaurant.Restaurant
import com.mediapadtech.guestxp.models.restaurant.JsFormat.restaurantFormat
import org.specs2.mock.Mockito
import org.specs2.mutable._
import play.api.libs.json._
import play.api.test.Helpers._
import play.api.test._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import reactivemongo.api.collections.GenericQueryBuilder
import play.api.libs.json.JsArray
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.libs.json.JsObject
import reactivemongo.api.Cursor
import play.api.mvc.Controller

/**
 * This file is part of Guest eXPerience.
 *
 * Guest eXPerience is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * Guest eXPerience is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with Guest eXPerience.  If not, see <http://www.gnu.org/licenses/>.
 *
 * "Copyright 2014 Benjamin Schaff"
 */
object RestaurantAPISpec extends Specification with Mockito {

  private val Owner: String = "owner"

  "Restaurant api" should {

    "return an empty list" in {
      val restaurantAPI = initializeRestaurantAPI(List.empty[Restaurant])

      val result = restaurantAPI.list(Owner)(FakeRequest())

      status(result) must equalTo(OK)
      contentType(result) must beSome("application/json")
      charset(result) must beSome("utf-8")

      contentAsJson(result).as[JsArray].value must beEmpty
    }

  }

  private def initializeRestaurantAPI(response: List[Restaurant]): RestaurantAPI = {
    trait RestaurantAPIStub extends Controller with RestaurantAPI

    val queryBuilder = mock[GenericQueryBuilder[JsObject, Reads, Writes]]

    val collection = mock[JSONCollection]
    collection.find(any[JsObject])(any[Writes[JsObject]]) returns queryBuilder

    val cursor = mock[Cursor[Restaurant]]

    queryBuilder.cursor[Restaurant] returns cursor

    cursor.collect[List]() returns Future {
      response
    }

    new RestaurantAPIStub {
      override def restaurants: JSONCollection = collection
    }
  }

}
