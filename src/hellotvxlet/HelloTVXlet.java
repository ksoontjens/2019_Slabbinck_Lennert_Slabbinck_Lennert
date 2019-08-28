package hellotvxlet;

import javax.tv.xlet.*;
import java.awt.event.*;
import java.util.Random.*;
import java.awt.*;
import org.dvb.event.*;
import org.havi.ui.*;
import org.havi.ui.event.*;



public class HelloTVXlet implements Xlet, HActionListener, ActionListener, UserEventListener {
    
    
    static HScene myScene;
    
    //Buttons om te antwoorden
    private HTextButton optionA;
    private HTextButton optionB;
    private HTextButton optionC;
    
    //Player Variabelen
    int currentQuestion = 0;
    int currentPoints = 0;
    
    Question[] questions = new Question[10];
    
    //UI elementen
    private HText welcomeText;
    private HText endText;
    private HText result;
    private HText questionText;
    private HText pointsText;
    private int isSelected = 0;
    
    private boolean isRunning = false;
    private boolean hasStarted = false;
    private boolean isDone = false;
    
    public HelloTVXlet() {
        
    }

    // myScene inladen
    public static HScene getScene(){
        return myScene;
    }
    
    public void initXlet(XletContext context) {
        System.out.println("Init Xlet");
        
        // Array met alle vragen
        String[] questionStrings = {
            "1. The more you take, the more you leave behind. What am I?",
            "2. What has a head, a tail, is brown and has no legs?",
            "3. David's father has three sons: Judas, Kain and ___?",
            "4. What room do ghosts avoid?",
            "5. What belongs to you, but other people use it more than you?",
            "6. What is more usefull when broken?",
            "7. What is easy to get into but hard to get out?",
            "8. I get whiter the dirtier I get, what am I?",
            "9. Is this the final question?",
            "10.What is the name of the famous hit from Vanilla Ice?"};
        
        // Array met alle mogelijke antwoorden
        String[][] answersStrings = {
            {"Friends","Footprints","Plants"},
            {"Monkey","Penny","Jessy"},
            {"Goliath","Jesus","David"},
            {"The basement","The living room","The bedroom"},
            {"Your Money","Your Name","Your Car"},
            {"An Egg","A Waterbottle","A Shelf"},
            {"A Pyjama","Trouble","A Homeparty"},
            {"A Blackboard","A bunny","A Snowball"},
            {"Yes","Nah","Maybe"},
            {"Ice Ice Baby","How Much Is The Fish","Pina Colada"},
        };
        
        // Add vragen en antwoorden aan klasse var
        for(int i = 0; i < 10; i++){
            questions[i] = new Question(questionStrings[i]);
            questions[i].SetOptionA(answersStrings[i][2]);
            questions[i].SetOptionB(answersStrings[i][1]);
            questions[i].SetOptionC(answersStrings[i][0]);
            
            // Check of alles goed in ingelezen
            System.out.println("Questionz a " + questions[i].GetOptionA());
            System.out.println("Questionz b " + questions[i].GetOptionB());
            System.out.println("Questionz c " + questions[i].GetOptionC());
        }
        
        // Juiste antwoorden toevoegen
        questions[0].SetCorrectAnswer(answersStrings[0][0]);
        questions[1].SetCorrectAnswer(answersStrings[1][1]);
        questions[2].SetCorrectAnswer(answersStrings[2][2]);
        questions[3].SetCorrectAnswer(answersStrings[3][1]);
        questions[4].SetCorrectAnswer(answersStrings[4][1]);
        questions[5].SetCorrectAnswer(answersStrings[5][0]);
        questions[6].SetCorrectAnswer(answersStrings[6][1]);
        questions[7].SetCorrectAnswer(answersStrings[7][0]);
        questions[8].SetCorrectAnswer(answersStrings[8][1]);
        questions[9].SetCorrectAnswer(answersStrings[9][0]);
        
        //
        myScene = HSceneFactory.getInstance().getDefaultHScene();
        resetQuiz();
        startMyScene();
    }
    
    public void resetQuiz(){
        // Variabelen Resetten
        resetMyScene();
        isSelected = 0;
        currentQuestion = 0;
        currentPoints = 0;
        isRunning = false;
        hasStarted = false;
        isDone = false;
    }
    
