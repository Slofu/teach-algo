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

package teachingalgorithms.logic.sorting;

import teachingalgorithms.logic.sorting.step.QuickArray;
import teachingalgorithms.logic.sorting.step.QuickStep;

import java.util.ArrayList;

/**
 * <p>
 *         The class QuickSort.
 *         This class represents the implementation of the algorithm quicksort.
 * </p>
 * @author Moritz Floeter
 */
public class QuickSort extends SortingAlgorithm {

    private static final String NAME = "Quicksort";


    private static final int STEP_LIMIT = -1;

    /**
     * The protocol.
     */
    private ArrayList<QuickStep> protocol = new ArrayList<QuickStep>();

    /**
     * Instantiates a new instance of QuickSort.
     *
     * @param input the inputarray
     */
    public QuickSort(int[] input) {
        QuickArray start = new QuickArray(input);
        QuickStep firstStep = new QuickStep();
        findPartners(start);
        firstStep.getQarrays().add(start);
        firstStep
                .add2Explanation("Zum Beginn wird ein Quicksortschritt auf das gesamte Array ausgef&uuml;hrt. "
                        + "i startet eine Einheit links vom Beginn des Arrays (also hier auf -1) und j auf der letzten Position "
                        + "des Arrays. <br> ");

        explainFirstQarray(firstStep);

        protocol.add(firstStep);
    }

    /**
     * Tries to find two suitable partners for swapping inside the QuickArray
     * and sets the LeftEnd and RightEnd values according to the point where the
     * search stopped.
     *
     * @param qarray the qarray
     */
    private static void findPartners(QuickArray qarray) {
		/*
		 * The search for both partners begins one position next to the stored
		 * starting position.
		 */
        int leftPartner = qarray.getLeftStart() + 1;
        int rightPartner = qarray.getRightStart() - 1;

        int pivot = qarray.getArray()[qarray.getArray().length - 1];
		/*
		 * Search for an element that belongs on the right side of the pivot
		 * element. Search goes from left to right
		 */
        while (leftPartner < qarray.getArray().length - 1
                && qarray.getArray()[leftPartner] <= pivot) {
            leftPartner++;
        }

		/*
		 * Search for a suitable swap partner -> an element that belongs on the
		 * left side of the pivot. Search goes from right to left.
		 */
        while (rightPartner > 0 && qarray.getArray()[rightPartner] > pivot) {
            rightPartner--;
        }
        qarray.setLeftEnd(leftPartner);
        qarray.setRightEnd(rightPartner);

    }

    /**
     * Creates a Subarray. This function basically has the same purpose as the
     * substring function for strings. It creates a new array that is defined by
     * bounds (begin and end) inside of the original array
     *
     * @param original the original array
     * @param begin    the begin of the subarray
     * @param end      the end of the subarray
     * @return the int[]
     */
    private static int[] subArray(int[] original, int begin, int end) {
        if (begin > end) {
            int temp = begin;
            begin = end;
            end = temp;
        }

        int[] subArray = new int[end - begin + 1];
        System.arraycopy(original, begin, subArray, 0, end - begin + 1);
        return subArray;
    }

    /**
     * Generates explanation for the first qarray of the step. It makes sense to
     * call this function to generate an explanation as long as the array has
     * not been split in parts and therefore only has one qarray to be
     * explained.
     *
     * @param step the step
     */
    private void explainFirstQarray(QuickStep step) {
        step.add2Explanation("i l&auml;uft nach rechts, bis es eine Zahl gefunden wird, die gr&ouml;sser als das Pivotelement ist (Pivotelement = Zahl rechts im Array, durch Unterstreichung markiert) <br> "
                + "j l&auml;uft nach links, bis es eine Zahl gefunden wird, die kleiner als das Pivotelement ist. <br>");

        if (step.getQarrays().get(0).getLeftEnd() >= step.getQarrays().get(0)
                .getRightEnd()) {
            step.add2Explanation("Hier wurden keine Tauschpartner gefunden und das Pivotelement wird im n&auml;chsten Schritt mit"
                    + " der Zahl an der Position i vertauscht werden. Das Array wird anschließend rechts und links des Pivotelements"
                    + " aufgeteilt, sodass neue Teilarrays entstehen. <br>");
        } else {
            int left = step.getQarrays().get(0).getLeftEnd();
            int right = step.getQarrays().get(0).getRightEnd();
            step.add2Explanation(" Das Element an der Stelle i (hier:"
                    + step.getQarrays().get(0).getArray()[left]
                    + ") wird im n&auml;chsten Schritt mit dem Element an der Stelle j (hier:"
                    + step.getQarrays().get(0).getArray()[right]
                    + ") vertauscht werden <br>");
        }
    }

