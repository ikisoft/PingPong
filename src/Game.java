
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * Ping Pong -peli. Ohjelmointi II harjoitustyö. Ohjelmassa käytetty java awt
 * -kirjastoa ja java swing -kirjastoa.
 *
 * @author Max Ikäheimo
 */
public class Game extends JFrame {

    private static boolean isRunning = true;
    private static final int fps = 60;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 500;
    private static boolean hit;
    private static boolean hit_border;
    private static int pisteet;
    private static int y;
    private static int y2;
    private static int pallo_x;
    private static int pallo_y;

    private static Insets insets;
    private static BufferedImage backBuffer;
    private static InputHandler input;

    /**
     * Ohjelman päämetodi. Luo uuden peli -olion ja ajaa run -metodin.
     *
     * @param args
     */
    public static void main(String[] args) {
        Game peli = new Game();
        peli.run();
        //System.exit(0);    

    }

    /**
     * Metodi aloittaa pelin, ja pistää sen ikuiseen looppiin. While -silmukan
     * sisällä metodi kutsuu jokaisella läpikäynnillä update- ja draw metodeja.
     * Päivittää peliruudun 60 kertaa sekunnissa.
     */
    public void run() {
        
        initialize();
        startscreen();
        try {
            Thread.sleep(5000);
        } catch (Exception e){
            
        }
        isRunning = true;
        
        
        while (isRunning) {
            long time = System.currentTimeMillis();

            update();
            draw();

            //jokaisen framen viive - framen aika
            time = (1000 / fps) - (System.currentTimeMillis() - time);

            if (time > 0) {
                try {
                    Thread.sleep(time);
                } catch (Exception e) {
                }
            }
        }
        while (isRunning == false) {
            gameover();
            key_input();
        }

    }

    /**
     * Asettaa tarvittavia oleellisia arvoja ennen pelin käynnistystä
     */
    void initialize() {

        insets = getInsets();
        pisteet = 0;
        y = 200;
        y2 = 300;
        pallo_x = 465;
        pallo_y = 300;
        setTitle("Ping Pong");
        setSize(insets.left + WIDTH + insets.right,
                insets.top + HEIGHT + insets.bottom);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        backBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        input = new InputHandler(this);
    }

    /**
     * tarkistaa syötteet (näppäimistöpainallukset yms.) tekee haluttuja
     * toimintoja jokaisella päivityksellä
     */
    void update() {
        physics();
        key_input();

    }

    /**
     * piirtää kaiken näytölle käyttäen Graphics 2D kirjastoa.
     */
    void draw() {

        Graphics g = getGraphics();
        Graphics bbg = backBuffer.getGraphics();

        //fontti
        Font font = new Font("Arial", Font.PLAIN, 16);
        bbg.setFont(font);

        //tausta
        bbg.setColor(Color.BLACK);
        bbg.fillRect(0, 0, WIDTH, HEIGHT);

        //yläpalkki
        bbg.setColor(Color.WHITE);
        bbg.fillRect(y, 30, 100, 15);

        //alapalkki
        bbg.setColor(Color.WHITE);
        bbg.fillRect(0, 475, WIDTH, 15);

        //pallo
        bbg.setColor(Color.WHITE);
        bbg.fillOval(pallo_y, pallo_x, 15, 15);

        //tekstiä
        bbg.setColor(Color.RED);
        bbg.drawString("POINTS: " + pisteet, 10, 70);
        //bbg.drawString("Palkki y: " + y, 10, 90);
        //bbg.drawString("Palkki y2: " + y2, 10, 110);
        //bbg.drawString("Pallo x: " + pallo_x, 10, 130);
        //bbg.drawString("Pallo y: " + pallo_y, 10, 150);

        //draw       
        g.drawImage(backBuffer, insets.left, insets.top, this);
        //g.dispose();
    }

    /**
     * Pelin "fysiikkamoottori". Määrää ja luo pelin fysiikan, joiden mukaan
     * peliobjekti liikkuu koordi- naatistossa.
     */
    void physics() {
        //LOGIIKKA//
        //KORKEUS X
        if (pallo_x < 0) {
            isRunning = false;
        }
        if (pallo_x == 465) {
            hit = true;
        }
        if (hit) {
            pallo_x -= 5;
        }
        if (pallo_x == 40 && (pallo_y >= (y - 15) && pallo_y <= y2)) {
            hit = false;
            pisteet++;
        }
        if (hit == false) {
            pallo_x += 5;
        }
        //LEVEYS Y
        if (pallo_y == 0) {
            hit_border = true;
        }
        if (hit_border) {
            pallo_y += 5;
        }
        if (pallo_y == 575) {
            hit_border = false;
        }
        if (hit_border == false) {

            pallo_y -= 5;
        }

    }

    /**
     * Määrää, mitä tapahtuu, kun tiettyä näppäintä painetaan. Kutsuu
     * InputHandler-luokan metodeja.
     */
    void key_input() {
        //KEY INPUT           
        if (input.isKeyDown(KeyEvent.VK_RIGHT)) {
            if (y2 < 600) {
                y += 5;
                y2 += 5;
            }
        }
        if (input.isKeyDown(KeyEvent.VK_LEFT)) {
            if (y > 0) {
                y -= 5;
                y2 -= 5;
            }
        }
        if (input.isKeyDown(KeyEvent.VK_ENTER)) {
            run();

        }
        if (input.isKeyDown(KeyEvent.VK_ESCAPE)) {
            System.exit(0);

        }

    }

    /**
     * Näyttö, mikä näytetään pelaajan hävitessä pelin. Käyttää Graphics 2D
     * -kirjastoa.
     */
    void gameover() {

        Graphics g = getGraphics();
        Graphics bbg = backBuffer.getGraphics();
        Font font = new Font("Arial", Font.PLAIN, 50);
        Font font2 = new Font("Arial", Font.PLAIN, 25);

        //GAME OVER
        bbg.setColor(Color.RED);
        bbg.setFont(font2);
        bbg.drawString("POINTS: " + pisteet, 240, 190);
        bbg.setFont(font);
        bbg.drawString("GAME OVER", 150, 250);
        bbg.setFont(font2);
        bbg.drawString("(PRESS ENTER FOR A NEW GAME)", 90, 295);
        bbg.drawString("(PRESS ESCAPE TO QUIT)", 140, 320);

        g.drawImage(backBuffer, insets.left, insets.top, this);
        g.dispose();

    }
    
    void startscreen() {

        Graphics g = getGraphics();
        Graphics bbg = backBuffer.getGraphics();
        Font font = new Font("Arial", Font.PLAIN, 50);
        Font font2 = new Font("Arial", Font.PLAIN, 25);

        //GAME OVER
        bbg.setColor(Color.RED);
        bbg.setFont(font2);
        bbg.drawString("CURRENT HIGHSCORE: " + pisteet, 140, 390);
        bbg.setFont(font);
        bbg.drawString("PING PONG BY IKIS", 60, 150);
        bbg.setFont(font2);
        bbg.drawString("(PRESS ENTER TO START A NEW GAME)", 60, 245);
        bbg.drawString("(PRESS ESCAPE TO QUIT)", 140, 270);

        g.drawImage(backBuffer, insets.left, insets.top, this);
        g.dispose();

    }

}
