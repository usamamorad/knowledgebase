package com.cgm.at.knowledgebase.api;

import java.util.List;

/**
 * IKnowledgeValidationService is responsible for validating the knowledge before it gets saved in the knowledge base.
 * this interface makes use of generics, so if the question is an audio wave, the implementer need to make sure, that the sound wave captured is valid.
 * another simpler example is when the question is in written language as text, then the implementer must take considerations accordingly.
 *
 * @author Usama Morad
 * @version 1.0
 * @param <Q> Generic type of Question.
 * @param <A> Generic type of Answer.
 * @param <S> Generic type of serialized knowledge.
 */
public interface IKnowledgeValidationService<Q, A, S> {
    /**
     * validates a question given, a question is a sentence worded or expressed so as to elicit information.
     * the following criteria defines a valid question:
     * 1) non emptiness, information must be given.
     * 2) length of answer does not exceed the predefined maximum length
     *
     * examples for a valid question:
     * > sound wave with content, the following answer "What is peters favorite color?", ...
     *
     * examples for an invalid question:
     * > empty sound wave, empty or an undefined string, string length greater than predefined maximum length, ...
     *
     * @param question question of generic type Q
     * @throws IllegalArgumentException if the question is empty, undefined or exceeds the predefined max length.
     */
    void validateQuestion(Q question) throws IllegalArgumentException;

    /**
     * validates a list of answers given, an answer is a thing that is said, written, or done as a reaction to a question, statement, or situation.
     * the following criteria defines an invalid answer:
     * 1) non emptiness, information must be given.
     * 2) length of answer does not exceed the predefined maximum length
     *
     * examples for valid answers:
     * sound wave where an answer can be heard, the following answer "peters favorite color is red" , ...
     *
     * examples for invalid answers:
     * > empty sound wave, empty string or null string, string length greater than predefined maximum length
     * @param answers an answer is a thing that is said, written, or done as a reaction to a question, statement, or situation.
     * @throws IllegalArgumentException thrown if at least the following criteria does not meet:
     *         1) non emptiness, information must be given.
     *         2) length of answer does not exceed the predefined maximum length
     */
    void validateAnswers(List<A> answers) throws IllegalArgumentException;

    /**
     * validates the serialized knowledge given of generic type S.
     * This method does not validate the question and answers itself, only the format of the serialization input.
     * @param serializedKnowledge Contains both the question, and the related answers inside of it as one serialized information.
     * @throws IllegalArgumentException thrown if the knowledge cannot be extracted from the serialization given due to noncompliance to a format defined in the specific type-implementation of this interface.
     * dependant of the type of Question and Answer, the implementation of the specific validation class, need to define rules, so this serialization is valid.
     */
    void validateSerializedKnowledgeFormat(S serializedKnowledge) throws IllegalArgumentException;
}
