/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hellotvxlet;

/**
 *
 * @author student
 */
public class Question {
    
    protected String AskQuestion;
    protected String CorrectAnswer;
    protected String OptionA;
    protected String OptionB;
    protected String OptionC;
    
    public Question(String question){
        AskQuestion = question;
    }
    public void SetCorrectAnswer(String answer){
        CorrectAnswer = answer;
    }
    public void SetOptionA(String a){
        OptionA = a;
    }
    public void SetOptionB(String b){
        OptionA = b;
    }
    public void SetOptionC(String c){
        OptionA = c;
    }
    
    
    public String GetQuestion(){
        return AskQuestion;
    }
    public String GetCorrectAnswer(){
        return CorrectAnswer;
    }
    public String GetOptionA(){
        return OptionA;
    }
    public String GetOptionB(){
        return OptionB;
    }
    public String GetOptionC(){
        return OptionC;
    }
}
