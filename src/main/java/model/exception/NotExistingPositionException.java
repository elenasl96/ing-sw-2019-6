package model.exception;

import model.moves.Target;

public class NotExistingPositionException extends InvalidMoveException{
        public NotExistingPositionException(Target target){super(target.toString() + " position doesn't exists");}
    }

