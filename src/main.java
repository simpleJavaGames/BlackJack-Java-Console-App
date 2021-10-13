import java.util.Scanner;
public class main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean playing = true;
        Deck Deck1 = new Deck();
        Player Player1 = new Player(500,"Lava");
        Dealer Dealer = new Dealer();
        System.out.println("Lets play blackjack!");

        do {
            int playerBetAmount = 0;
            //Player bet amount
            System.out.println(Player1.getName()+"'s balance is "+Player1.getBalance());
            System.out.print("How much would you like to bet? (Max bet 100): ");
            playerBetAmount = sc.nextInt();
            if (playerBetAmount > 100 || playerBetAmount <= 0 || playerBetAmount>Player1.getBalance()){
                System.out.println("Please enter a valid bet.");
                playerBetAmount = sc.nextInt();
            }else {
                Player1.setPlayerBetAmount(playerBetAmount);
            }
            System.out.println();

            boolean allPlayersStand = false;
            boolean allPlayersBusted = false;

            // Deal the Dealer's hand
            Dealer.addChosenCard(Deck1.dealOneCard());
            Deck1.removeTopCard();
            Dealer.addChosenCard(Deck1.dealOneCard());
            Deck1.removeTopCard();
            System.out.println(Dealer.dealerHandToStringInital());

            System.out.println(); // indent a line

            // Deal the player's hand, check for blackjack and then show value.
            Player1.addChosenCard(Deck1.dealOneCard());
            Deck1.removeTopCard();
            Player1.addChosenCard(Deck1.dealOneCard());
            Deck1.removeTopCard();
            System.out.println(Player1.getName() + "'s hand is: " + Player1.playerHandToString());
            if (Player1.hasBlackJack()) {
                System.out.println(Player1.getName() + " has a Blackjack!\n");
                allPlayersStand = true;
            } else {
                System.out.print(Player1.getName() + "'s hand is worth: (" + Player1.playerHandValue() + ")\n");
                System.out.println();
            }

            byte playerChoice = 0;

            //Player decisions

            while (allPlayersStand == false && allPlayersBusted == false) {
                System.out.print(Player1.getName() + " would you like to 1) Hit 2) Stand? ");
                playerChoice = sc.nextByte();
                switch (playerChoice) {
                    case 1:
                        Player1.hit(Deck1.dealOneCard());
                        Deck1.removeTopCard();
                        System.out.println("\n" + Player1.getName() + "'s hand is: " + Player1.playerHandToString());
                        System.out.print(Player1.getName() + "'s hand is worth: (" + Player1.playerHandValue() + ")\n");
                        if (Player1.isBusted() == true) {
                            System.out.println(Player1.getName() + " has busted!\n");
                            allPlayersBusted = true;
                        }
                        break;
                    case 2:
                        System.out.println();
                        allPlayersStand = true;
                        break;
                    default:
                        while (playerChoice != 1 && playerChoice != 2) {
                            System.out.println("Please enter a valid choice.");
                            playerChoice = sc.nextByte();
                        }
                        break;
                }
            }

            //Check if Player/Dealer has blackjack
            System.out.println("The Dealer's hand is: " + Dealer.dealerHandToString());
            if (Player1.hasBlackJack() == true && Dealer.hasBlackJack() == true) {
                wait2secs();
                System.out.println("Push! All bets are refunded.");
                System.out.println(Player1.getName()+"'s balance is now "+Player1.getBalance());
            } else if (Dealer.hasBlackJack() == true) {
                wait2secs();
                dealerWins(Player1);
            } else if (Player1.hasBlackJack() == true) {
                wait2secs();
                playerWinsBlackJack(Player1);
            }


            //Dealer decisions
            if (Player1.hasBlackJack() == false && Dealer.hasBlackJack() == false) {
                System.out.print("Dealer's hand is worth: (" + Dealer.dealerHandValue() + ")\n");
                wait2secs();
                while (Dealer.dealerHandValue() < 16) {
                    Dealer.hit(Deck1.dealOneCard());
                    Deck1.removeTopCard();
                    System.out.println("\nDealer's hand is: " + Dealer.dealerHandToString());
                    System.out.print("Dealer's hand is worth: (" + Dealer.dealerHandValue() + ")\n");
                    if (Dealer.isBusted() == true) {
                        System.out.println("Dealer has busted!");
                    }
                    wait2secs();
                }

                while (Dealer.dealerHandValue() < Player1.playerHandValue() && Player1.isBusted() == false) {
                    Dealer.hit(Deck1.dealOneCard());
                    Deck1.removeTopCard();
                    System.out.println("\nDealer's hand is: " + Dealer.dealerHandToString());
                    System.out.print("Dealer's hand is worth: (" + Dealer.dealerHandValue() + ")\n");
                }

                if (Dealer.isBusted() == true && Player1.isBusted() == false) {
                    playerWins(Player1);
                } else if (Dealer.isBusted() == false && Player1.isBusted() == true) {
                    dealerWins(Player1);
                } else if (Dealer.isBusted() == true && Player1.isBusted() == true) {
                    System.out.println("Push! All bets are refunded.");
                    System.out.println(Player1.getName()+"'s balance is now "+Player1.getBalance());
                } else if (Dealer.dealerHandValue() > Player1.playerHandValue()) {
                    dealerWins(Player1);
                } else if (Player1.playerHandValue() == Dealer.dealerHandValue()) {
                    System.out.println("Push! All bets are refunded.");
                    System.out.println(Player1.getName()+"'s balance is now "+Player1.getBalance());
                } else {
                    playerWins(Player1);
                }
            }
            //Reset the game
            resetGame(Dealer,Player1,Deck1);

            //Check if player still has money to play.
            if (Player1.getBalance() <= 0){
                playing = false;
                System.out.println(Player1.getName()+" is out of money.");
                System.exit(0);
            }

            System.out.println("Would you like to quit? (1 = Play again, 2 = Quit)");
                if(sc.nextByte() == 2){
                    playing = false;
                    System.exit(0);
                }
            System.out.println("\n----------------------------------------------\n");
            wait2secs();
        }while (playing == true);

        //System.out.println(Player1.getName() + "'s balance is " + Player1.getBalance()); // get Balance for player
    }
    public static void wait2secs(){
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    public static void playerWins(Player Player1){
        System.out.println(Player1.getName() + " wins!\n");
        Player1.winAddMoney();
        System.out.println(Player1.getName()+"'s balance is now "+Player1.getBalance());
    }

    public static void dealerWins(Player Player1){
        System.out.println("Dealer wins!\n");
        Player1.loseReduceMoney();
        System.out.println(Player1.getName()+"'s balance is now "+Player1.getBalance());
    }

    public static void playerWinsBlackJack(Player Player1){
        System.out.println(Player1.getName() + " wins!\n");
        Player1.winAddMoneyBlackJack();
        System.out.println(Player1.getName()+"'s balance is now "+Player1.getBalance());
    }
    public static void resetGame(Dealer Dealer,Player Player1,Deck Deck1){
        Deck1.resetDeck();
        Player1.clearHand();
        Dealer.clearHand();
    }
}
