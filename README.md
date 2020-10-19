# knowledgebase

The Knowledgebase is a java CLI Application with the following options:
1) Ask a specific question 
2) add questions and their answers

For adding a question, the following format needs to comply
<question>? "<answer1>" "<answer2>" "<answerX>"

For asking a question, you need to provide the question case sensitive as identical match to get the defined answers back, otherwise the following text will be the answer:
"the answer to life, universe and everything is 42"

Generics were used to give the Knowledgebase more flexibility in the usage of preferred types in future implementations of the knowledgebase. For example, if you need the answers to be a more complex structure than a string, or if the question should be an model object with more informations to it.
