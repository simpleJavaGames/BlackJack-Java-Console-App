import java.util.ArrayList;
import java.util.*;
public class Deck{
    private static int counter;
    protected ArrayList<Card> deckOfCards = new ArrayList<Card>();
    private ArrayList<Card> cardsOut = new ArrayList<Card>();


    public Deck() {

        for(byte i=0;i<4;i++){                        // create the deck and fill it with all the cards
            for(byte j=1;j<14;j++){
                deckOfCards.add(new Card(i,j));
            }
        }
        shuffleDeck();
    }

    public void shuffleDeck(){
        Collections.shuffle(deckOfCards);
        // shuffle deck
    }

    public Card dealOneCard(){
        int chosenCard = counter; // Deal one card out
        counter++;                            // there is a bug where you can get the same card twice. this is because we're removing the card before sending the new one.
        cardsOut.add(deckOfCards.get(chosenCard));
        return deckOfCards.get(chosenCard);
    }
    public void removeTopCard(){
        deckOfCards.remove(counter);
    }
    public int deckOfCardsLength(){
        return deckOfCards.size();
    }

    //This will reset the deck by adding all of the cards out back into the deck.
    public void resetDeck(){
        counter =0;
        deckOfCards.addAll(cardsOut);
        cardsOut.clear();
        shuffleDeck();
    }

}
