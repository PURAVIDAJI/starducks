package org.kosta.starducks.fina.entity;

import lombok.Getter;

@Getter
public enum StoreOperationalYn {
    storeOperationalY("Open"), storeOperationalN("Closed");

    private final String description;

    StoreOperationalYn(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
