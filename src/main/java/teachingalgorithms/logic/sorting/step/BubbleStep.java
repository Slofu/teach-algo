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

package teachingalgorithms.logic.sorting.step;

// TODO: Auto-generated Javadoc

/**
 * <p>
 *         The Class Bubblestep. Serves the representation of one step for the bubblsort
 *         algorithm
 * </p>
 * @author Moritz Floeter
 */
public class BubbleStep extends SortingStep {

    /**
     * The Sortingmarker. This marks the position which is looked at in the
     * according step.
     */
    int sortingmarker;
    /**
     * The array.
     */
    private int[] array;
    /**
     * The memory. This stores information about whether numbers have been
     * swapped or not
     */
    private boolean memory;
    /**
     * The explanation.
     */
    private String explanation;


    /**
     * Instantiates a new sorting step.
     *
     * @param array the array @param sortingmarker the sortingmarker @param
     *              memory the memory @param explanation the explanation
     */
    public BubbleStep(int[] array, int sortingmarker, boolean memory, String explanation) {
        super();
        this.array = array;
        this.sortingmarker = sortingmarker;
        this.memory = memory;
        this.explanation = explanation;
    }

    /**
     * Gets the array.
     *
     * @return the array
     */
    public int[] getArray() {
        return array;
    }

    /**
     * Gets the markers.
     *
     * @return the markers
     */
    public int getSortingmarker() {
        return sortingmarker;
    }

    /**
     * Gets the memory.
     *
     * @return the memory
     */
    public boolean getMemory() {
        return memory;
    }

    /**
     * Gets the explanation.
     *
     * @return the explanation
     */
    public String getExplanation() {
        return explanation;
    }
}
