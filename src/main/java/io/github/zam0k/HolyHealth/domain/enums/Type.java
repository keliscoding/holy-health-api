package io.github.zam0k.HolyHealth.domain.enums;

public enum Type {
    TYPE_1(1),
    TYPE_2(2);

    private int typeValue;

    Type(int typeValue) {
        this.typeValue = typeValue;
    }

    public int getTypeValue() {
        return typeValue;
    }
}
