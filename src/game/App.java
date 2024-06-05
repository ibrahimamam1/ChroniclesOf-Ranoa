package game;

import javax.swing.JFrame;

public class App {

    public static JFrame window = new JFrame();
    public static void main(String[] args) throws Exception {
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Chronicles Of Ranoa");
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
