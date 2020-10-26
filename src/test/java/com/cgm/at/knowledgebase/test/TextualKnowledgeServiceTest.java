package com.cgm.at.knowledgebase.test;

import com.cgm.at.knowledgebase.common.ErrorMessages;
import com.cgm.at.knowledgebase.common.KnowledgeBase;
import com.cgm.at.knowledgebase.exceptions.KnowledgeBaseException;
import com.cgm.at.knowledgebase.services.TextualKnowledgeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * tests the functionalities of the Textual Knowledge Base implementation.
 * @author Usama Morad
 * @version 1.0
 */
public class TextualKnowledgeServiceTest {

    //KnowledgeBase where answer, question and serializedKnowledge is of type String.
    private KnowledgeBase<String, String, String> textualKnowledgeService;

    @BeforeEach
    public void setUp() {
        Map<String, List<String>> predefinedKnowledge = new HashMap<>();
        predefinedKnowledge.put("Can a mosquito transmit more than one disease?", Arrays.asList(
            "The mosquito species Aedes aegypti and Aedes albopictus carry dengue, chikungunya and Zika virus.",
            "In addition\nAedes aegypti also transmits yellow fever."
        ));
        predefinedKnowledge.put("What is Peters favorite food?", Arrays.asList(
            "Pizza",
            "Spaghetti",
            "Ice cream"
        ));
        predefinedKnowledge.put("What is Prince William's full name and What's the name of the Coco Pops mascot and What year did Vincent Van Gogh die and Switzerland is made up of how many cantons and Which political figure became Baronness of Kesteven and Who designed the Eiffel Tower?",
                Collections.singletonList("these are more than one question, I don't know so much!"));
        this.textualKnowledgeService = new TextualKnowledgeService(predefinedKnowledge, "the answer to life, universe and everything is 42");
    }

    @Test
    public void addKnowledge_ShouldSucceed_QuestionAddedToKnowledgeBase_Test() throws KnowledgeBaseException {
        this.textualKnowledgeService.addKnowledge("What are Peters favorite colors? \"red\" \"blue\" \"green\"");
        Map<String, List<String>> knowledge = textualKnowledgeService.getAllKnowledge();
        assertEquals(Arrays.asList("red", "blue", "green"), knowledge.get("What are Peters favorite colors?"));
    }

    @Test
    public void addKnowledge_ShouldSucceed_QuestionWithCarriageReturn_Test() throws KnowledgeBaseException {
        this.textualKnowledgeService.addKnowledge("What are Peters favorite colors? \"red\ngreen\" \"blue\" \"green\"");
        Map<String, List<String>> knowledge = textualKnowledgeService.getAllKnowledge();
        assertEquals(Arrays.asList("red\ngreen", "blue", "green"), knowledge.get("What are Peters favorite colors?"));
    }

    @Test
    public void addKnowledge_ShouldSucceed_QuestionWithOnlyOneAnswer_Test() throws KnowledgeBaseException {
        this.textualKnowledgeService.addKnowledge("How old is Peter? \"35\"");
        Map<String, List<String>> knowledge = this.textualKnowledgeService.getAllKnowledge();
        assertEquals(Collections.singletonList("35"), knowledge.get("How old is Peter?"));
    }

    @Test
    public void addKnowledge_ShouldSucceed_AnswerExactly255Chars_Test() throws KnowledgeBaseException {
        String answer = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimat.";
        assertEquals(255, answer.length());
        this.textualKnowledgeService.addKnowledge("Can you give me a 255 characters lorem ipsum? \"" + answer + "\"");
        Map<String, List<String>> knowledge = this.textualKnowledgeService.getAllKnowledge();
        assertEquals(Collections.singletonList(answer), knowledge.get("Can you give me a 255 characters lorem ipsum?"));
    }

