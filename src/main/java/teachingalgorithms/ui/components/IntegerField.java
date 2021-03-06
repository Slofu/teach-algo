/*
 *
 *     Copyright (C) 2015-2016  Moritz Flöter
 *     Copyright (C) 2016  Jonathan Lechner
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package teachingalgorithms.ui.components;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

import javax.swing.JTextField;

/**
 * <p>
 *         The Class IntegerField. This Class is basically an implementation of a
 *         TextField that only accepts integers (more specifically positive integers) in
 *         a certain range that is defined by a minimum and maximum passed to the
 *         constructor.
 * </p>
 * <p>
 *         Why not use a simple JSPinner instead? Amazingly those things perform
 *         differently under OSX and Windows (not tested on Linux but perhaps they have
 *         their own parallel life there too). While working perfectly fine on OSX in
 *         one situation, they might get you into trouble on Windows and vice versa. By
 *         writing my own class, I have the luxury of being under the impression that I
 *         am actually in control of what's happening. And if I am not sure whether the
 *         inputs are correct, I can always call the validateValue() method.
 * </p>
 * <p>
 *         And when a class like this is quickly written - why bother looking for an
 *         alternative (JFormattedTextField etc) that would also need evaluation and
 *         testing:)
 * </p>
 * @author Moritz Floeter
 */
public class IntegerField extends JTextField implements FocusListener {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 7993439468559754401L;

    /**
     * The max.
     */
    private int max;

    /**
     * The min.
     */
    private int min;

    /**
     * The int listen.
     */
    private IntegerListener intListen = new IntegerListener();

    /**
     * Instantiates a new integer field.
     *
     * @param min the minimum value @param max the maximum value
     */
    public IntegerField(int min, int max) {
        super();
        if (max < min) {
            int temp = max;
            max = min;
            min = temp;
        }

        this.addFocusListener(this);
        this.addKeyListener(intListen);
        this.max = max;
        this.min = min;
    }

    /**
     * Gets the int value of the field.
     *
     * @return the int value
     */
    public Integer getInt() {
        Integer toReturn = null;
        try {
            toReturn = Integer.parseInt(this.getText());
        } catch (NumberFormatException exception) {
            // do nothing
        }
        return toReturn;
    }

    /**
     * Validate value.
     */
    public void validateValue() {
        String text = this.getText();
        while (text.length() > 1 && text.charAt(0) == '0') {
            text = text.substring(1);
        }
        this.setText(text);
        Integer number = null;
        try {
            number = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            // do nothing
        }

        if (Objects.nonNull(number)) {
            if (number < min) {
                this.setText("" + min);
            } else if (number > max) {
                this.setText("" + max);
            } else {
                this.setText("" + number);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.JTextComponent#setText(java.lang.String)
     */
    public void setText(String text) {
        try {
            Integer.parseInt(text);
            super.setText(text);
        } catch (NumberFormatException exception) {

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
     */
    @Override
    public void focusGained(FocusEvent e) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
     */
    @Override
    public void focusLost(FocusEvent e) {
        // if focus is lost, the values should get validated
        this.validateValue();

    }

    /**
     * IntegerListener is a class used for the input of numbers. The class that
     * is responsible for processing an input event that only should allow
     * numbers. When the integer event occurs, the appropriate method is
     * invoked.
     */
    private class IntegerListener implements KeyListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
         */
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!(((c >= '0') && (c <= '9')) || (c == KeyEvent.VK_DELETE))) {
                getToolkit().beep();
                e.consume();
            }

        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
         */
        @Override
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
         */
        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

        }
    }

}