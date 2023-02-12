package com.laudynetwork.mlgrush.game;

public enum PlayerStatus {
    MLG_Playing,
    MLG_Spec,
    Lobby;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
