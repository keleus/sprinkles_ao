package cn.myzju.lib.sprinkles.exceptions;

/**
 * Created by emilsjolander on 05/02/14.
 */
public class ContentValuesEmptyException extends RuntimeException {

    public ContentValuesEmptyException() {
        super("Cannot insert an empty row into the database.");
    }

}