    public void startMyScene() {
        // Welkom pagina toevoegen aan MyScene
        welcomeText = new HText("Press Enter To Start The Riddle Quiz");
        welcomeText.setLocation(200, 20);
        welcomeText.setSize(500, 100);
       
        myScene.add(welcomeText);
        welcomeText.setBackground(Color.pink);
        welcomeText.setBackgroundMode(HVisible.BACKGROUND_FILL);
        welcomeText.setBordersEnabled(false);
        myScene.add(welcomeText);
        setSceneVisible();

    }
    
    public void setSceneVisible(){
        myScene.repaint();
        myScene.validate();
        myScene.setVisible(true);
    }
    
    public void resetMyScene(){
        myScene.removeAll();
    }
    
    public void writeToScene() {
        // Vraag en UI aan scene toevoegen
        resetMyScene();
        isRunning = true;
        hasStarted = true;
        myScene = HSceneFactory.getInstance().getDefaultHScene();
        
        questionText = new HText(questions[currentQuestion].GetQuestion());
        pointsText = new HText(currentPoints + " Points");
        questionText.setLocation(30,100);
        pointsText.setLocation(30, 25);
        questionText.setSize(600,60);
        pointsText.setSize(150, 25);
        pointsText.setBackground(Color.pink);
        questionText.setBackground(Color.pink);
        pointsText.setBackgroundMode(HVisible.BACKGROUND_FILL);
        questionText.setBackgroundMode(HVisible.BACKGROUND_FILL);
        pointsText.setBordersEnabled(false);
        questionText.setBordersEnabled(false);
        
        myScene.add(questionText);
        myScene.add(pointsText);
        
        // Get options
        optionA = new HTextButton(questions[currentQuestion].GetOptionA());
        optionB = new HTextButton(questions[currentQuestion].GetOptionB());
        optionC = new HTextButton(questions[currentQuestion].GetOptionC());
        optionA.setLocation(75, 200);
        optionB.setLocation(75, 320);
        optionC.setLocation(75, 440);
        optionA.setSize(150, 100);
        optionB.setSize(150, 100);
        optionC.setSize(150, 100);
        optionA.setBackground(Color.red);
        optionB.setBackground(Color.pink);
        optionC.setBackground(Color.pink);
        optionA.setBackgroundMode(HVisible.BACKGROUND_FILL);
        optionB.setBackgroundMode(HVisible.BACKGROUND_FILL);
        optionC.setBackgroundMode(HVisible.BACKGROUND_FILL);
        optionA.setActionCommand("option A");
        optionB.setActionCommand("option B");
        optionC.setActionCommand("option C");
        optionA.addHActionListener(this);
        optionB.addHActionListener(this);
        optionC.addHActionListener(this);
        myScene.add(optionA);
        myScene.add(optionB);
        myScene.add(optionC);
        
        setSceneVisible();
    }
    
    
    public void actionPerformed(ActionEvent e){
        String useractions = e.getActionCommand();
        if(useractions.equals("OptionA")){
            optionA.setBackground(Color.red);
            optionA.repaint();
            myScene.add(optionA);
        }
        myScene.validate();
        myScene.repaint();
    }
    
    public void startXlet() {
      EventManager eventmanager = EventManager.getInstance();
      UserEventRepository userRepo = new UserEventRepository("Keys");
      userRepo.addAllArrowKeys();
      userRepo.addKey(HRcEvent.VK_ENTER);
      eventmanager.addUserEventListener(this, userRepo);
    }
    
   

