/**
 * @author Mustafa Ilker Saraç
 */
package GameComponents;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;


import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import GUIComponents.MainGameFrame;
import GUIComponents.MainGameFrame;




public class GameManager extends JPanel implements ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	//private static final GameManager Instance = new GameManager();
	public static final int INITAL_TIME = (int)System.currentTimeMillis() / 1000;//initial system time in seconds
	public static final int WIND_CHANGE_FACTOR = 10;//wind factor will change randomly in every this much second.
	public static final int GAME_HEIGHT = 700;
	public static final int GAME_WIDTH = 700;
	public static final int LAST_LEVEL = 3;//burada bir bug var, etkisiz eleman
	public static final int TIGHT_TURN_FACTOR = 5;//every one direction command is multipliedd by this factor for tightness
	public static final int BOAT_SPEED_FACTOR = 4;//Directly effects the speed of the boat
	public static final double WIND_FORCE_FACTOR = BOAT_SPEED_FACTOR * 0.55;//Computed according to the boats speed factor
	public static final double WIND_FORCE_FACTOR2 = BOAT_SPEED_FACTOR * 0.7;//Computed according to the boats speed factor
	public static final int INITIAL_BOAT_LIFE_AMOUNT = 5;
	public static final int INITIAL_BOAT_BULLET_AMOUNT = 5;
	public static final int BULLET_BONUS_AMOUNT_FACTOR = 5;
	public static final int SPEED_BONUS_FACTOR = 2;
	public static final int BULLET_SPEED_FACTOR = 2 * BOAT_SPEED_FACTOR * SPEED_BONUS_FACTOR;//Computed according to the boats speed factor


	private Timer timer;
	private MainGameFrame mgf;
	//private PausePanelGM pp;//pause panel

	//Create game objects
	public WindManager wind;
	private Boat boat;
	//private Collidable boat;
	private ArrayList<Collidable> buoys;
	private ArrayList<Collidable> islands;
	private ArrayList<Collidable> bonuses;

