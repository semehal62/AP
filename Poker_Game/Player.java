package Poker_Game;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Player {
    int score = 0;
    Set<String> cards = new TreeSet<>();
    
    public void take_cards( String[] deck) {
        Random rand = new Random();
        while (cards.size() < 4) {
            int c_on = rand.nextInt(13);
            cards.add(deck[c_on]);
        }
    }

    public static void main(String[] arg) {
        Random rand = new Random();
        String[] deck = {"King", "Queen", "Jack", "10", "9", "8", "7", "6", "5", "4", "3", "2", "Ace"};
        Scanner input = new Scanner(System.in);

        Player p1 = new Player();
        Player p2 = new Player();
        
        ArrayList<String> tableCards = new ArrayList<String>();
        Set<Integer> given = new TreeSet<>();

        // Initial table deal
        while (tableCards.size() < 4) {
            int c_on = rand.nextInt(13);
            if (given.add(c_on)) {
                tableCards.add(deck[c_on]);
            }
        }

        p1.take_cards(deck);
        p2.take_cards(deck);

        int turn = 0;
        while (p1.cards.size() > 0 || p2.cards.size() > 0) {
            Player current = (turn == 0) ? p1 : p2;

            if (current.cards.size() > 0) {
                boolean moved = false; // Track if the player made a VALID move
                
                while (!moved) { // Keep asking the same player until they succeed
                    System.out.println("\n--- Player " + (turn + 1) + "'s turn ---");
                    System.out.println("Table: " + tableCards);
                    System.out.println("Your Hand: " + current.cards);
                    System.out.print("1. Take Match | 2. Drop Card: ");
                    
                    int choice = input.nextInt();
                    System.out.print("Enter card name: ");
                    String taken = input.next();

                    if (choice == 1) {
                        if (current.cards.contains(taken) && tableCards.contains(taken)) {
                            current.score++;
                            current.cards.remove(taken);
                            tableCards.remove(taken);
                            System.out.println(">> MATCHED! Point gained.");
                            moved = true; // Move successful
                        } else {
                            System.out.println(">> ERROR: Card not in hand or not on table. Try again!");
                        }
                    } else if (choice == 2) {
                        if (current.cards.contains(taken)) {
                            current.cards.remove(taken);
                            tableCards.add(taken);
                            System.out.println(">> Dropped " + taken);
                            moved = true; // Move successful
                        } else {
                            System.out.println(">> ERROR: You don't have that card. Try again!");
                        }
                    } else {
                        System.out.println(">> Invalid choice! Select 1 or 2.");
                    }
                }
                
                turn = 1 - turn; // Switch turn ONLY after a successful move
            } else {
                turn = 1 - turn; // Skip player if they have no cards
            }
        }
        
        System.out.println("\nGame Over! P1: " + p1.score + " | P2: " + p2.score);
    }
}