    /**
     * Gets the step.
     *
     * @param i the i
     * @return the step
     */
    public QuickStep getStep(int i) {
        return protocol.get(i);
    }

    /**
     * Gets the protocol size.
     *
     * @return the protocol size
     */
    public int getProtocolSize() {
        return protocol.size();
    }

    /**
     * Does all steps.
     */
    public void doAllSteps() {
        while (doStep())
            ;
    }

    /**
     * Undo step.
     *
     * @return true, if successful
     */
    public boolean undoStep() {
        boolean removed = false;
        if (protocol.size() > 1) {
            protocol.remove(protocol.size() - 1);
            removed = true;
        }
        return removed;
    }

    /**
     * Resets this instance of quicksort to the 1st step/beginning .
     */
    public void reset() {
        while (protocol.size() > 1) {
            protocol.remove(protocol.size() - 1);
        }
    }

    /**
     * Performs one step.
     *
     * @return true, if successful
     */
    public boolean doStep() {
        QuickStep prevStep = this.getStep(this.getProtocolSize() - 1);
        QuickStep nextStep = new QuickStep();
        String explainOneElement = "";
        String explainSwapPartners = "";
        String explainSwapPivot = "";

        boolean stepDone = false;

        if (prevStep != null && !prevStep.isTerminated()) {
            nextStep.add2Explanation("i l&auml;uft in jedem Teilarray von links nach rechts, bis"
                    + " ein Element gefunden wird, das gr&ouml;sser als das Pivotelement ist <br> "
                    + " j l&auml;uft in jedem Teilarray von rechts nach links, bis ein Element gefunden wird, das kleiner"
                    + " als das Pivotelement ist. <br> <br> <i>Hinweis: Pivotelement ist in dieser Variante"
                    + " des QuickSort immer die Zahl rechts im Teilarray (durch Unterstreichung markiert).</i> <br>");
            for (int i = 0; i < prevStep.getQarrays().size(); i++) {
                // If the array currently looked at only has one element, no
                // further action is required.
                if (prevStep.getQarrays().get(i).getArray().length == 1) {
                    nextStep.getQarrays().add(prevStep.getQarrays().get(i));
                    explainOneElement += "["
                            + prevStep.getQarrays().get(i).getArray()[0] + "] ";
                    // If this is not the case, the quicksort algorithm tries to
                    // swap values and splits the array if needed
                } else {

                    int[] oldArrayClone = prevStep.getQarrays().get(i)
                            .getArray().clone();
                    int rightEnd = prevStep.getQarrays().get(i).getRightEnd();
                    int leftEnd = prevStep.getQarrays().get(i).getLeftEnd();
                    boolean split = false;

					/*
                     * When the rightEnd and Left end have not "traded sides"
					 * but also aren't just on their default positions, that
					 * means that suitable swap partners have been found during
					 * the last step. Therefore those values get swapped.
					 *
					 * Trading sides means that the marker "rightEnd" that
					 * initially came from the right side of the array is now on
					 * the left side of the array (and vice versa for the marker
					 * "leftEnd").
					 */
                    if ((rightEnd > leftEnd) && leftEnd >= 0) {
                        explainSwapPartners = " Wenn i im VORIGEN Schritt nicht weiter nach rechts gelaufen ist, als j nach links - i und j  also nicht"
                                + " aneinander vorbei oder aufeinander gelaufen sind - bedeutet das, dass passende Tauschpartner gefunden wurden."
                                + " die Zahlen an den Positionen i und j im Array werden nun also in DIESEM Schritt vertauscht. <br> ";
                        int temp = oldArrayClone[leftEnd];
                        oldArrayClone[leftEnd] = oldArrayClone[rightEnd];
                        oldArrayClone[rightEnd] = temp;
						/*
						 * When the rightEnd and the leftEnd have "traded sides"
						 * this means that all elements are on the according
						 * side. The only thing left to do here is to swap the
						 * pivot element to the right position and to cut the
						 * array into three pieces : the left side // pivot //
						 * the right side
						 */
                    } else if ((rightEnd < leftEnd) || (rightEnd == 0)) {
                        explainSwapPivot = " Wenn i und j im VORIGEN Schritt aneinander vorbei oder aufeinander gelaufen sind, bedeutet das, dass "
                                + "im entsprechenden Teilarray keine weiteren Tauschpartner gefunden werden konnten. Somit muss in DIESEM Schritt lediglich das Pivotelement"
                                + " an die richtige Position gebracht werden und wird mit der Zahl an der Position i vertauscht. <br> "
                                + " Anschliessend wird das Array rechts und links des Pivotelements getrennt und es entstehen neue Teilarrays. <br> ";
                        split = true;
                        int temp = oldArrayClone[oldArrayClone.length - 1];
                        oldArrayClone[oldArrayClone.length - 1] = oldArrayClone[leftEnd];
                        oldArrayClone[leftEnd] = temp;

						/*
						 * The left part of the array (containing all elements
						 * that are smaller than the pivot) is added to the list
						 * first
						 */
                        if (leftEnd - 1 >= 0) {
                            int[] leftSideSplit = subArray(oldArrayClone, 0,
                                    leftEnd - 1);
                            QuickArray leftQuickArray = new QuickArray(
                                    leftSideSplit);
                            findPartners(leftQuickArray);
                            nextStep.getQarrays().add(leftQuickArray);
                        }

						/*
						 * The pivot-element is added to the list as an array
						 * with only the pivot as element
						 */
                        int[] pivotSplit = subArray(oldArrayClone, leftEnd,
                                leftEnd);
                        nextStep.getQarrays().add(new QuickArray(pivotSplit));

						/*
						 * Finally, the right part of the array (containing all
						 * elements that are larger than the pivot) is added to
						 * the list
						 */
                        if (leftEnd != oldArrayClone.length - 1) {
                            int[] rightSideSplit = subArray(oldArrayClone,
                                    leftEnd + 1, oldArrayClone.length - 1);
                            QuickArray rightQuickArray = new QuickArray(
                                    rightSideSplit);
                            findPartners(rightQuickArray);
                            nextStep.getQarrays().add(rightQuickArray);

                        }

                    }

                    if (!split) {
                        QuickArray nextQuickArray = new QuickArray(
                                oldArrayClone, leftEnd, rightEnd);
                        findPartners(nextQuickArray);
                        nextStep.getQarrays().add(nextQuickArray);
                    }
                }

            }
            nextStep.add2Explanation(explainSwapPartners);
            nextStep.add2Explanation(explainSwapPivot);
            if (!explainOneElement.isEmpty()) {
                explainOneElement = " Die Zahl(en) "
                        + explainOneElement
                        + " stand(en) bereits im letzten Schritt alleine in einem Array. Einelementige Teilarrays sind bereits sortiert"
                        + " und m&uuml;ssen nicht mehr bearbeitet werden. <br> ";
                nextStep.add2Explanation(explainOneElement);
            }
            protocol.add(nextStep);
            stepDone = true;
        }
        return stepDone;
    }

