package controller;

public enum Moves {
    RUN("run"),
    MOVE("move"),
    GRAB("grab");

    private String name;
    Moves(String name){
        this.name = name;
    };
}