    @Test
    public void addKnowledge_ShouldSucceed_AnswerIs254Chars_Test() throws KnowledgeBaseException {
        String answer = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takima.";
        assertEquals(254, answer.length());
        this.textualKnowledgeService.addKnowledge("Can you give me a 254 characters lorem ipsum? \"" + answer + "\"");
        Map<String, List<String>> knowledge = this.textualKnowledgeService.getAllKnowledge();
        assertEquals(Collections.singletonList(answer), knowledge.get("Can you give me a 254 characters lorem ipsum?"));
    }

    @Test
    public void addKnowledge_ShouldSucceed_ThreeAnswersWith255Chars_Test() throws KnowledgeBaseException {
        String firstLorem = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimat.";
        assertEquals(255, firstLorem.length());
        String secondLorem = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. Stet clita kasd gubergren, no sea takima. At vero eos et accusam et justo duo dolores et ea rebuma.";
        assertEquals(255, secondLorem.length());
        String thirdLorem = "Lorem ipsum dolor sit amet, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takima. consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erata.";
        assertEquals(255, thirdLorem.length());
        this.textualKnowledgeService.addKnowledge("Can you give me three 255 characters long lorem ipsum? \"" + firstLorem + "\" \"" + secondLorem + "\" \"" + thirdLorem+ "\"");
        Map<String, List<String>> knowledge = this.textualKnowledgeService.getAllKnowledge();
        assertEquals(Arrays.asList(firstLorem, secondLorem, thirdLorem), knowledge.get("Can you give me three 255 characters long lorem ipsum?"));
    }

    @Test
    public void addKnowledge_ShouldSucceed_QuestionExactly255Chars_Test() throws KnowledgeBaseException {
        String question = "What is Prince William's full name and What's the name of the Coco Pops mascot and What year did Vincent Van Gogh die and Switzerland is made up of how many cantons and Which political figure became Baronness of Kesteven and Who designed the Eiffel Tower?";
        String answer = "these are more than one question, I don't know so much!";
        assertEquals(255, question.length());
        this.textualKnowledgeService.addKnowledge(question + " \"" + answer + "\"");
        Map<String, List<String>> knowledge = this.textualKnowledgeService.getAllKnowledge();
        assertEquals(Collections.singletonList(answer), knowledge.get(question));
    }

