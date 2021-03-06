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

package teachingalgorithms.ui.windows.sorting;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import teachingalgorithms.io.SaveFile;
import teachingalgorithms.logic.sorting.SortingAlgorithm;
import teachingalgorithms.ui.components.LaTeXPanel;
import teachingalgorithms.ui.windows.AlgorithmWindowSubstructure;

/**
 * <p>
 *         The Class SelectionWindow. This class serves the graphical
 *         representation of the selectionsort algorithm in a gui frame. From
 *         here all important actions concerning the sorting algorithm can be
 *         performed and displayed.
 * </p>
 * @author Moritz Floeter
 */
public class SortingWindow extends AlgorithmWindowSubstructure implements ActionListener {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 7326001445728823589L;


    /**
     * The ssort.
     */
    private SortingAlgorithm algorithm;

    /**
     * Instantiates a new selectionsort window.
     *
     * @param parent the parent window @param bsort the BubbleSort instance
     */
    public SortingWindow(JFrame parent, SortingAlgorithm algorithm) {
        super(parent);
        this.setTitle(algorithm.getName());
        this.algorithm = algorithm;
        nextStep.addActionListener(this);
        undoStep.addActionListener(this);
        allSteps.addActionListener(this);
        reset.addActionListener(this);
        infoBtn.addActionListener(this);
        exportBtn.addActionListener(this);

        if (algorithm.getStepLimit() != -1 && algorithm.getStepLimit() < algorithm.getInputSize()) {
            allSteps.setText("<html> &nbsp; <br>Gehe " + algorithm.getStepLimit() + " Schritte <br> &nbsp; <html>");
        }


        this.addLastStepToProtocol();
    }


    private void addLastStepToProtocol() {

        this.add2protocol(new LaTeXPanel(algorithm.step2LaTeX(algorithm.getProtocolSize() - 1),
                algorithm.getStep(algorithm.getProtocolSize() - 1).getExplanation()));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(nextStep)) {
            boolean stepDone = algorithm.doStep();
            if (stepDone) {
                this.addLastStepToProtocol();
                this.validate();
                this.repaint();
                this.scroll2Bottom();
            } else {
                JOptionPane.showMessageDialog(this,
                        "<html>Fertig sortiert - keine weiteren Schritte m&ouml;glich</html>", "Fehler",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource().equals(undoStep)) {
            boolean stepUndone = algorithm.undoStep();
            if (stepUndone) {
                this.remFromProtocol();
                this.validate();
                this.repaint();
            } else {
                JOptionPane.showMessageDialog(this,
                        "<html>Es kann kein weiterer Schritt zur&uuml;ck gegangen werden...</html>", "Fehler",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource().equals(reset)) {
            this.algorithm.reset();
            while (this.protocolListPnl.getComponentCount() > 1) {
                this.remFromProtocol();
            }
            this.validate();
            this.repaint();
        } else if (e.getSource().equals(allSteps)) {
            int oldSize = algorithm.getProtocolSize();

            if (algorithm.getStepLimit() == -1 || algorithm.getStepLimit() >= algorithm.getInputSize()) {
                algorithm.doAllSteps();
            } else {
                boolean stepDone = true;
                for (int i = 0; i < algorithm.getStepLimit() && stepDone; i++) {
                    stepDone = algorithm.doStep();
                }
            }

            for (int i = oldSize; i < algorithm.getProtocolSize(); i++) {
                this.add2protocol(
                        new LaTeXPanel(algorithm.step2LaTeX(i), algorithm.getStep(i).getExplanation()));
            }

            this.validate();
            this.repaint();
        } else if (e.getSource().equals(exportBtn)) {
            if (this.algorithm.getInputSize() > 20) {
                int reply = JOptionPane.showConfirmDialog(this,
                        "<html> Sie versuchen das Sortierprotokoll eines grossen Arrays (>20 Elemente) zu exportieren. <br>"
                                + " Zwar ist es immer m&ouml;glich eine .tex-Datei zu erstellen, jedoch ist LaTeX ein Format,<br>"
                                + " das f&uuml;r die Ausgabe von Formaten gedacht ist, die sich gut auf Papiergr&ouml;sse bringen lassen. <br>"
                                + " Dadurch kann die .tex-Datei gegebenenfalls durch ihren LaTeX-Setzer nicht oder nur fehlerhaft gesetzt werden."
                                + "<br> <br> Wollen Sie die Datei dennoch erstellen? </html>",
                        "Arraygroesse problematisch", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    SaveFile.saveLaTeX(this, this.algorithm.protocol2LaTeX());
                }
            } else {
                SaveFile.saveLaTeX(this, this.algorithm.protocol2LaTeX());
            }
        } else if (e.getSource().equals(infoBtn)) {
            try {
                Desktop.getDesktop().open(new File("info/" + algorithm.getName().toLowerCase() + ".pdf"));
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(this,
                        "<html> Das &Ouml;ffnen der Zusatzinformation 'selectionsort.pdf' ist fehlgeschlagen. M&ouml;glicherweise <br>"
                                + "wurde die Datei durch den Nutzer gel&ouml;scht oder es ist kein geeignetes Anzeigeprogramm vorhanden.",
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}
