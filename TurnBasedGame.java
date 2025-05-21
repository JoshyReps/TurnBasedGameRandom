package com.mycompany.turnbasedgamereponoya;

import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class TurnBasedGameReponoya {

    public static Random random = new Random();
    public static Scanner s = new Scanner(System.in);
    
    // ------------------------------------  HP    Name   Max Min  Passive Ability
    static Character player = new Character(100, "Player", 10, 1, "TurnTechnique");
    // ---------------------------------------------------------------------
    
    // ----------------- Random Names LinkedList ---------------------------
    public static LinkedList<String> firstNames = new LinkedList<>(List.of(
        "Michael", "James", "Cabasan","Tigbason","Luther", "Anthony","Edwards","Steph","Curry","Lebron",
        "James", "Russel", "Westbrook","Kobe","Bryant", "Butler", "Vincent", "Uchiha","Hyuga","Sasuke",
        "Naruto", "Uzumaki", "Killua","Zero","Michele", "Sarada", "Kyle", "Izuki","Natalio","Solis",
        "Justine", "Bronny", "Ligma","Ballu","Amper", "Maroon", "Brody", "Perry","Platapus","Monster",
        "Kage", "Izuka", "Joshua","Iron","Silver", "Bronze", "Golden", "Superior","Gon","Kurapika"
    )); 
        
    public static LinkedList<String> lastNames = new LinkedList<>(List.of(
        "Reponoya", "Ampi", "Palencia","Gonzaga","Capybara", "Dela Cruz","Garcia","Undangan","Olpenado","Cruz",
        "Padilla", "Cartel", "Mc Daniel","Magdallona","Pogi", "Coffin", "Grave", "Nightmare","Ghost","Oppenheimer",
        "Hitler", "Nazi", "Kanye","Lamar","Kendric", "Drake","Doechi","2pac","Zombie","Creeper",
        "Creep", "T-rex", "Crow","Oleander","Belladonna", "Craven","Raimi","Ash","Bruce","Campbell",
        "Cairns", "Hyuga", "Bovary","Marlowe","Poe", "Frost","Fire","Flame","Toxic","Poison"
    )); 
    // -------------------------------------------------------------------------
    
    public static void main(String[] args) {
        
        boolean heart = true;
        
        while(heart) {
            
            // --------------------------- Open Menu -------------------------------
            System.out.println("""
                           -------------- Hello Gamer: "%s"! -------------
                           
                           What Would you like to Do?
                           1. -> Travel
                           2. -> Check Map
                           3. -> Fight Random
                           4. -> Exit
                           """.formatted(player.playerName));
        
            System.out.print("Enter Input: ");
            // ---------------------------------------------------------------------
            
            try {
                
                int actionChoice = s.nextInt(); s.nextLine();
                
                if(actionChoice >= 1 && actionChoice <= 3) {
                    switch(actionChoice) {
                        case 1 -> travel();
                        case 2 -> System.out.println("""
                                    -------------- %s's Travel Map! --------------

                                    Area Coordinates :

                                    [0, 0] -> Spawn / Foosha Village
                                    [3, 12] -> Sky Island
                                    [-3, -12] -> Fish Island
                                    [15, 15] -> The Land of Wano
                                    [50, 50] -> The Grand Line
                                    Back to Menu
                                   """);
                        case 3 -> encounter(100, " (BOT)", 10, 1);
                        case 4 -> heart = false;
                    }
                }
                else System.out.println("Invalid Input");
                
            } 
            catch (InterruptedException e) {
                System.out.println("Thread Interrupted"); s.nextLine();
            }
            catch (InputMismatchException e) {
                System.out.println("That is an Invalid Input"); s.nextLine();
            }
        }
    }
    
    
    private static void travel () throws InterruptedException {
        
        // -------------------- Location Area Coordinates ----------------------
        int[][] locationArea = {{0,0},{3,12},{-3,-12},{15,15},{50, 50}};
        // ---------------------------------------------------------------------
        
        while(true) {
            
            System.out.println("""
                               %n--------------- Player Movement Control -------------
                               
                               Current Location : %s
                               
                               %s's Position : x - %d  y - %d
                               
                               "w" -> North
                               "a" -> West
                               "s" -> South
                               "d" -> East
                               "x" -> Back to Menu
                               -----------------------------------------------------%n
                               """.formatted(coordinateToLocation(), player.playerName, player.playerPosition[0], player.playerPosition[1]));
            
            
            for(int[] area : locationArea) {
                
                if(Arrays.equals(area, player.playerPosition)){
                    
                    if(Arrays.toString(player.playerPosition).equals("[0, 0]")) {System.out.println("Welcome to Foosha Village/Spawn\n"); break;}
                    
                    String message = "It Appears That....,You Have Ventured in a New Location....,Prepare To DIE!!!!\n";
                    
                    for(var sentence : message.split(",")) {
                        System.out.println(sentence);
                        Thread.sleep(3500L);
                    }
                    
                    System.out.println("____________________________________________________");
                    System.out.println("----------------!! BOSS BATTLE !!-------------------");
                    switch(Arrays.toString(player.playerPosition)) {
                        case "[3, 12]" -> encounter(150, " (Sky Island Boss)", 15, 5);
                        case "[-3, -12]" -> encounter(50, " (Fish Island Boss)", 30, 15);
                        case "[15, 15]" -> encounter(250, " (Wano Boss)", 25, 10); 
                        case "[50, 50]" -> encounter(300, " (Grand Line Boss)", 30, 15); 
                    }
                    
                    break;
                }
            }

            System.out.print("Enter Movement: ");
            
            String inputAction = s.nextLine().trim().toLowerCase();
            if(inputAction.isEmpty()) System.out.println("Bruh Moment");
            else {
                
                
                
                if(inputAction.charAt(0) == 'x') break;

                switch(inputAction.charAt(0)) {
                    case 'w' -> player.playerPosition[1]++;
                    case 'a' -> player.playerPosition[0]--;
                    case 's' -> player.playerPosition[1]--;
                    case 'd' -> player.playerPosition[0]++;
                }
            }
        }
    }
    
//    private static String enteredNewLand() {
//        
//    }
    
    private static String coordinateToLocation () {
        
        String stringPosition = Arrays.toString(player.playerPosition);
        
        return switch(stringPosition) {
            case "[0, 0]" -> "Spawn/Foosha Village";
            case "[3, 12]" -> "Sky Island";
            case "[-3, -12]" -> "Fish Island";
            case "[15, 15]" -> "The Land of Wano";
            case "[30, 30]" -> "GrandLine";
            default -> "Wilderness";
        };
    }
    
    
    private static void encounter(int botHP, String additionalInfo, int maxDMG, int minDMG) throws InterruptedException {
        
        // ---------------------- Player HP Reset ------------------------------
        player.playerHP = 100;
        // ---------------------------------------------------------------------
        
        // ------------------ Random Bot Name Generator ------------------------
        String botFirstName = firstNames.get(random.nextInt(15));
        String botLastName = lastNames.get(random.nextInt(15));
        
        String botName = "%s %s%s".formatted(botFirstName, botLastName, additionalInfo);
        
        firstNames.remove(botFirstName);
        lastNames.remove(botLastName);
        // ---------------------------------------------------------------------
        
        Character bot = new Character(botHP, botName, maxDMG, minDMG, "Heal", "UnoReverse");
        
        int gameTime = 1;
        
        while(true) {
            
            if(gameTime % 2 != 0) {
                
                System.out.println("""
                               
                               %s HP : %d HP
                               %s HP : %d HP
                               
                               Actions :
                               `>> type `attack`
                               `>> type `stun`
                               `>> type `skip`
                               """.formatted(player.playerName, player.playerHP,bot.playerName, bot.playerHP));
                
                System.out.print("Enter Action : ");
                String actionStringInput = s.nextLine().trim().toLowerCase();
                
                System.out.println("\n----------- Player at Play! ------------");
                encounterInputAction(player, bot, actionStringInput);
                System.out.println("------------------------------------------"); Thread.sleep(2000L);
                
                if(bot.playerHP <= 0) {
                    System.out.println("You Won"); break;
                }
                
            }
            else {
                
                System.out.printf("%n%s is Analyzing their Next Brilliant Move!!%n".formatted(bot.playerName)); Thread.sleep(7000L);
                
                String randomBotChoice = switch (random.nextInt(3) + 1) {case 1 -> "attack"; case 2 -> "stun"; case 3 -> "skip"; default -> "ran";}; 
                System.out.printf("%n----- %s at Play! (Random Choice) -----%n".formatted(bot.playerName));
                if(bot.passive.containsKey("Heal")) bot.passive.get("Heal").passiveAbility(bot, player);
                if(player.actionHistoryStack.peek().equals("attack")) bot.passive.get("UnoReverse").passiveAbility(bot, player);
                encounterInputAction(bot, player, randomBotChoice);
                if(randomBotChoice.equals(randomBotChoice)) parry(player);
                System.out.println("------------------------------------------"); Thread.sleep(5000L);
                
                if(player.playerHP <= 0) {
                    System.out.println("You Lost"); break;
                }
            }
            gameTime++;
        }
    }

    
    private static void encounterInputAction (Character character, Character enemy, String stringInput) {
        
        character.actionHistoryStack.push(stringInput);
        
        if(character.stunned != 0) {
            System.out.printf("You are Stunned By %d turns left %n", character.stunned--);
            return;
        }
        
        if(character.burned != 0) {
            System.out.printf("%s Has been burned to %d HP   %d turns left%n", character.playerName, 
                                                                         character.playerHP -= 3,
                                                                         character.burned--);
        }
        
        switch(stringInput) {
            case "attack" -> {
                character.attack(enemy);
            }
            case "stun" -> {
                character.stun(enemy);
            }
            case "skip" -> {
                System.out.println("Skipped Turn");
            }
            default -> {
                System.out.println("That is not a valid Action!!");
            }
        }
    }
    
    // ----------------------------------- Parry by Mark Vincent Palencia ----------------------------------------
    // ===========================================================================================================
    private static void parry (Character player){
        //  0 1 2 3
      int chance = random.nextInt(16);
      if(chance != 1) {
          return;
      }
      
      int chance2 = random.nextInt(5);
      double array [] = {0.05,0.1,0.15,0.20,0.25};
      double boost = array[chance2];
     
      System.out.println("*Parry Technique* Player Has Parried the Attack " + (boost * 100) + "% to HP and DMG");
      
      player.playerHP = (int)(player.playerHP + (player.playerHP * boost));
      player.playerMinDMG = (int)(player.playerMinDMG + (player.playerMinDMG * boost));
      player.playerMaxDMG = (int)(player.playerMaxDMG + (player.playerMaxDMG * boost));         
    }// ===========================================================================================================
    // ------------------------------------------------------------------------------------------------------------
}

