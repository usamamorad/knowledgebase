package com.cgm.at.knowledgebase.services;

import com.cgm.at.knowledgebase.common.KnowledgeBase;
import com.cgm.at.knowledgebase.exceptions.KnowledgeBaseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * A Textual implementation of the knowledge base,
 * questions and answers are both of type String.
 *
 * @author Usama Morad
 * @version 1.0
 */
public class TextualKnowledgeService extends KnowledgeBase<String, String, String> {

    /**
     * initializes the textual knowledge base with an empty HashMap, a new validationService, and a default answer for unknown questions.
     */
    public TextualKnowledgeService() {
        super(new HashMap<>(), new TextualKnowledgeValidationService(), "the answer to life, universe and everything is 42");
    }

    /**
     * initializes the textual knowledge base with the given HashMap, a new validationService, and the given default answer for unknown questions.
     * @param knowledge A Map containing predefined knowledge (Questions and Answers accordingly).
     * @param defaultValue A String containing the default answer.
     */
    public TextualKnowledgeService(Map<String, List<String>> knowledge, String defaultValue) {
        super(knowledge, new TextualKnowledgeValidationService(), defaultValue);
    }

    /**
     * Extracts the question, and the answers to that question from the given question line.
     * then adds the extracted information as new knowledge to the knowledge base.
     * makes sure, that the input is validated properly.
     * @param questionLine the question provided as String of the following format:
     * <question>? "<answer1>" "<answer2>" "<answerX>"
     */
    @Override
    public boolean addKnowledge(String questionLine) throws IllegalArgumentException, KnowledgeBaseException {
        try {
            getValidationService().validateSerializedKnowledgeFormat(questionLine);
            //split question and answer part
            String[] parts = questionLine.split("\\?", 2);
            String question = parts[0] + "?";
            String answersPart = parts[1];
            //get answers separated into different strings and add to list
            List<String> answers = new ArrayList<>();
            Pattern p = Pattern.compile("(\")([\\S\\s]+?)(\")");
            Matcher m = p.matcher(answersPart);
            //find answers using the pattern defined and add to knowledge base
            while (m.find()) {
                String answer = m.group(2);
                answers.add(answer);
            }
            return super.addKnowledge(question, answers);
        } catch(PatternSyntaxException e) {
            //encapsulate exception as root cause, and delegate to be handled by the caller.
            throw new KnowledgeBaseException("Question and answers cannot be extracted due to pattern syntax errors, please contact your administrator!" + e.getMessage(), e);
        } catch (IllegalStateException e) {
            throw new KnowledgeBaseException("Question and answers cannot be extracted due to pattern match operation errors, please contact your administrator!" + e.getMessage(), e);
        } catch (IndexOutOfBoundsException e) {
            throw new KnowledgeBaseException("Question and answers cannot be extracted due to indexing errors, please contact your administrator!" + e.getMessage(), e);
        }
    }
}
