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

package teachingalgorithms.export.latex.graph;

import teachingalgorithms.export.latex.LatexExporter;
import teachingalgorithms.logic.graph.algorithm.Kruskal;
import teachingalgorithms.logic.graph.protocol.StepByStepProtocol;
import teachingalgorithms.logic.graph.protocol.step.Step;
import teachingalgorithms.logic.graph.util.Edge;
import teachingalgorithms.logic.graph.util.Node;
import teachingalgorithms.ui.i18n.I18n;

import java.util.List;
import java.util.Objects;

/**
 * export format for kruskal.
 * @author Jonathan Lechner
 */
public class LatexKruskal extends LatexExporter{

    /**
     * base structure for Table.
     * <ul>
     *     <li>0,1,2,3 are TableHeader</li>
     *     <li>4 are the actual rows</li>
     *     <li>5 sum of used edges</li>
     * </ul>
     */
    private static final String laTex = "\\begin{tabular}{|c|c|l|c|}\n" +
            "\\hline" +
            " [0] & [1] & [2] & [3] \\\\\n" +
            "\\hline" +
            "[4]"+
            "\\end{tabular} \\\\\\\\" +
            "[5]";

    /**
     * Table row.
     * <ul>
     *     <li>0 Edge</li>
     *     <li>1 weight</li>
     *     <li>2 notes</li>
     *     <li>3 selected</li>
     * </ul>
     */
    private static final String laTexRow = " [0] & [1] & [2] & [3] \\\\\n" +
            "\\hline";

    private Integer sum;

    @Override
    public String toLatex(StepByStepProtocol protocol, I18n messages) {
        String toReturn = laTex;

        sum = 0;
        String rows = "";
        for (Step step : protocol) {
            rows = rows.concat(stepAsRow(step));
        }
        toReturn = toReturn.replace("[0]", messages.getMessage("exporter.latex.edge"));
        toReturn = toReturn.replace("[1]", messages.getMessage("exporter.latex.weight"));
        toReturn = toReturn.replace("[2]", messages.getMessage("exporter.latex.notes"));
        toReturn = toReturn.replace("[3]", messages.getMessage("exporter.latex.selected"));
        toReturn = toReturn.replace("[4]", rows);
        toReturn = toReturn.replace("[5]", "$\\sum$ " + sum.toString());

        return toReturn;
    }

    private String stepAsRow(Step step) {
        String toReturn = laTexRow;
        Edge currentEdge = (Edge) step.getAdditionalInformation(Kruskal.CURRENT_EDGE);
        List<List<Node>> visitedNodes = (List<List<Node>>) step.getAdditionalInformation(Kruskal.NODE_LIST);
        String nodeList = "";
        if (Objects.nonNull(visitedNodes)) {
            for (List<Node> list : visitedNodes) {
                nodeList = nodeList.concat("\\{");
                for (Node node : list) {
                    nodeList = nodeList.concat(node.getName() + ", ");
                }
                nodeList = nodeList.concat("\\}, ");
            }
            nodeList = nodeList.replaceAll(", \\}", "\\}");
        }


        toReturn = toReturn.replace("[0]", currentEdge.getFrom().getName() + "-" + currentEdge.getTo().getName());
        String weight = "";
        try {
            weight = currentEdge.getWeight().toString();
        } catch (NullPointerException exc) {

        }

        String selected = "";
        Object s = step.getAdditionalInformation(Kruskal.IS_SELECTED);
        if (s instanceof Boolean) {
            Boolean isSelected = (Boolean) step.getAdditionalInformation(Kruskal.IS_SELECTED);
            if (isSelected) {
                sum += currentEdge.getWeight();
            }
            selected = isSelected.toString();
        } else if (Objects.nonNull(s)){
            selected = s.toString();
        }

        toReturn = toReturn.replace("[1]", weight);
        toReturn = toReturn.replace("[2]", nodeList.length() > 2 ? nodeList.substring(0, nodeList.length() - 2) : "");
        toReturn = toReturn.replace("[3]", selected);

        return toReturn;
    }

}
