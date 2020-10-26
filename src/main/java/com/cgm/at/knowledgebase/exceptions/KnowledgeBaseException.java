package com.cgm.at.knowledgebase.exceptions;

/**
 * Exception implementation to add context information to the exception delegation.
 * @author Usama Morad
 * @version 1.0
 */
public class KnowledgeBaseException extends Exception {

    /**
     * This constructor allows to initialize a message
     * @param message A String representing the exception error message
     */
    public KnowledgeBaseException(String message) {
        super(message);
    }

    /**
     * This constructor allows to initialize a message and a throwable cause
     * @param message A String representing the exception error message.
     * @param cause A Throwable object representing the cause.
     */
    public KnowledgeBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * This constructor allows to initialize a throwable cause
     * @param cause A Throwable object representing the cause.
     */
    public KnowledgeBaseException(Throwable cause) {
        super(cause);
    }

    /**
     * This constructor allows to initialize a message and a throwable cause,
     * further more the enableSuppression and writableStackTrace.
     * @param message A String representing the exception error message.
     * @param cause A Throwable object representing the cause.
     * @param enableSuppression A boolean enabling suppression or disabling it.
     * @param writableStackTrace A boolean enabling writableStackTrace or disabling it.
     */
    public KnowledgeBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
