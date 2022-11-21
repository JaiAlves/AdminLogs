package com.systemnow.logs.model.enums;

public enum EnumTypeLog {
    UNDEFINED(0),
    WARN(1),
    INFO(2),
    ERROR(3);

    private final Integer id;

    EnumTypeLog(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public static String getById(Integer id) {
        if (id == null) {
            return "";
        }
        if (id == UNDEFINED.id) {
            return UNDEFINED.name();
        }
        if (id == WARN.id) {
            return WARN.name();
        }
        if (id == INFO.id) {
            return INFO.name();
        }
        if (id == ERROR.id) {
            return ERROR.name();
        }

        return "";
    }

    public static EnumTypeLog getByName(String name) {
        if (UNDEFINED.name().equals(name)) {
            return UNDEFINED;
        }

        if (WARN.name().equals(name)) {
            return WARN;
        }

        if (INFO.name().equals(name)) {
            return INFO;
        }

        if (ERROR.name().equals(name)) {
            return ERROR;
        }

        return null;
    }
}
