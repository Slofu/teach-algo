package teachingalgorithms.ui.components;

import java.awt.Font;
import javax.swing.JTextArea;

/**
 * @author Moritz Floeter
 *         <p>
 *         The Class MonoTextArea. Displays monospaced text.
 *         <p>
 *         --------------------------------------------------------------------
 *         This program is free software: you can redistribute it and/or modify
 *         it under the terms of the GNU General Public License as published by
 *         the Free Software Foundation, either version 3 of the License, or
 *         (at your option) any later version.
 *         <p>
 *         This program is distributed in the hope that it will be useful,
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *         GNU General Public License for more details.
 *         <p>
 *         You should have received a copy of the GNU General Public License
 *         along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class MonoTextArea extends JTextArea {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new mono text area.
     *
     * @param text the text
     */
    public MonoTextArea(String text) {
        super(text);
        Font courier = new Font("Courier", 0, 12);
        this.setFont(courier);
        this.setEditable(false);
        this.setLineWrap(true);
    }
}
