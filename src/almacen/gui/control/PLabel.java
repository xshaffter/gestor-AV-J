package almacen.gui.control;

import javafx.scene.control.Label;

/**
 *
 * @author Alfredo Antonio Martinez Armendariz
 */
public class PLabel extends Label {

        public PLabel(final String text) {
                super(text);
        }

        public PLabel() {

        }

        public int getInt() {
                try {
                        return Integer.parseInt(this.getText().replace("$", ""));
                } catch (NumberFormatException nfe) {
                        return 0;
                }
        }

        public double getDouble() {
                try {
                        return Double.parseDouble(this.getText().replace("$", ""));
                } catch (NumberFormatException nfe) {
                        return 0;
                }
        }

        public float getFloat() {
                try {
                        return Float.parseFloat(this.getText().replace("$", ""));
                } catch (NumberFormatException nfe) {
                        return 0;
                }
        }

        public long getLong() {
                try {
                        return Long.parseLong(this.getText().replace("$", ""));
                } catch (NumberFormatException nfe) {
                        return 0;
                }
        }

        @Override
        public String toString() {
                return getText();
        }

}