//	private boolean ingame;
	private boolean pauseFlag;
	private boolean nextLevelFlag;
	private boolean directionKeysFlag;
	private boolean difficultyFlag;
	private int currentLevel;


	//LEVEL1 instantiations
	private int gamePoint;
	private int[][] buoyPositions = {
			{250,500}, {300,100}, {500,300},{450,125},{400,200},
			{350,400},{200,250}
	};
	//create islandPositions/types
	private int[][] islandPositions = {
			{50,175},//small
			{550,300},//medium
			{50,450}//medium
	};
	private String[] islandTypes = {"small", "medium", "medium" };
	//create bonusPositions/types
	private int[][] bonusPositions = {
			{60,350},{500,550}
	};
	//private String[] bonusTypes = {"lifebonus", "speedbonus", "bulletbonus"};

	/**Constructor*/
	public GameManager(MainGameFrame mgf1){
		//to be able to reach main menu from the game we need to pass it to the actual game manager to know its caller.
		mgf = mgf1;

		setSize(GAME_WIDTH, GAME_HEIGHT);
		addKeyListener(new TAdapter());
		addKeyListener(new PauseAdapter(this));
		setFocusable(true);
		setBackground(Color.CYAN);
		setDoubleBuffered(true);

		//ingame = true;
		pauseFlag = false;
		nextLevelFlag = false;
		directionKeysFlag = mgf.getControls();
		difficultyFlag = mgf.getDifficulty();
		currentLevel = 1;

		//Init game objects
		wind = WindManager.getInstance();//Initialize singleton WindManager class
		boat = new Boat(0,20);

		initGame();
		//gamePoint = 0;


		//Init Timer
		timer = new Timer(40, this);
		startTimer();

	}

	/**timer methods*/
	public void startTimer(){
		timer.start();
	}
        /**timer methods*/
	public void stopTimer(){
		timer.stop();
	}

	public void addNotify(){//bu ne işe yarar??-yok edilebilir.
		super.addNotify();
	}
	public void setDifficultInBoat(boolean newDifficult){
		difficultyFlag = newDifficult;
	}
	public boolean getDifficulty(){
		return difficultyFlag;
	}

	/**Init methods for multiple objects buoy, island, bonus*/
	private void initBuoys(){
		buoys = new ArrayList<Collidable>();
		for(int i = 0; i  < buoyPositions.length; i++){
			buoys.add(new Buoy(buoyPositions[i][0], buoyPositions[i][1]));
		}
	}
        /**Init methods for multiple objects buoy, island, bonus*/
	private void initIslands(){
		islands = new ArrayList<Collidable>();
		for(int i = 0; i < islandPositions.length; i++){
			islands.add(new Island(islandPositions[i][0], islandPositions[i][1], islandTypes[i]));
		}
	}
        /**Init methods for multiple objects buoy, island, bonus*/
	private void initBonuses(){
		bonuses = new ArrayList<Collidable>();
		for(int i = 0; i < bonusPositions.length; i++){
			//bonuses.add(new Bonus(bonusPositions[i][0], bonusPositions[i][1], bonusTypes[i]));
			bonuses.add(new Bonus(bonusPositions[i][0], bonusPositions[i][1], ""));
		}
	}
        /**Level initialization handled here*/
	public void initGame(){
		//First level initializations-burada can da sıfırlanmalı, oyunu restart yapan method bu olmalı
		if(currentLevel == 1){
			boat.setFlashForward(false);//init bonus state
			boat.replenishUsedNumOfBullets();//init bonus state
			//boat.setNumOfLives(INITIAL_BOAT_LIFE_AMOUNT);//init bonus state
			boat.setBoatImage(mgf.getBoatColor());//To set the proper image from the main game frame

			//setDifficultInBoat(mgf.getDifficulty());
		        //System.out.println("difffi: " + mgf.getDifficulty());
                        if(mgf.getDifficulty()){
                            boat.setNumOfLives(INITIAL_BOAT_LIFE_AMOUNT - 2);
                        }
                        else{
                            boat.setNumOfLives(INITIAL_BOAT_LIFE_AMOUNT);
                        }
                        
			boat.setDirectionKeysOn(mgf.getControls());

			boat.setPosition(0, 20);
			boat.setDirection(0);
			boat.setVisible(true);
			//Game object positions
			buoyPositions = new int[][]{
					{250,500}, {300,100}, {500,300},{450,125},{400,200},
					{350,400},{200,250}
			};
			islandPositions = new int[][]{
					{50,175},//small
					{550,300},//medium
					{50,450}//medium
			};
			islandTypes = new String[]{"small", "medium", "medium" };
			bonusPositions = new int[][]{
					{60,350},{500,550}
			};
			gamePoint = 0;
			initBuoys();
			initIslands();
			initBonuses();
			System.out.println("inside init game level-1");
		}
		//other level initializations
		if(currentLevel == 2){
			initGameLevel2();
		}
		if(currentLevel == 3){
			initGameLevel3();
		}
	}
	private void initGameLevel2(){
		boat.setFlashForward(false);//init bonus state
		boat.replenishUsedNumOfBullets();//init bonus state
		boat.setNumOfLives(INITIAL_BOAT_LIFE_AMOUNT);//init bonus state
		boat.setBoatImage(mgf.getBoatColor());
		boat.setPosition(0, 20);
		boat.setDirection(0);
		boat.setVisible(true);
		//Game object positions
		buoyPositions = new int[][]{{100,100}, {200,300}, {100,150}, {200,600}, {400,400}, {500,650}};
		islandPositions = new int[][]{
				{40,300},	//Small islands
				{500,200},	//Medium islands
				{400,400}	//Large islands
		};
		islandTypes = new String[]{ "small", "medium", "large" };
		bonusPositions = new int[][]{{100,500}, {500,100}, {400,200}};

		initBuoys();
		initIslands();
		initBonuses();
		System.out.println("inside init game level-2");
	}
	private void initGameLevel3(){
		boat.setFlashForward(false);//init bonus state
		boat.replenishUsedNumOfBullets();//init bonus state
		boat.setNumOfLives(INITIAL_BOAT_LIFE_AMOUNT);//init bonus state
		boat.setBoatImage(mgf.getBoatColor());
		boat.setPosition(0, 20);
		boat.setDirection(0);
		boat.setVisible(true);
		//Game object positions
		buoyPositions = new int[][]{
				{400,500}, {350,120}, {630,400},{500,145},{50,550},
				{650,200},{250,400},{200,250},{600,630},{60,375}
		};
		islandPositions = new int[][]{
				{50,100},//medium
				{550,500},//small
				{100,500},//medium
				{300,200}//large
		};
		islandTypes = new String[]{"medium", "small", "medium", "large" };

		bonusPositions = new int[][]{
				{250,100},{80,310},{450,550},{600,330}
		};
		initBuoys();
		initIslands();
		initBonuses();
		System.out.println("inside init game level-3");
	}