    // UserInput: isSelected = 0 = a
    // isSelected( 0 = a, 1 = b, 2 = c)
    public void userEventReceived(UserEvent e){
        if(e.getType() == KeyEvent.KEY_PRESSED){
            switch(e.getCode()){
                case HRcEvent.VK_DOWN:
                    if(isSelected < 3 && isSelected >= 0){
                        isSelected++;}
                    break;
                case HRcEvent.VK_UP:
                    if(isSelected >= 0 && isSelected < 3){
                        isSelected--;
                    }
                    break;
                case HRcEvent.VK_ENTER:
                    if(isRunning){
                        // enter = vraag beantwoorden
                        if(isSelected == 0){System.out.println("User Answer: A");}
                        if(isSelected == 1){System.out.println("User Answer: B");}
                        if(isSelected == 2){System.out.println("User Answer: C");}
                        answerQuestion();
                    }
                    if(!hasStarted){
                        System.out.println("Quiz Has Been Started, Enjoy!");
                        // enter = quiz starten in homescreen
                        isRunning = true;
                        hasStarted = true;
                        writeToScene();
                        //break;
                    }
                    if(isDone){
                        System.out.println("Quiz Has Been Completed, Good Job!");
                        resetQuiz();
                        startMyScene();
                    }
                    break;
            }
            switch(isSelected){
                case 0:
                    optionA.setBackground(Color.red);
                    optionA.repaint();
                    optionB.setBackground(Color.pink);
                    optionB.repaint();
                    optionC.setBackground(Color.pink);
                    optionC.repaint();
                    break;
                case 1:
                    optionA.setBackground(Color.pink);
                    optionA.repaint();
                    optionB.setBackground(Color.red);
                    optionB.repaint();
                    optionC.setBackground(Color.pink);
                    optionC.repaint();
                    break;
                case 2:
                    optionA.setBackground(Color.pink);
                    optionA.repaint();
                    optionB.setBackground(Color.pink);
                    optionB.repaint();
                    optionC.setBackground(Color.red);
                    optionC.repaint();
                    break;
                default:
                    System.out.println("ERROR: userinput out of bounds");
            }
        }
    }
    
    public void answerQuestion(){
        String userInput = new String("");
        String correctAnswer = questions[currentQuestion].CorrectAnswer;
        
        String A = questions[currentQuestion].GetOptionA();
        String B = questions[currentQuestion].GetOptionB();
        String C = questions[currentQuestion].GetOptionC();
        System.out.println("Options: " + A + B + C);
        resetMyScene();
        
        if(isSelected == 0){
            userInput = "a";
        }
        if(isSelected == 1){
            userInput = "b";
        }
        if(isSelected == 2){
            userInput = "c";
        }
        else{
            userInput = "a";
        }
        System.out.println("User Picked Option: " + userInput);
        if(userInput.equals(correctAnswer)){
            currentPoints = currentPoints + 1;
            currentQuestion = currentQuestion + 1;
            System.out.println("Correct Answer");
            setSceneVisible();
            if(currentQuestion < 11){
                System.out.println(">> Loading Next Answer, Current Points: " + currentPoints);
                isSelected = 0;
                writeToScene();
            }
            else{
                System.out.println("Completed All Questions");
                endGame();
                isDone = true;
            }
        }
        else{
            currentQuestion = currentQuestion + 1;
            System.out.println("Wrong Answer, Correct Answer Was: " + correctAnswer);
            setSceneVisible();
            if(currentQuestion < 11){
                System.out.println(">> Loading Next Answer, Current Points: " + currentPoints);
                isSelected = 0;
                writeToScene();
            }
            else{
                System.out.println("Completed All Questions");
                endGame();
                isDone = true;
            }
        }
    }
    
    public void endGame(){
        resetMyScene();
        endText = new HText("You have reached the end. Thanks for playing! ( Press ENTER to restart )");
        endText.setBackground(Color.pink);
        endText.setBackgroundMode(HVisible.BACKGROUND_FILL);
        endText.setLocation(200, 20);
        endText.setSize(500, 100);
        endText.setBordersEnabled(false);
        myScene.add(endText);
        
        if(currentPoints > 0 && currentPoints < 6){
            result = new HText("Aw Man, you only got " + currentPoints + " correct answers.");
            result.setBackground(Color.red);
            result.setBackgroundMode(HVisible.BACKGROUND_FILL);
        }
        else{
            result = new HText("GG, you got " + currentPoints + " correct answers!");
            result.setBackground(Color.green);
            result.setBackgroundMode(HVisible.BACKGROUND_FILL);
        }
        result.setLocation(200, 120);
        result.setSize(500, 100);
        result.setBordersEnabled(false);
        myScene.add(result);
        
        setSceneVisible();
        
    }
    
    
    
    
    public void pauseXlet() {
     
    }

    public void destroyXlet(boolean unconditional) {

    }
}