    @Test
    public void addKnowledge_ShouldThrowValidationError_MissingQuestionMark_Test() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> this.textualKnowledgeService.addKnowledge("What are Peters favorite colors \"red\" \"blue\" \"green\""));
        assertEquals(ErrorMessages.QUESTION_FORMAT_INCORRECT, exception.getMessage());
    }

    @Test
    public void addKnowledge_ShouldThrowValidationError_MissingClosingQuotationOnLastAnswer_Test() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> this.textualKnowledgeService.addKnowledge("What are Peters favorite colors? \"red\" \"blue\" \"green"));
        assertEquals(ErrorMessages.ANSWERS_FORMAT_INCORRECT, exception.getMessage());
    }

    @Test
    public void addKnowledge_ShouldThrowValidationError_MissingOpeningQuotationOnFirstAnswer_Test() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> this.textualKnowledgeService.addKnowledge("What are Peters favorite colors? blue\" \"red\" \"white\""));
        assertEquals(ErrorMessages.ANSWERS_FORMAT_INCORRECT, exception.getMessage());
    }

    @Test
    public void addKnowledge_ShouldThrowValidationError_MissingClosingQuotationOnFirstAnswer_Test() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> this.textualKnowledgeService.addKnowledge("What are Peters favorite colors? \"blue\"white\""));
        assertEquals(ErrorMessages.ANSWERS_FORMAT_INCORRECT, exception.getMessage());
    }

    @Test
    public void addKnowledge_ShouldThrowValidationError_QuestionIsEmpty_Test() {
        String question = "?";
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> this.textualKnowledgeService.addKnowledge(question + " \"red\" \"blue\" \"green\""));
        assertEquals(String.format(ErrorMessages.QUESTION_IS_EMPTY_OR_BLANK, question), exception.getMessage());
    }

    @Test
    public void addKnowledge_ShouldThrowValidationError_QuestionIsBlank_Test() {
        String question = "  ?";
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> this.textualKnowledgeService.addKnowledge(question + " \"red\" \"blue\" \"green\""));
        assertEquals(String.format(ErrorMessages.QUESTION_IS_EMPTY_OR_BLANK, question), exception.getMessage());
    }

    @Test
    public void addKnowledge_ShouldThrowValidationError_QuestionWithNoAnswer_Test() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> this.textualKnowledgeService.addKnowledge("How old is Peter?"));
        assertEquals(ErrorMessages.ANSWERS_NONE_GIVEN, exception.getMessage());
    }

    @Test
    public void addKnowledge_ShouldThrowValidationError_AnswerLargerThan255Chars_Test() {
        String answer = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata.";
        assertEquals(256, answer.length());
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> this.textualKnowledgeService.addKnowledge("Can you give me a 256 characters lorem ipsum? \"" + answer + "\""));
        assertEquals(String.format(ErrorMessages.ANSWER_EXCEEDS_255_CHARACTERS, answer), exception.getMessage());
    }

    @Test
    public void addKnowledge_ShouldThrowValidationError_QuestionLargerThan255Chars_Test() {
        String question = "What is Prince William's full name and What's the name of the Coco Pops mascot and What year did Vincent Van Gogh die and Switzerland is made up of how many cantons and Which political figure became Baronness of Kesteven, and Who designed the Eiffel Tower?";
        assertEquals(256, question.length());
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> this.textualKnowledgeService.addKnowledge(question + " \"these are more than one question, I don't know so much!\""));
        assertEquals(String.format(ErrorMessages.QUESTION_EXCEEDS_255_CHARACTERS, question), exception.getMessage());
    }


    @Test
    public void askQuestion_ShouldSucceed_Test() throws KnowledgeBaseException {
        List<String> answers = this.textualKnowledgeService.askQuestion("Can a mosquito transmit more than one disease?");
        assertEquals(Arrays.asList(
                "The mosquito species Aedes aegypti and Aedes albopictus carry dengue, chikungunya and Zika virus.",
                "In addition\nAedes aegypti also transmits yellow fever."
        ), answers);
    }

    @Test
    public void askQuestion_ShouldSucceed_UnknownQuestionReturnsDefaultAnswer_Test() throws KnowledgeBaseException {
        List<String> answers = this.textualKnowledgeService.askQuestion("What is the answer to life?");
        assertEquals(Collections.singletonList("the answer to life, universe and everything is 42"), answers);
    }

    @Test
    public void askQuestion_ShouldSucceed_QuestionExactly255Chars_Test() throws KnowledgeBaseException {
        String question = "What is Prince William's full name and What's the name of the Coco Pops mascot and What year did Vincent Van Gogh die and Switzerland is made up of how many cantons and Which political figure became Baronness of Kesteven and Who designed the Eiffel Tower?";
        assertEquals(255, question.length());
        List<String> answers = this.textualKnowledgeService.askQuestion(question);
        assertEquals(Collections.singletonList("these are more than one question, I don't know so much!"), answers);
    }

    @Test
    public void askQuestion_ShouldThrowValidationError_Question256Chars_Test() {
        String question = "What is Prince William's full name and What's the name of the Coco Pops mascot and What year did Vincent Van Gogh die and Switzerland is made up of how many cantons and Which political figure became Baronness of Kesteven, and Who designed the Eiffel Tower?";
        assertEquals(256, question.length());
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> this.textualKnowledgeService.askQuestion(question));
        assertEquals(String.format(ErrorMessages.QUESTION_EXCEEDS_255_CHARACTERS, question), exception.getMessage());
    }

    @Test
    public void askQuestion_ShouldThrowValidationError_QuestionMarkIsMissing_Test() {
        String question = "What is the answer to life";
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> this.textualKnowledgeService.askQuestion(question));
        assertEquals(String.format(ErrorMessages.QUESTION_NO_QUESTIONMARK, question), exception.getMessage());
    }
}