package com.mediapadtech.guestxp.controllers;

/**
 * This file is part of guest-xp.
 * <p/>
 * guest-xp is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * guest-xp is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with guest-xp.  If not, see <http://www.gnu.org/licenses/>.
 * <p/>
 * "Copyright 2014 Benjamin Schaff"
 */
public enum Error {

    // Persistence errors
    RESTAURANT_CREATION_FAILED(2000, "persistence.error.message.restaurant.creation.failed"),

    // Validation errors
    RESTAURANT_INVALID(3000, "validation.error.message.restaurant.invalid");

    private final int code;

    private final String messageKey;

    private Error(final int code, final String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    public int getCode() {
        return code;
    }

    public String getMessageKey() {
        return messageKey;
    }

}
