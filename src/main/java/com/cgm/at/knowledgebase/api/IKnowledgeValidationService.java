package com.cgm.at.knowledgebase.api;

import java.util.List;

/**
 * IKnowledgeValidationService is responsible for validating the knowledge before it gets saved in the knowledge base.
 * this interface makes use of generics, so if the question is an audio wave, the implementer need to make sure that the sound wave captured is valid.
 * another simpler example is when the question is in written language as text, then the implementer must take considerations accordingly.
 *
 * @author Usama Morad
 * @version 1.0
 * @param <Q> Generic type of Question
 * @param <A> Generic type of Answer
 */
public interface IKnowledgeValidationService<A, Q> {
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
     * @throws IllegalArgumentException thrown if the following criteria does not meet:
     *         1) non emptiness, information must be given.
     *         2) length of answer does not exceed the predefined maximum length
     */
    void validateAnswers(List<A> answers) throws IllegalArgumentException;
}
