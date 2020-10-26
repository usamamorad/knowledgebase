package com.cgm.at.knowledgebase.services;

import com.cgm.at.knowledgebase.api.IKnowledgeValidationService;
import com.cgm.at.knowledgebase.common.ErrorMessages;
import java.util.List;

/**
 * {@inheritDoc}
 * the following implementation uses strings for question and answer.
 */
public class TextualKnowledgeValidationService implements IKnowledgeValidationService<String, String, String> {

    //the maxLength predefined for validation of answer's length and question length.
    private int maxLength;

    /**
     * Initializes the knowledge validation service with the validity constraint for Strings of max length of 255.
     */
    public TextualKnowledgeValidationService(){
        this.maxLength = 255;
    }

    /**
     * {@inheritDoc}
     * @param question A string representing a question with a question mark at the end.
     * @throws IllegalArgumentException thrown if one of the answers does not comply to the following criteria:
     * 1) an answer's maxLength is exceeded,
     * 2) answer is null,
     * 3) answer is empty or blank.
     * 4) no question mark at the end of a question.
     */
    @Override
    public void validateQuestion(String question) throws IllegalArgumentException {
        if(question == null || question.length() == 0) throw new IllegalArgumentException(ErrorMessages.QUESTION_NONE_GIVEN);
        if(question.length() > this.maxLength) throw new IllegalArgumentException(String.format(ErrorMessages.QUESTION_EXCEEDS_255_CHARACTERS, question));
        if(question.charAt(question.length()-1) != '?') throw new IllegalArgumentException(String.format(ErrorMessages.QUESTION_NO_QUESTIONMARK, question));
        String questionWithoutQuestionMark = question.substring(0, question.length()-1);
        if(questionWithoutQuestionMark.isBlank() || questionWithoutQuestionMark.isEmpty()) throw new IllegalArgumentException(String.format(ErrorMessages.QUESTION_IS_EMPTY_OR_BLANK, question));
    }

    /**
     * {@inheritDoc}
     * @param answers A List of Strings representing answers.
     * @throws IllegalArgumentException thrown if one of the answers does not comply to the following criteria:
     * 1) an answer's maxLength is exceeded,
     * 2) answer is null,
     * 3) answer is empty.
     */
    @Override
    public void validateAnswers(List<String> answers) throws IllegalArgumentException {
        if(answers.isEmpty()) throw new IllegalArgumentException(ErrorMessages.ANSWERS_NONE_GIVEN);
        for (String answer: answers) {
            this.validateAnswer(answer);
        }
    }

    /**
     * validates the format of the well-formed question and answers serialized chunk.
     * This method does not validate the question and answers itself, only the format of the serialization input.
     * the answers part need to be checked for well-formedness, so if a quotation is opened,
     * then there needs to be a closing quotation for each answer. Answers between quotations will be ignored.
     *
     * examples for a non well-formed question and answers chunk:
     * > What are Peters favorite colors? "blue" "red" "white
     * > What are Peters favorite colors? blue" "red" "white"
     * > What are Peters favorite colors? "blue"white"
     * > What are Peters favorite colors "blue" "red" "white"
     *
     * examples for well-formed question and answers chunk:
     * > What are Peters favorite colors? "blue" "red" "white"
     * > What are Peters favorite colors? "red" some text "yellow"
     * > What are Peters favorite colors?    "red" some "yellow" text
     *
     * @param serializedKnowledge A String representing a question and all answers in one chunk of the following format:
     * <question>? "<answer1>" "<answer2>" ... "<answerX>"
     * @throws IllegalArgumentException thrown if the format of {@param serializedKnowledge} is not well formed.
     */
    @Override
    public void validateSerializedKnowledgeFormat(String serializedKnowledge) throws IllegalArgumentException {
        //split question and answer part
        String[] parts = serializedKnowledge.split("\\?", 2);
        if (parts.length < 2) {
            throw new IllegalArgumentException(ErrorMessages.QUESTION_FORMAT_INCORRECT);
        }
        //check format of answer chunk
        String answersChunk = parts[1];
        boolean wellFormed = true;
        for(int i=0; i < answersChunk.length(); i++) {
            if(answersChunk.charAt(i) == '"') {
                wellFormed = !wellFormed;
            }
        }
        if(!wellFormed) throw new IllegalArgumentException(ErrorMessages.ANSWERS_FORMAT_INCORRECT);
    }

    /**
     * {@inheritDoc}
     * an answer is valid if the following rules apply:
     * 1) an answer's maxLength is not exceeded
     * 2) answer is not null
     * 3) answer is not empty
     *
     * @param answer A String representing an answer.
     * @throws IllegalArgumentException if the answer is undefined or null or the larger than maxLength.
     */
    private void validateAnswer(String answer) throws IllegalArgumentException {
        if(answer == null) throw new IllegalArgumentException(ErrorMessages.ANSWER_INVALID);
        if(answer.length() > this.maxLength) throw new IllegalArgumentException(String.format(ErrorMessages.ANSWER_EXCEEDS_255_CHARACTERS, answer));
        if(answer.isEmpty() || answer.isBlank()) throw new IllegalArgumentException(String.format(ErrorMessages.ANSWER_IS_EMPTY_OR_BLANK, answer));
    }
}
