package com.cgm.at.knowledgebase.common;

import com.cgm.at.knowledgebase.api.IKnowledgeService;
import com.cgm.at.knowledgebase.api.IKnowledgeValidationService;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * An abstract class encapsulating the main functionalities of the {@link IKnowledgeService} interface.
 * uses {@link IKnowledgeValidationService} to validate input.
 * @param <Q> generic type of the Question stored in the KnowledgeBase.
 * @param <A> generic type of the Answer stored in the KnowledgeBase.
 * @see IKnowledgeService
 * @see IKnowledgeValidationService
 *
 * @author Usama Morad
 * @version 1.0
 */
public abstract class KnowledgeBase<Q, A> implements IKnowledgeService<Q, A> {

    // a map containing all knowledge of the knowledge base.
    private Map<Q, List<A>> knowledge;

    // a validation service to validate inputs.
    private IKnowledgeValidationService<A, Q> validationService;

    // the default value if a question is not in the knowledge base.
    private A defaultValue;

    /**
     * initializes the knowledge base with the parameters given.
     *
     * @param knowledge A Map consisting of a question of generic type Q as the key, and a List of answers of generic type A.
     * @param validationService a validation service to validate inputs.
     * @param defaultValue a default answer of generic type A used to be the answer for the unknown.
     */
    public KnowledgeBase(Map<Q, List<A>> knowledge, IKnowledgeValidationService<A, Q> validationService, A defaultValue) {
        this.knowledge = knowledge;
        this.validationService = validationService;
        this.defaultValue = defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<A> askQuestion(Q question) throws IllegalArgumentException {
        this.validationService.validateQuestion(question);
        return knowledge.getOrDefault(question, Collections.singletonList(defaultValue));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addKnowledge(Q question, List<A> answers) throws IllegalArgumentException {
        this.validationService.validateQuestion(question);
        this.validationService.validateAnswers(answers);
        knowledge.put(question, answers);
    }
}
