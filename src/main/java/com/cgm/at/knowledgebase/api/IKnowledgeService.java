package com.cgm.at.knowledgebase.api;

import java.util.List;

/**
 * IKnowledgeService defines which functionality a KnowledgeBase should have.
 * Either to ask a question or to add knowledge to the Knowledge base.
 * This KnowledgeBase is implemented using generics to allow future implementations using more complex types of question and answer.
 *
 * @author Usama Morad
 * @version 1.0
 * @param <Q> Generic type of Question
 * @param <A> Generic type of Answer
 * @see IKnowledgeValidationService
 */
public interface IKnowledgeService<Q, A> {
    /**
     * Returns a list of all Answers of the asked question.
     * If the question asked is not in the KnowledgeBase,
     * then a predefined default answer will be returned as the only item in the list.
     * @param question the question to ask of the generic type Q.
     *                 needs to be validated using {@link IKnowledgeValidationService}.
     * @return a List of answers of type A to the question asked,
     *         otherwise a default answer of type A will be returned as the only item in the list.
     */
    List<A> askQuestion(Q question) throws IllegalArgumentException;

    /**
     * Adds the given question of type Q and adds the given answers to it of type A to the knowledge base.
     * question and answers needs to be validated using {@link IKnowledgeValidationService}.
     * @param question of type Q, example: using type String: "what is james favorite color?"
     * @param answers a List of Type A, the list must not be empty!
     */
    void addKnowledge(Q question, List<A> answers) throws IllegalArgumentException;
}