    /**
     * Protocol2 la te x.
     *
     * @return the string
     */
    public String protocol2LaTeX() {
        String retString = "";
        for (int i = 0; i < protocol.size(); i++) {
            retString += " " + this.step2LaTeX(i)
                    + "\n\\newline \n\\newline \n\\newline \n";
        }
        return retString;
    }


    /**
     * Generates LaTeX code representing the according step The type of the
     * matrix used can be defined in this function.
     *
     * @param chosenStepNumber the chosen step number
     * @return the string
     */
    public String step2LaTeX(int chosenStepNumber) {
        // String that will get returned at the end of this function
        String retString = "$\n\\begin{smallmatrix}\n &";

        QuickStep chosenStep = this.protocol.get(chosenStepNumber);

		/*
		 * If the chosenStep is not the first step, a reference to the previous
		 * step gets stored. If it is the first step, that reference points to
		 * null.
		 */
        QuickStep prevStep = null;
        if (chosenStepNumber > 0) {
            prevStep = this.protocol.get(chosenStepNumber - 1);
        }

        ArrayList<QuickArray> chosenQarrays = chosenStep.getQarrays();

		/*
		 * current position in the entire array. Because we start at the first
		 * position, this value is 0 in the beginning. (meaning all arrays of
		 * the chosenQarrays list put together)
		 */
        int position = 0;

        // loop over all QuickArrays in the chosenQarrays list
        for (int i = 0; i < chosenQarrays.size(); i++) {
            QuickArray chosenQarray = chosenQarrays.get(i);
            // loop over the int array that is stored in the QuickArray that is
            // currently looked at (chosenArray)
            for (int j = 0; j < chosenQarray.getArray().length; j++) {
				/*
				 * the number at the position j of the chosenArray gets stored
				 * as a String in order to later add it to the retString of this
				 * function
				 */
                String number = "" + chosenQarray.getArray()[j];

				/*
				 * color adjustment. If the value is different from the one
				 * stored in the previous step, it gets highlighted
				 */
                if (prevStep != null
                        && chosenStep.getValueAtPosition(position) != prevStep
                        .getValueAtPosition(position)) {
                    number = "\\textcolor{red}{" + number + "}";
                }

				/*
				 * underline adjustment. If the value is on the right of an
				 * Qarray and that array is bigger than one, it is a pivot
				 * element and therefore gets highlighted
				 */
                if (j == chosenQarray.getArray().length - 1
                        & (chosenQarrays.get(i).getArray().length > 1)) {
                    number = "\\underline{" + number + "}";
                }

                // after those adjustments, the number gets added to the
                // retString
                retString += number;

                // inserts LaTeX-seperator for columns and divides array with a
                // "|" if needed
                if (j < chosenQarray.getArray().length - 1) {
                    retString += " & ";
                } else if ((j == chosenQarray.getArray().length - 1)
                        && (i <= chosenQarrays.size() - 2)) {
                    retString = " " + retString + " | & ";
                }
                position++;
            }
        }

		/*
		 * after all numbers were inserted into the retString, a linebreak is
		 * inserted as we now will continue by writing the Markers under the
		 * according numbers
		 */
        retString += " \\\\ \n ";

        String[] startMarkers = chosenStep.getStartMarkerArray();

        for (int j = 0; j < startMarkers.length; j++) {
            if (startMarkers[j] != null) {
                retString += startMarkers[j];
            }
            if (j < startMarkers.length - 1) {
                retString += " & ";
            }
        }

        retString += " \\\\ \n ";

        String[] endMarkers = chosenStep.getEndMarkerArray();
        for (int j = 0; j < endMarkers.length; j++) {
            if (endMarkers[j] != null) {
                retString += endMarkers[j];
            }
            if (j < endMarkers.length - 1) {
                retString += " & ";
            }
        }

        return retString + "\n\\end{smallmatrix}\n$";
    }


    /**
     * Gets the size of the array that was passed to the constructor.
     *
     * @return the input size
     */
    public int getInputSize() {
        return protocol.get(0).getQarrays().get(0).getArray().length;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return NAME;
    }

    public int getStepLimit() {
        return STEP_LIMIT;
    }

}
