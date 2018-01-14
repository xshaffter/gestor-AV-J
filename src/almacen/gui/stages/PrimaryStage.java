package almacen.gui.stages;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author HP
 */
public class PrimaryStage extends Stage {

        @SuppressWarnings({"LeakingThisInConstructor", "OverridableMethodCallInConstructor"})
        public PrimaryStage(final BorderPane pane) {
                final Scene root = new Scene(pane);

                setScene(root);
                setMaximized(true);
        }
}
