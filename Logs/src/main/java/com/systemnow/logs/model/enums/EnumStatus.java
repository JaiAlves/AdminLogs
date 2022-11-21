package com.systemnow.logs.model.enums;

public enum EnumStatus {
    SUCCESS(1),
    ERROR(2);

    private final Integer id;

    EnumStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public static String getById(Integer id) {
        if (id == null) {
            return "";
        }

        if (id == SUCCESS.id) {
            return SUCCESS.name();
        }
        if (id == ERROR.id) {
            return ERROR.name();
        }

        return "";
    }
}
