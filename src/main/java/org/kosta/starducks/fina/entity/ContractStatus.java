package org.kosta.starducks.fina.entity;

import lombok.Getter;

@Getter
public enum ContractStatus {
    CONTRACT_ACTIVE("Y"),

    CONTRACT_STOPPED("N");

    private final String displayName;

    ContractStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
