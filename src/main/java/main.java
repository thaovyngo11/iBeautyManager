import controller.ViewController;
import view.iBeautyFenster;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                iBeautyFenster view = new iBeautyFenster();
                ViewController controller = new ViewController(view);
                controller.iBeautyFenster_anzeigen();
            }
        });
    }

}
