package com.cgm.at.knowledgebase.api;

import com.cgm.at.knowledgebase.exceptions.KnowledgeBaseException;
import java.util.List;

/**
 * IKnowledgeService defines which functionality a knowledge base should have.
 * Ae knowledge base is a Collection of questions and related answers, where a question can have unlimited answers, but at least one.
 * It allows for two operations either to ask a question or to add knowledge to the Knowledge base.
 * This knowledge base is implemented using generics to allow future implementations using more complex types of question and answer.
 *
 * @author Usama Morad
 * @version 1.0
 * @param <Q> Generic type of Question
 * @param <A> Generic type of Answer
 * @param <S> Generic type of serialized Knowledge.
 */
public interface IKnowledgeService<Q, A, S> {
    /**
     * Returns a list of all Answers of the asked question.
     * If the question asked is not in the KnowledgeBase,
     * then a predefined default answer will be returned as the only item in the list.
     * @param question the question to ask of the generic type Q.
     *                 needs to be validated using {@link IKnowledgeValidationService}.
     * @return a List of answers of type A to the question asked,
     *         otherwise a default answer of type A will be returned as the only item in the list.
     * @see IKnowledgeValidationService
     * @throws IllegalArgumentException thrown if the IKnowledgeValidationService invalidates the question given.
     * @throws KnowledgeBaseException thrown if the question cannot be asked due to collection implementation errors.
     */
    List<A> askQuestion(Q question) throws IllegalArgumentException, KnowledgeBaseException;

    /**
     * Adds the given question of type Q and adds the given answers to it of type A to the knowledge base.
     * question and answers needs to be validated using {@link IKnowledgeValidationService}.
     * @param question of type Q, example: using type String: "what is james favorite color?"
     * @param answers a List of Type A, the list must not be empty!
     * @see IKnowledgeValidationService
     * @return true if the knowledge has been added to the knowledge base successfully, otherwise false.
     * @throws IllegalArgumentException thrown if the {@link IKnowledgeValidationService} invalidates the {@param question} or {@param answers} given.
     * @throws KnowledgeBaseException thrown if the question cannot be asked due to collection implementation errors.
     */
    boolean addKnowledge(Q question, List<A> answers) throws IllegalArgumentException, KnowledgeBaseException;

    /**
     * Adds the given serialized knowledge to the knowledge base by first extracting the question and answers from it,
     * and then adding it to the knowledge base. The {@link IKnowledgeValidationService} need to be used to validate input.
     * @param serializedKnowledge Contains both the question, and the related answers inside of it as one serialized information.
     * @throws IllegalArgumentException thrown if the serialized knowledge does not comply to the rules defined in {@link IKnowledgeValidationService}.
     * @see IKnowledgeValidationService
     * @return true if the knowledge has been added to the knowledge base successfully, otherwise false.
     * @throws IllegalArgumentException thrown if the {@link IKnowledgeValidationService} invalidates the {@param serializedKnowledge} given.
     * @throws KnowledgeBaseException thrown if the question cannot be asked due to collection or deserialization implementation errors.
     */
    boolean addKnowledge(S serializedKnowledge) throws IllegalArgumentException, KnowledgeBaseException;
}
