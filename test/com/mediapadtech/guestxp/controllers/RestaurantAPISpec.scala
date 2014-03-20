package com.mediapadtech.guestxp.controllers

import com.mediapadtech.guestxp.models.common.Address
import com.mediapadtech.guestxp.models.restaurant.JsFormat.restaurantFormat
import com.mediapadtech.guestxp.models.restaurant.Restaurant
import org.specs2.mock.Mockito
import org.specs2.mutable._
import play.api.libs.json._
import play.api.mvc.{Request, Controller}
import play.api.test.Helpers._
import play.api.test._
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.Cursor
import reactivemongo.api.collections.GenericQueryBuilder
import reactivemongo.core.commands.{GetLastError, LastError}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import PersistenceError._
import ValidationError._

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
object RestaurantAPISpec extends SpecificationWithJUnit with Mockito {

  private val Owner: String = "owner"

  private val Restaurant1: Restaurant = Restaurant(
    ownerId = Owner,
    name = "Restaurant1",
    address = Address(1, "street", "postalCode", "city", "state", "country")
  )

  private val Restaurant2: Restaurant = Restaurant(
    ownerId = Owner,
    name = "Restaurant2",
    address = Address(1, "street", "postalCode", "city", "state", "country")
  )

  "Restaurant api" should {

    "return an empty list" in {
      val restaurantAPI = initializeRestaurantAPIList(List.empty[Restaurant])

      val result = restaurantAPI.list(Owner)(FakeRequest())

      status(result) must equalTo(OK)
      contentType(result) must beSome("application/json")
      charset(result) must beSome("utf-8")

      contentAsJson(result).as[JsArray].value must beEmpty
    }

    s"return List($Restaurant1)" in {
      val restaurantAPI = initializeRestaurantAPIList(List(Restaurant1))

      val result = restaurantAPI.list(Owner)(FakeRequest())

      status(result) must equalTo(OK)
      contentType(result) must beSome("application/json")
      charset(result) must beSome("utf-8")

      val restaurants = contentAsJson(result).as[List[Restaurant]]
      restaurants must not be empty
      restaurants.size must equalTo(1)
      restaurants.head must equalTo(Restaurant1)
    }

    s"return List($Restaurant1, $Restaurant2)" in {
      val restaurantAPI = initializeRestaurantAPIList(List(Restaurant1, Restaurant2))

      val result = restaurantAPI.list(Owner)(FakeRequest())

      status(result) must equalTo(OK)
      contentType(result) must beSome("application/json")
      charset(result) must beSome("utf-8")

      val restaurants = contentAsJson(result).as[List[Restaurant]]
      restaurants must not be empty
      restaurants.size must equalTo(2)
      restaurants.head must equalTo(Restaurant1)
      restaurants.tail.head must equalTo(Restaurant2)
    }

    "fail to create restaurant with validation error" in {
      val restaurantAPI = initializeRestaurantAPICreateFail()

      val request: Request[JsObject] = FakeRequest().withBody(Json.obj("owner" -> Owner))
      val result = restaurantAPI.create(Owner)(request)

      status(result) must equalTo(BAD_REQUEST)
      contentType(result) must beSome("application/json")
      charset(result) must beSome("utf-8")

      val message = contentAsJson(result)
      (message \ "status").as[String] must equalTo("failure")
      (message \ "code").as[Int] must equalTo(RESTAURANT_INVALID.code)
      (message \ "message").as[String] must equalTo(RESTAURANT_INVALID.messageKey)
      (message \ "reason").asOpt[JsObject].isDefined must beTrue
    }

    "fail to create restaurant with persistence error" in {
      val restaurantAPI = initializeRestaurantAPICreateFail()

      val request: Request[JsValue] = FakeRequest().withBody(Json.toJson(Restaurant1))
      val result = restaurantAPI.create(Owner)(request)

      status(result) must equalTo(INTERNAL_SERVER_ERROR)
      contentType(result) must beSome("application/json")
      charset(result) must beSome("utf-8")

      val message = contentAsJson(result)
      (message \ "status").as[String] must equalTo("failure")
      (message \ "code").as[Int] must equalTo(RESTAURANT_CREATION_FAILED.code)
      (message \ "message").as[String] must equalTo(RESTAURANT_CREATION_FAILED.messageKey)
      (message \ "reason").asOpt[JsObject].isDefined must beFalse
    }

    "success to create restaurant" in {
      val restaurantAPI = initializeRestaurantAPICreateSuccess()

      val request: Request[JsValue] = FakeRequest().withBody(Json.toJson(Restaurant1))
      val result = restaurantAPI.create(Owner)(request)

      status(result) must equalTo(CREATED)
      contentType(result) must beSome("application/json")
      charset(result) must beSome("utf-8")

      val message = contentAsJson(result)
      (message \ "status").as[String] must equalTo("success")
      (message \ "content").as[Restaurant] must equalTo(Restaurant1)
    }

  }

  trait RestaurantAPIStub extends Controller with RestaurantAPI

  private def initializeRestaurantAPIList(response: List[Restaurant]): RestaurantAPI = {
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

  private def initializeRestaurantAPICreateFail(): RestaurantAPI = {
    val collection = mock[JSONCollection]
    collection.insert[Restaurant](any[Restaurant], any[GetLastError])(any[Writes[Restaurant]], any[ExecutionContext]) returns Future {
      LastError(
        ok = false,
        err = Some("error"),
        code = Some(0),
        errMsg = Some("error message"),
        originalDocument = None,
        updated = 0,
        updatedExisting = false
      )
    }

    new RestaurantAPIStub {
      override def restaurants: JSONCollection = collection
    }
  }

  private def initializeRestaurantAPICreateSuccess(): RestaurantAPI = {
    val collection = mock[JSONCollection]
    collection.insert[Restaurant](any[Restaurant], any[GetLastError])(any[Writes[Restaurant]], any[ExecutionContext]) returns Future {
      LastError(
        ok = true,
        err = None,
        code = None,
        errMsg = None,
        originalDocument = None,
        updated = 0,
        updatedExisting = false
      )
    }

    new RestaurantAPIStub {
      override def restaurants: JSONCollection = collection
    }
  }

}
