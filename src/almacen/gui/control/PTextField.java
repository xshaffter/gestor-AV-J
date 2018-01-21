package almacen.gui.control;


public class PTextField extends javafx.scene.control.TextField {

    /**
     * @param placeholder texto que se quiere colocar
     */
    public PTextField(final String placeholder) {
        setPromptText(placeholder);
        setMaxHeight(16);
    }

    @SuppressWarnings("unused")
    public PTextField(final String text, final boolean confirm) {
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

    @SuppressWarnings("unused")
    public float getFloat() {
        try {
            return Float.parseFloat(this.getText());
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    @SuppressWarnings("unused")
    public long getLong() {
        try {
            return Long.parseLong(this.getText());
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    @SuppressWarnings("unused")
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
