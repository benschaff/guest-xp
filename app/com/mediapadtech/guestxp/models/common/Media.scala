package com.mediapadtech.guestxp.models.common

import reactivemongo.bson.BSONObjectID
import scala.util.{Failure, Success}

/**
 * This file is part of guest-xp.
 *
 * guest-xp is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * "Copyright 2014 Benjamin Schaff"
 */
case class Media(id: Option[BSONObjectID], title: String, description: String, mimeType: String, extension: Option[String])

case class MediaView(id: Option[String], title: String, description: String, mimeType: String, extension: Option[String])

object MediaImplicitConversions {

  implicit def mediaView2Media(mediaView: MediaView): Media = Media(
    mediaView.id.flatMap {
      stringId => BSONObjectID.parse(stringId) match {
        case Success(objectId) => Some(objectId)
        case Failure(_) => None
      }
    },
    mediaView.title,
    mediaView.description,
    mediaView.mimeType,
    mediaView.extension
  )

  implicit def media2MediaView(media: Media): MediaView = MediaView(
    media.id.map(_.stringify), media.title, media.description, media.mimeType, media.extension
  )

}