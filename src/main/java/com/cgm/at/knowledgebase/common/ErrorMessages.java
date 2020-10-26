package com.cgm.at.knowledgebase.common;

/**
 * Class used for declaring constants used for error messages.
 *
 * @author Usama Morad
 * @version 1.0
 */
public final class ErrorMessages  {
    public static final String QUESTION_NONE_GIVEN = "No valid question given!";
    public static final String QUESTION_EXCEEDS_255_CHARACTERS = "The question length of {'%s'} exceeds the maximum of 255 characters!";
    public static final String QUESTION_NO_QUESTIONMARK = "the question {'%s'} does not have a question mark at the end of the question!";
    public static final String QUESTION_IS_EMPTY_OR_BLANK = "The question {'%s'} is empty or blank!";
    public static final String QUESTION_FORMAT_INCORRECT = "Question format is incorrect! No question mark defined in the question!";
    public static final String ANSWERS_NONE_GIVEN = "No answers in quotations defined!";
    public static final String ANSWERS_FORMAT_INCORRECT = "The answers format is incorrect!";
    public static final String ANSWER_INVALID = "Invalid answer given!";
    public static final String ANSWER_EXCEEDS_255_CHARACTERS = "The answer length of {'%s'} exceeds the maximum length of 255 characters!";
    public static final String ANSWER_IS_EMPTY_OR_BLANK = "The answer {'%s'} is empty or blank!";

    /**
     * The caller should be prevented from constructing objects of
     * this class, by declaring this private constructor.
     */
    private ErrorMessages(){
        throw new AssertionError();
    }
}
