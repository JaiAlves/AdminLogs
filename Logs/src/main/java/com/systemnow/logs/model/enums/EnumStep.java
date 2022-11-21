package com.systemnow.logs.model.enums;

public enum EnumStep {
    GET_FILES(1),
    READ_FILES(2);

    private final Integer id;

    EnumStep(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public static String getById(Integer id) {
        if (id == null) {
            return "";
        }

        if (id == GET_FILES.id) {
            return GET_FILES.name();
        }
        if (id == READ_FILES.id) {
            return READ_FILES.name();
        }

        return "";
    }
}
