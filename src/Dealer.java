import java.util.ArrayList;
public class Dealer{

private ArrayList<Card> dealerHand = new ArrayList<Card>();
private boolean isBusted= false;

    public void addChosenCard(Card chosenCard){
        dealerHand.add(chosenCard);

    }
    public String dealerHandToString(){
        String returnedString = "";
        for (int i=0;i<dealerHand.size();i++){
            returnedString += dealerHand.get(i).toCardString();
            if (i != dealerHand.size()-1){
                returnedString += " & ";
            }
        }
        return returnedString;
    }
    public String dealerHandToStringInital(){
        return "The Dealer hand is "+dealerHand.get(0).toCardString()+" & ???";
    }

    public byte dealerHandValue(){
        byte handValue =0;
        byte numAces =0;
        for (int i=0;i<dealerHand.size();i++){
            switch (dealerHand.get(i).getCardRank()){
                case 1: numAces++;
                    if (handValue + 11 > 21){
                        handValue += 1;
                    }else{
                        handValue += 11;
                    }
                    break;
                case 11:
                case 12:
                case 13:
                    handValue += 10;
                    break;
                default: handValue += dealerHand.get(i).getCardRank();
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

    public void hit(Card chosenCard){
        dealerHand.add(chosenCard);
    }

    public boolean isBusted(){
        return isBusted;
    }

    public boolean hasBlackJack(){
        boolean hasAce = false;
        boolean hasFaceCard = false;

        for (int i=0;i<2;i++){
            switch (dealerHand.get(i).getCardRank()){
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
        dealerHand.clear();
        isBusted = false;
    }
}

