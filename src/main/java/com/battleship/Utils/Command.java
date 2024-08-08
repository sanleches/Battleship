package com.battleship.Utils;

public enum Command {
    LOGIN,
    LOGIN_SUCCEED,
    NAME_NOT_AVAILABLE,
    GAME_NAME_NOT_AVAILABLE,
    AVAILABLE_GAME,
    CREATE_GAME,
    GAME_HAS_ALREADY_2_PLAYERS,
    JOIN_TO_GAME,
    DELETE_GAME,
    ABANDON_GAME,
    REMOVE_GAME,
    GAME_DELETED,
    GAME_ABANDON_AND_DELETED,
    OPPONENT_GIVE_UP,
    HOST_DELETED_THIS_GAME,
    HOST_CHANGE,
    WAIT_FOR_OPPONENT,
    OPPONENT_JOINED,
    INVITATION,
    OFFER_ACCEPT,
    OFFER_REJECT,
    OPPONENT_EXIT,
    OPPONENT_IS_READY,
    ALL_SHIPS_PLACED,
    READY,
    OPPONENT_HIT,
    OPPONENT_HIT_AND_SINK,
    YOUR_TURN,
    NOT_YOUR_TURN,
    YOU_WIN,
    YOU_LOSE,
    GIVE_UP,
    JOINED,
    ABANDON_OK,
    JOIN_TO_GAME_FAILED,
    PLACE_YOUR_SHIPS,
    PLACE_A_SHIP,
    REMOVE_SHIP,
    REMOVE_OK,
    PLACEMENT_SUCCEED,
    PLACEMENT_FAILED,
    OPPONENT_MISSED_YOUR_TURN,
    SHOOT,
    MISSED_NOT_YOUR_TURN,
    HIT_SHOOT_AGAIN,
    HIT_AND_SINK,
    PLAYER_HINT,
    CHAT_MESSAGE,
    CLIENT_CLOSE,
    SERVER_SHUTDOWN
}