//	private void initLevel(int level){//initializes levels according to the level parameter
//
//	}
	public void paint(Graphics g){
		super.paint(g);

		Graphics2D g2d = (Graphics2D)g;

		//Windmill drawing
		g2d.rotate(wind.getDirection()*Math.PI / 180, 636,64);
		g2d.drawImage(wind.getImage(), 572, 0, this);
		g2d.rotate(-wind.getDirection()*Math.PI / 180, 636,64);


		//Boat drawing
		if(boat.isVisible()){
			g2d.rotate(TIGHT_TURN_FACTOR*boat.getDirection()*Math.PI / 180, boat.getX()+16, boat.getY()+16);
			//g2d.drawImage(boat.getImage(), boat.getX(), boat.getY(), this);
			g2d.drawImage(boat.getImage(), boat.getX(), boat.getY(), this);
			//TEST-sınırları görmek için
//			Rectangle r1 = boat.getBounds();
//			g2d.drawRect(r1.x, r1.y, r1.width, r1.height);
			g2d.rotate(-TIGHT_TURN_FACTOR*boat.getDirection()*Math.PI / 180, boat.getX()+16, boat.getY()+16);
			if((buoys.size() == 0) && (currentLevel <= LAST_LEVEL)){//level atlama ve başarılı bir şekilde oyunu bitirme burada olacak
				System.out.println("All buoys finished, well done!");
				int tp = computeTotalScore();
				System.out.println("Total Score: " + tp);
				nextLevelFlag = true;//Actionlistenera tek seferlik girebilmek için bir flag
				//System.exit(0);
			}
		}
		else{//If boat is dead then check for remaining lives
			if(boat.getNumOfLives() > 0){
				boat.decrementNumOfLives();
				boat.setPosition(0, 20);
				boat.setDirection(0);
				boat.setVisible(true);
			}
			else{//No remaining lives case
				//g2d.drawString("Game Over!", 80, 15);
				System.out.println("Game Over");
				int tp = computeTotalScore();
				System.out.println("Total Score: " + tp);
				currentLevel = 1;
        		CardLayout c2 = (CardLayout)(mgf.getCanvas().getLayout());
        		c2.show(mgf.getCanvas(), "GUI");
				//System.exit(0);
			}
		}


		//Island drawing
		for(int i = 0; i < islands.size(); i++){
			Island is = (Island)islands.get(i);
			g2d.drawImage(is.getImage(), is.getX(), is.getY(), this);
		}

		//Bullet drawing
		ArrayList<Bullet> bs = boat.getBulletsList();
		for(int i = 0; i < bs.size(); i++){
			Bullet b = (Bullet)bs.get(i);
			g2d.drawImage(b.getImage(), b.getX(), b.getY(), this);
		}

		//Buoy drawing
		for(int i = 0; i < buoys.size(); i++){
			Buoy b = (Buoy)buoys.get(i);
			if(b.isVisible()){
				g2d.drawImage(b.getImage(), b.getX(), b.getY(), this);
			}
		}

		//Bonus drawing
		for(int i = 0; i < bonuses.size(); i++){
			Bonus b = (Bonus)bonuses.get(i);
			if(b.isVisible()){
				g2d.drawImage(b.getImage(), b.getX(), b.getY(), this);
			}
			else{
				if(b.getBonusType().equalsIgnoreCase("lifebonus")){
					boat.incrementNumOfLives();
				}
				if(b.getBonusType().equalsIgnoreCase("speedbonus")){
					boat.setFlashForward(true);
				}
				if(b.getBonusType().equalsIgnoreCase("bulletbonus")){
					boat.addBonusBullets();
				}
			}
		}


		//Information
        g2d.drawString("Buoys left: " + buoys.size(), 5, 15);

        if(boat.getNumOfLives() == 0)
        	g2d.drawString("Last Chance!", 80, 15);
        else
        	g2d.drawString("Lives: " + boat.getNumOfLives(), 90, 15);
        //g2d.drawString("-" + (int)System.currentTimeMillis()/1000, 5, 25);

        g2d.drawString("Score: " + gamePoint, 160, 15);
        g2d.drawString("Bullets: " + boat.getRemainingNumOfBullets(), 235, 15);
        g2d.drawString("Level: " + currentLevel, 320, 15);


        //Generates new random wind
        wind.generateWindDirection();

        //Default system methods
        Toolkit.getDefaultToolkit().sync();//?
        g.dispose();//?
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		ArrayList<Bullet> bs = boat.getBulletsList();

		for(int i = 0; i < bs.size(); i++){
			Bullet b = (Bullet)bs.get(i);
			if(b.isVisible())
				b.move();
			else
				bs.remove(i);
		}

		for(int i = 0; i < buoys.size(); i++){
			Buoy b = (Buoy)buoys.get(i);
			if(!(b.isVisible())){
				gamePoint += b.getPoint();
				buoys.remove(i);
				//System.out.println("Remaining Buoys: " + buoys.size());
			}
		}

		for(int i = 0; i < bonuses.size(); i++){
			Bonus b = (Bonus)bonuses.get(i);
			if(!(b.isVisible())){
				gamePoint += b.getPoint();
				bonuses.remove(i);
			}
		}
		//level olayı burada hallediliyor!!
		//en son level kalmadığında highscore' a bak yazdır
		if(nextLevelFlag){
			System.out.println("next Level a geçtin");
			currentLevel++;
			System.out.println("level: " + currentLevel);
			initGame();//oyunu restart edecek
			nextLevelFlag = false;
			if(currentLevel > LAST_LEVEL){//tüm levellar bittikten sonra terminate/ana menüye dönüş, tam bu anda high score yazdır. Ancak oyunu bitirenler highscore table a girmeye hak kazanır.
				//High scores to table
				try {
					writeHighScoreToFile(computeTotalScore());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				currentLevel = 1;
				gamePoint = 0;
				initGame();
        		CardLayout c2 = (CardLayout)(mgf.getCanvas().getLayout());
        		c2.show(mgf.getCanvas(), "GUI");
				//System.exit(0);
			}
		}
		else{

		}


		boat.move();
		checkCollisions();
		repaint();
	}
	public void resetGameLevel(){
		currentLevel = 1;
	}
	public void resetGamePoint(){
		gamePoint = 0;
	}
        /**Checks all collisions between collidables*/
	public void checkCollisions(){

		ArrayList<Bullet> bulletList = boat.getBulletsList();//fire methodu çağıtıldığında boatun içinde oluşan bulletların listesi
		Rectangle boatBound = boat.getBounds();

		//boat ve ada çarpışması
		for(int i = 0; i < islands.size(); i++){
			Island is = (Island)islands.get(i);
			Rectangle islandBound = is.getBounds();
			if(boatBound.intersects(islandBound)){
				boat.setVisible(false);
			}
		}

		//boat ve buoy çarpışması
		for(int i = 0; i < buoys.size(); i++){
			Buoy b = (Buoy)buoys.get(i);
			Rectangle buoyBound = b.getBounds();
			if(boatBound.intersects(buoyBound)){
				b.setVisible(false);
				//timer.stop();
			}
		}

		//boat ve uncracked/cracked bonus çarpışması
		for(int i = 0; i < bonuses.size(); i++){
			Bonus b = (Bonus)bonuses.get(i);
			Rectangle bonusBound = b.getBounds();
			if(boatBound.intersects(bonusBound)){
				if(!b.isCracked()){//boat&uncracked bonus çarpışması
					boat.setVisible(false);
				}
				else{//boat&bonus çarpışması
					b.setVisible(false);
				}
			}
		}


		//bullet ve bonus(uncracked) çarpışması
		for(int i = 0; i < bulletList.size(); i++){
			Bullet bu = (Bullet)bulletList.get(i);
			Rectangle bulletBound = bu.getBounds();
			for(int k = 0; k < bonuses.size(); k++){
				Bonus bo = (Bonus)bonuses.get(k);
				Rectangle bonusBound = bo.getBounds();
				if(bonusBound.intersects(bulletBound) && !bo.isCracked()){
					bo.setCracked(true);
					//bo.setBonusImage(bo.getBonusType());//burası çok önemli, bonus baştan yaratılıyor sayılır.
					bo.setRandomBonusImage();
					bu.setVisible(false);
				}
			}
		}


	}
	/**Calculates the total score*/
	private int computeTotalScore(){
		int life = boat.getNumOfLives();
		int bullet = boat.getRemainingNumOfBullets();
		int point = gamePoint;

		System.out.println("life: " + life + "\nbullet: " + bullet + "\npoint: " + point);
		return life+bullet+point;
	}
	/**To arrange boat image from settings, GUI*/
	public void setBoatImageFromGUI(String newImage){
		boat.setBoatImage(newImage);
	}


        /**KeyListener for boat movements*/
       private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
        	if(!pauseFlag)
        		boat.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            if(!pauseFlag)
            	boat.keyPressed(e);
        }
    }
     /**KeyListener for pause operation*/
    private class PauseAdapter extends KeyAdapter{
    	private GameManager gm;
    	public PauseAdapter(GameManager gm){
    		this.gm = gm;
    	}

        public void keyReleased(KeyEvent e) {
        }
        public void keyPressed(KeyEvent e) {
        	if(e.getKeyCode() == KeyEvent.VK_P){
        		if(pauseFlag){//eğer oyun pause halindeyse
        			startTimer();
        			pauseFlag = false;
        			//pause paneli görünmez yap
        			//pp.setVisible(false);

        		}
        		else{//oyun oynanırken P ye basılırsa/pause haline geçilirse
        			stopTimer();
        			pauseFlag = true;

//        			CardLayout c1 = (CardLayout)(mgf.getCanvas().getLayout());
//        			c1.show(mgf.getCanvas(), "Pause Panel");
        			System.out.println("Paused by game manager");
        		}
        	}

        	if(e.getKeyCode() == KeyEvent.VK_Q){

        		System.out.println("Q pressed");
        		//oyunu pause lamak faln gerekeblir
        		gm.resetGameLevel();
        		gm.resetGamePoint();
        		gm.initGame();

        		CardLayout c2 = (CardLayout)(mgf.getCanvas().getLayout());
        		c2.show(mgf.getCanvas(), "GUI");
        	}

        }
    }

    /**Writes high scores in descending order to the file*/
    private void writeHighScoreToFile(int newHighScore) throws IOException{
    	/*
    	 * "highscores.txt"
    	 */
    	BufferedReader br = new BufferedReader(new FileReader("resources/highScores.txt"));
    	ArrayList<String> scoresList = new ArrayList<String>();

    	while(br.ready()){
    		scoresList.add(br.readLine());
    	}
    	br.close();

    	//insert new highScore
    	scoresList.add("" + newHighScore);
    	//apply bubblesort
    	for(int i = scoresList.size(); --i >= 0; ){
    		boolean flipped = false;
    		for(int j = 0; j < i; j++){
    			if(Integer.parseInt(scoresList.get(j)) > Integer.parseInt(scoresList.get(j+1))){
    				int temp = Integer.parseInt(scoresList.get(j));
    				scoresList.set(j, scoresList.get(j+1));
    				scoresList.set(j+1, "" + temp);
    				flipped = true;
    			}
    			if(!flipped){
    				break;
    			}
    		}
    	}


    	BufferedWriter bw = new BufferedWriter(new FileWriter("resources/highScores.txt"));
    	//Write the list from bottom to top to reach descending order
    	for(int i = scoresList.size()-1; i >= 0; i--){
    		bw.write(scoresList.get(i));
    		bw.newLine();
    	}
    	bw.close();

    }


}
