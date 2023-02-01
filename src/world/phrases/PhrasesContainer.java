package world.phrases;

import java.io.Serializable;

public interface PhrasesContainer extends Serializable {

    public void addSentence(String sentence);

    public String getRandomSentence();

}
