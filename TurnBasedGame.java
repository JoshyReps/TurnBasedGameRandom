package com.mycompany.turnbasedgame;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class TurnBasedGame {

    public static Random random = new Random();
    public static Scanner s = new Scanner(System.in);
    
    // ------------------------------ HP    Name   Max  Min Passive Ability
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
    
    // -------------------- Location Area Coordinates ----------------------
    int[][] locationArea = {{0,0},{15,5000},{300,-3000},{750,1641},{162365125, 512516434}};
    // ---------------------------------------------------------------------
    
    
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
                           """);
        
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

                                    [0,0] -> Spawn / Foosha Village
                                    [15,100] -> Sky Island
                                    [25,-100] -> Fish Island
                                    [50,50] -> The Land of Wano
                                    [333, 666] -> The Grand Line
                                    Back to Menu
                                   """);
                        case 3 -> encounter();
                        case 4 -> heart = false;
                    }
                }
                else System.out.println("Invalid Input");
                
            } 
            catch (Exception e) {
                System.out.println("That is an Invalid Input");
            }
        }
    }
    
    
    private static void travel () {
        
        while(true) {
            
            System.out.println("""
                               --------------- Player Movement Control -------------
                               
                               Current Location : %s
                               
                               %s's Position : x - %d  y - %d
                               
                               "w" -> North
                               "a" -> West
                               "s" -> South
                               "d" -> East
                               "x" -> Back to Menu
                               -----------------------------------------------------
                               """.formatted(coordinateToLocation(), player.playerName, player.playerPosition[0], player.playerPosition[1]));
            
            System.out.print("Enter Movement: ");

            char inputAction = s.nextLine().trim().toLowerCase().charAt(0);
            
            if(inputAction == 'x') break;
            
            switch(inputAction) {
                case 'w' -> player.playerPosition[1]++;
                case 'a' -> player.playerPosition[0]--;
                case 's' -> player.playerPosition[1]--;
                case 'd' -> player.playerPosition[0]++;
            }
        }
    }
    
    private static String coordinateToLocation () {
        
        String stringPosition = Arrays.toString(player.playerPosition);
        
        return switch(stringPosition) {
            case "[0, 0]" -> "Spawn/Foosha Village";
            case "[15,100]" -> "Sky Island";
            case "[25,-100]" -> "Fish Island";
            case "[50,50]" -> "The Land of Wano";
            case "[333, 666]" -> "GrandLine";
            default -> "Unknown";
        };
    }
    
    
    private static void encounter() {
        
        // ------------------ Random Bot Name Generator ------------------------
        String botFirstName = firstNames.get(random.nextInt(15));
        String botLastName = lastNames.get(random.nextInt(15));
        
        String botName = "%s %s".formatted(botFirstName, botLastName);
        
        firstNames.remove(botFirstName);
        lastNames.remove(botLastName);
        // ---------------------------------------------------------------------
        
        Character bot = new Character(100, botName, 10, 1, "Heal", "UnoReverse");
        
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
                System.out.println("------------------------------------------");
                
                if(bot.playerHP <= 0) {
                    System.out.println("You Won"); break;
                }
                
            }
            else {
                
                String randomBotChoice = switch (random.nextInt(3) + 1) {case 1 -> "attack"; case 2 -> "stun"; case 3 -> "skip"; default -> "ran";}; 
                System.out.printf("%n----- %s at Play! (Random Choice) -----%n".formatted(bot.playerName));
                if(bot.passive.containsKey("Heal")) bot.passive.get("Heal").passiveAbility(bot, player);
                if(player.actionHistoryStack.peek().equals("attack")) bot.passive.get("UnoReverse").passiveAbility(bot, player);
                encounterInputAction(bot, player, randomBotChoice);
                if(randomBotChoice.equals(randomBotChoice)) parry(player);
                System.out.println("------------------------------------------");
                
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
            System.out.printf("%s Has been burned to %dHP turns left%n", character.playerName, 
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

abstract class Passive {
    
    public abstract void passiveAbility (Character character, Character enemy);
    
    public static Passive assignPassive(String passive) {
        return switch(passive) {
            case "Heal" -> new HealPassive();
            case "TurnTechnique" -> new TurnTechniquePassive();
            case "UnoReverse" -> new UnoReversePassive();
            default -> null;
        };
    }
    
    @Override
    public String toString() {
        return this.getClass().getName();
    }
}

    class HealPassive extends Passive {
        
        @Override
        public void passiveAbility(Character character, Character enemy) {

            if(character.playerHPStack.size() <= 1 || new Random().nextInt(4) + 1 != 4) return;
            character.playerHPStack.pop();
            character.playerHP = character.playerHPStack.peek();
            System.out.printf("%s's *Passive Healing Ability* has healed itself back to %s%n", character.playerName, character.playerHP);
        }
    }
    
    class TurnTechniquePassive extends Passive {
        
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
    
    class UnoReversePassive extends Passive {
        
        @Override
        public void passiveAbility(Character character, Character enemy) {
            
            Stack<Integer> damageInflictedStack = enemy.damageInflicted;
            
            if(damageInflictedStack.isEmpty()) damageInflictedStack.add(character.playerDMG);
            
            if (new Random().nextInt(5) == 0) {
                if (!damageInflictedStack.isEmpty()) {
                    System.out.printf("%s has used *Uno Reversed Technique*. %s healed, %s damage returned %n", 
                            character.playerName,
                            damageInflictedStack.peek(),
                            damageInflictedStack.peek());
                            character.playerHP += damageInflictedStack.peek();
                            enemy.playerDMG -= damageInflictedStack.pop();
                } 
                else System.out.println("UNO reverse card failed.");
            }
        }
    }
   