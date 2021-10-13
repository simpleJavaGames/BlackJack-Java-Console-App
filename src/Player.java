import java.util.ArrayList;

public class Player{
    private int balance;
    private String name;
    private ArrayList<Card> playerHand = new ArrayList<Card>();
    private boolean isBusted = false;
    private int playerBetAmount =0;

    Player(){
        balance = 500;
        name = "Jared";
    }
    Player(int balance,String name){
        this.balance = balance;
        this.name = name;
    }

    public int getBalance(){
        return balance;
    }

    public void winAddMoney(){
        balance += playerBetAmount;
    }

    public void winAddMoneyBlackJack(){
        balance += (playerBetAmount * 1.5);
    }

    public void loseReduceMoney(){
        balance -= playerBetAmount;
    }

    public String getName(){
        return name;
    }

    public void addChosenCard(Card chosenCard){
        playerHand.add(chosenCard);
    }

    public String playerHandToString(){
        String returnedString = "";
        for (int i=0;i<playerHand.size();i++){
            returnedString += playerHand.get(i).toCardString();
            if (i != playerHand.size()-1){
                returnedString += " & ";
            }
        }
        return returnedString;
    }

    public void hit(Card chosenCard){
        playerHand.add(chosenCard);
    }

    public byte playerHandValue(){
        byte handValue =0;
        byte numAces =0;
        for (int i=0;i<playerHand.size();i++){
            switch (playerHand.get(i).getCardRank()){
                case 1:
                        if (handValue + 11 > 21){
                            handValue += 1;
                        }else{
                            handValue += 11;
                            numAces++;
                        }
                    break;
                case 11:
                case 12:
                case 13:
                    handValue += 10;
                    break;
                default: handValue += playerHand.get(i).getCardRank();
                    break;
            }
            if (handValue > 21){
                if(numAces > 0){
                    numAces--;
                    handValue -= 10;
                }else {
                    isBusted = true;
                }
            }
        }
        return handValue;
    }

    public boolean isBusted(){
        return isBusted;
    }

    public boolean hasBlackJack(){
        boolean hasAce = false;
        boolean hasFaceCard = false;

        for (int i=0;i<2;i++){
            switch (playerHand.get(i).getCardRank()){
                case 1: hasAce = true;
                    break;
                case 10:
                case 11:
                case 12:
                case 13:
                    hasFaceCard = true;
                    break;
            }
        }
        if (hasAce == true & hasFaceCard == true){
            return true;
        }else{
            return false;
        }
    }
    public void clearHand(){
        playerHand.clear();
        isBusted = false;
    }
    public int getPlayerBetAmount(){
        return playerBetAmount;
    }
    public void setPlayerBetAmount(int playerBetAmount){
        this.playerBetAmount = playerBetAmount;
    }
}
