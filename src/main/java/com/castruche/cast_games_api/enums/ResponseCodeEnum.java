package com.castruche.cast_games_api.enums;

public enum ResponseCodeEnum {

    FORBIDDEN(403);
    /*OK(200),
    CREATED(201),
    ACCEPTED(202),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    CONFLICT(409),
    INTERNAL_SERVER_ERROR(500);*/

    private final Integer code;

    ResponseCodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