class Character {
        
    public static Random random = new Random();

    Stack<Integer> playerHPStack = new Stack<>();
    Stack<Integer> damageInflicted = new Stack<>();
    Stack<String> actionHistoryStack = new Stack<>();

    int[] playerPosition;
    
    public String playerName;
    public int playerHP;
    public int playerDMG;
    public int playerMaxDMG;
    public int playerMinDMG;
    
    //effects
    public int stunned;
    public int burned;
    
    HashMap<String, Passive> passive;

    public Character(int playerHP, String playerName, int playerMaxDMG, int playerMinDMG, String... passive) {
        
        this.playerName = playerName;
        this.playerHP = playerHP;
        this.playerMaxDMG = playerMaxDMG;
        this.playerMinDMG = playerMinDMG;
        
        this.passive = new HashMap<>();
        this.playerPosition = new int[2];
        
        for(String pas : passive) this.passive.put(pas, Passive.assignPassive(pas));
        
        playerHPStack.push(playerHP);
    }

    public void attack(Character enemy) {

        playerDMG = random.nextInt(playerMaxDMG) + playerMinDMG;
        if(passive.containsKey("TurnTechnique")) {
            TurnTechniquePassive turnPassive = (TurnTechniquePassive) this.passive.get("TurnTechnique");
            turnPassive.passiveAbility(this, enemy);
        }
        System.out.print("""
                        %s has dealt %d Damage
                        %s has now %d HP
                         """.formatted( playerName, 
                                        playerDMG,
                                        enemy.playerName, 
                                        enemy.damageAttack(playerDMG)));
        damageInflicted.push(playerDMG);
        enemy.playerHPStack.push(enemy.playerHP);
    }

