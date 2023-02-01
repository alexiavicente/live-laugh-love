package world.phrases;

import world.Room;
import world.phrases.PhrasesContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhrasesList implements PhrasesContainer {

    private List<String> list;

    public PhrasesList() {
        list = new ArrayList<>();
    }

    @Override
    public void addSentence(String sentence) {
        list.add(sentence);
    }

    @Override
    public String getRandomSentence() {
        Random r = new Random();
        return list.get(r.nextInt(list.size()));
    }
}
