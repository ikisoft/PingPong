
import java.awt.Component;
import java.awt.event.*;

/**
 * Input Handler Käytetty javan valmista KeyListener -rajapintaa, jonka avulla
 * voidaan rekisteröidä käyttäjän näppäinpainalluksia. Suuri osa ohjelmakoodista
 * tässä luokassa on autogeneroitua.
 *
 * @author Max
 */

public class InputHandler implements KeyListener {

    boolean alreadyExecuted = false;

    boolean[] keys = new boolean[256];

    public InputHandler(Component c) {

        c.addKeyListener(this);
    }

    /**
     * Saa syötteenä KeyEvent -metodin määräämän kokonaisluvun, joka vastaa
     * jotain näppäintä.
     *
     * @param keyCode painetun näppäimen arvo
     * @return palauttaa näppäinkoodin arvon.
     */
    public boolean isKeyDown(int keyCode) {
        if (keyCode > 0 && keyCode < 256) {
            return keys[keyCode];
        }
        return false;
    }

    //ei käytössä, koodissa kokeiluja varten.
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() > 0 && e.getKeyCode() < 256) {
            keys[e.getKeyCode()] = true;
        }

    }

    //ei käytössä, koodissa kokeiluja varten.
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() > 0 && e.getKeyCode() < 256) {
            keys[e.getKeyCode()] = false;
        }

    }

    //ei käytössä, koodissa kokeiluja varten.
    public void keyTyped(KeyEvent e) {

    }

}