    public void stun(Character enemy) {
        
        if(new Random().nextInt(4) != 0) {
            System.out.println("Tried to Stun But Failed"); return;
        }
        
        int stunAmount = random.nextInt(3) + 1;
        System.out.printf("%s have Stun %s by %d Turn!%n".formatted(playerName, 
                                                          enemy.playerName, 
                                                          enemy.stunned = stunAmount));
    }
    
    public void passive(Character enemy) {
        if(!passive.isEmpty()) {
            for (var pas : passive.entrySet()) {
                if(pas.getKey().equals("TurnTechnique")) continue;
                Passive curPas = Passive.assignPassive(pas.getKey());
                if(passive!=null) curPas.passiveAbility(this, enemy);
            }
        }
    }
    
    public int damageAttack(int damageDealth) {
        if(playerHP - damageDealth <= 0) playerHP = 0;
        else playerHP -= damageDealth;
        return playerHP;
    }
    
    
}

interface Passive {
    
    public abstract void passiveAbility (Character character, Character enemy);
    
    public static Passive assignPassive(String passive) {
        return switch(passive) {
            case "Heal" -> new HealPassive();
            case "TurnTechnique" -> new TurnTechniquePassive();
            case "UnoReverse" -> new UnoReversePassive();
            default -> null;
        };
    }
}

