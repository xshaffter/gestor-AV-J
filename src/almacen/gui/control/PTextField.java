package almacen.gui.control;

import javafx.geometry.Point2D;

public class PTextField extends javafx.scene.control.TextField {

        /**
         *
         * @param placeholder
         */
        public PTextField(final String placeholder) {
                setPromptText(placeholder);
                setMaxHeight(16);
                setMaxWidth(placeholder.length());
        }

        /**
         *
         * @param placeholder
         * @param length
         */
        public PTextField(final String placeholder, final int length) {
                setPromptText(placeholder);
                setMaxHeight(16);
                setMaxWidth(length);
        }

        public static Point2D getPoint(final PTextField a, final PTextField b) {
                return new Point2D(a.getDouble(), b.getDouble());
        }

        public PTextField(final String text, final String confirm) {
                super(text);
        }

        public PTextField() {
        }

        public int getInt() {
                try {
                        return Integer.parseInt(this.getText());
                } catch (NumberFormatException nfe) {
                        return 0;
                }
        }

        public double getDouble() {
                try {
                        return Double.parseDouble(this.getText());
                } catch (NumberFormatException nfe) {
                        return 0;
                }
        }

        public double getAbarrotes() {
                try {
                        final String ab = getFirst();
                        return Double.parseDouble(this.getText().replace(ab, ""));
                } catch (NumberFormatException nfe) {
                        return 0;
                }
        }

        public float getFloat() {
                try {
                        return Float.parseFloat(this.getText());
                } catch (NumberFormatException nfe) {
                        return 0;
                }
        }

        public long getLong() {
                try {
                        return Long.parseLong(this.getText());
                } catch (NumberFormatException nfe) {
                        return 0;
                }
        }

        public boolean getBoolean() {
                return Boolean.parseBoolean(this.getText());
        }

        public String getFirst() {
                String strPart = "";
                for (char car : getText().toCharArray()) {
                        if (car != ' ') {
                                strPart += car;
                        } else {
                                break;
                        }
                }
                return strPart;
        }

        @Override
        public String toString() {
                return getText();
        }
}
