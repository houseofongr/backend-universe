package com.hoo.admin.domain.exception;

public class RoomNameNotFoundException extends RuntimeException {
    public RoomNameNotFoundException(String houseTitle, String name) {
        super("house '" + houseTitle + "' doesn't have room named '" + name + "'.");
    }
}