class HealPassive implements Passive {

    @Override
    public void passiveAbility(Character character, Character enemy) {

        if(character.playerHPStack.size() <= 1 || new Random().nextInt(4) + 1 != 4) return;
        character.playerHPStack.pop();
        character.playerHP = character.playerHPStack.peek();
        System.out.printf("%s's *Passive Healing Ability* has healed itself back to %s%n", character.playerName, character.playerHP);
    }
}

class TurnTechniquePassive implements Passive {

    Queue<Integer> attackStack = new LinkedList<>();

    @Override
    public void passiveAbility(Character character, Character enemy) {
        attackStack.add(character.playerDMG);
        if(attackStack.size() % 4 == 0) {
            int abilityChoice = new Random().nextInt(2);
            if(abilityChoice == 0) {
                System.out.println("*Turn Technique Passive/Jingu Mastery* has been activiated -> double damage");
                character.playerDMG = character.playerDMG * 2;
            }
            else {
                System.out.println("*Turn Technique Passive/Jingu Mastery* has been activiated -> enemy has been burned for 3 turns");
                enemy.burned = 3;
            }
        }
    }
}

class UnoReversePassive implements Passive {

    @Override
    public void passiveAbility(Character character, Character enemy) {

        Stack<Integer> damageInflictedStack = enemy.damageInflicted;

        if(damageInflictedStack.isEmpty()) damageInflictedStack.add(character.playerDMG);

        if (new Random().nextInt(10) == 0) {
            if (!damageInflictedStack.isEmpty()) {
                System.out.printf("%s has used *Uno Reversed Technique*. %s healed, %s damage returned %n", 
                        character.playerName,
                        damageInflictedStack.peek(),
                        damageInflictedStack.peek());
                        character.playerHPStack.push(character.playerHP += damageInflictedStack.peek());
                        enemy.playerDMG -= damageInflictedStack.pop();
            } 
            else System.out.println("UNO reverse card failed.");
        }
    }
}
