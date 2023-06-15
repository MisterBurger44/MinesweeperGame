package model;

public enum GameState {
    NOT_STARTED, // no moves have been made yet.
    IN_PROGRESS, // at least one selection has been made and the game has not yet ended.
    WON, // the game has been played to completion and ended in victory. All non-mine cells have been uncovered without uncovering a mine cell.
    LOST; // the game has been played to completion and ended in defeat. A mine cell was uncovered before all non-mine cells were uncovered.
}
