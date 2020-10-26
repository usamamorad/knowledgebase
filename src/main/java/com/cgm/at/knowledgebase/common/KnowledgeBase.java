package com.cgm.at.knowledgebase.common;

import com.cgm.at.knowledgebase.api.IKnowledgeService;
import com.cgm.at.knowledgebase.api.IKnowledgeValidationService;
import com.cgm.at.knowledgebase.exceptions.KnowledgeBaseException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * An abstract class encapsulating the main functionalities of the {@link IKnowledgeService} interface.
 * uses {@link IKnowledgeValidationService} to validate input.
 * @param <Q> generic type of the Question stored in the KnowledgeBase.
 * @param <A> generic type of the Answer stored in the KnowledgeBase.
 * @param <S> generic type of the serialized knowledge input.
 * @see IKnowledgeService
 * @see IKnowledgeValidationService
 *
 * @author Usama Morad
 * @version 1.0
 */
public abstract class KnowledgeBase<Q, A, S> implements IKnowledgeService<Q, A, S> {

    // a map containing all knowledge of the knowledge base.
    private Map<Q, List<A>> knowledge;

    // a validation service to validate inputs.
    private IKnowledgeValidationService<Q, A, S> validationService;

    // the default value if a question is not in the knowledge base.
    private A defaultValue;

    /**
     * initializes the knowledge base with the parameters given.
     *
     * @param knowledge A Map consisting of a question of generic type Q as the key, and a List of answers of generic type A.
     * @param validationService a validation service to validate inputs.
     * @param defaultValue a default answer of generic type A used to be the answer for the unknown.
     */
    public KnowledgeBase(Map<Q, List<A>> knowledge, IKnowledgeValidationService<Q, A, S> validationService, A defaultValue) {
        this.knowledge = knowledge;
        this.validationService = validationService;
        this.defaultValue = defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<A> askQuestion(Q question) throws IllegalArgumentException, KnowledgeBaseException {
        this.validationService.validateQuestion(question);
        try {
            return knowledge.getOrDefault(question, Collections.singletonList(defaultValue));
        } catch (NullPointerException | ClassCastException e) {
            throw new KnowledgeBaseException("Error while asking a Question to the knowledge base: " + e.getMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addKnowledge(Q question, List<A> answers) throws IllegalArgumentException, KnowledgeBaseException {
        this.validationService.validateQuestion(question);
        this.validationService.validateAnswers(answers);
        try {
            this.knowledge.put(question, answers);
        } catch (NullPointerException | ClassCastException e) {
            throw new KnowledgeBaseException("Error while adding a Question to the knowledge base: " + e.getMessage(), e);
        }
        return true;
    }

    /**
     * Gets the knowledge map
     * @return a map contains all knowledge of the knowledge base.
     */
    public Map<Q, List<A>> getAllKnowledge(){
        return this.knowledge;
    }

    /**
     * Gets the validation service
     * @return A {@link IKnowledgeValidationService}
     * @see IKnowledgeValidationService
     */
    public IKnowledgeValidationService getValidationService(){
        return this.validationService;
    }
}
