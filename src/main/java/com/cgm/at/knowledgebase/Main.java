package com.cgm.at.knowledgebase;

import com.cgm.at.knowledgebase.api.IKnowledgeService;
import com.cgm.at.knowledgebase.exceptions.KnowledgeBaseException;
import com.cgm.at.knowledgebase.services.TextualKnowledgeService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Main class, the entry point of the Application.
 * The Application provides two options, first is to add a question to the knowledge base and second to ask a question of it.
 * If the question isn't present in the knowledge base, then the answer will be "the answer to life, universe and everything is 42".
 * @author Usama Morad
 * @version 1.0
 */
public class Main {

    /**
     * the main method, the start entry of the Application.
     * @param args arguments to pass when Application is started.
     */
    public static void main(String[] args) {
        //The textual knowledge base service implementation instance to add and ask questions.
        IKnowledgeService<String, String, String> textualKnowledgeService = new TextualKnowledgeService();
        try (Scanner sc = new Scanner(System.in)) {
            String line;
            //helper variable to exit program, the user need to type exit in order to switch to true.
            boolean exit = false;
            do {
                System.out.println("--------------------------");
                System.out.println("------- MAIN MENU --------");
                System.out.println("Please select one of the following options: ");
                System.out.println("  [1] ask Question");
                System.out.println("  [2] add Question");
                System.out.println("  [exit] to exit program");
                if (sc.hasNextLine()) {
                    line = sc.nextLine();
                    switch (line) {
                        case "1":
                            List<String> answers;
                            do {
                                System.out.println("Please ask a question or enter [back] to return to main menu.");
                                if (sc.hasNextLine()) {
                                    line = sc.nextLine();
                                    if (line.equals("back")) break;
                                    try {
                                        answers = textualKnowledgeService.askQuestion(line);
                                        printAnswers(answers);
                                    } catch (IllegalArgumentException e) {
                                        System.out.println("Arguments Error: " + e.getMessage());
                                    }
                                }
                            } while (true); //as long as the user does not enter [back] to return.
                            break;
                        case "2":
                            boolean questionAdded = false;
                            do {
                                System.out.println("Please enter a Question in the following format:");
                                System.out.println("<question>? \"<answer1>\" \"<answer2>\" \"<answerX>\"");
                                System.out.println("Or enter 'exit' to exit to main menu.");
                                if (sc.hasNextLine()) {
                                    line = sc.nextLine();
                                    if (line.equals("exit")) break;
                                    try {
                                        questionAdded = textualKnowledgeService.addKnowledge(line);
                                        System.out.println("Question has been added successfully!");
                                    } catch (IllegalArgumentException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            } while (!questionAdded);
                            break;
                        case "exit":
                            exit = true;
                            break;
                        default:
                            System.out.println("option entered is invalid!");
                    }
                }
            } while (!exit);
        } catch (IllegalStateException e) {
            System.out.println("Scanner closed unexpectedly, Program must exit.");
        } catch (KnowledgeBaseException e) {
            System.out.println("The knowledge base reported following error: " + e.getMessage() + ", Program must exit.");
        } catch (NoSuchElementException e) {
            System.out.println("Input Reading Error: " + e.getMessage() + ", Program must exit.");
        } finally {
            System.out.println("Program exists, see you in 7,5 Million years!");
        }
    }

    /**
     * prints all answers given, one answer per line.
     * @param answers A List of answers to print to the System.out stream.
     */
    private static void printAnswers(List<String> answers){
        if(answers == null) return;
        for (String answer : answers) {
            System.out.println(answer);
        }
    }
}
