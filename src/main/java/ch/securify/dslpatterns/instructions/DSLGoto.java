package ch.securify.dslpatterns.instructions;

import ch.securify.decompiler.Variable;
import ch.securify.dslpatterns.util.DSLLabel;
import ch.securify.dslpatterns.util.VariableDC;

import java.util.List;

/**
 * A goto DSL goto instruction
 */
public class DSLGoto extends AbstractDSLInstruction{

    Variable var;
    DSLLabel secondBranchLabel;

    /**
     * @param label the label at which the instuction is
     * @param var the variable on which the condition is applied
     * @param secondBranchLabel the label of the second branch
     */
    public DSLGoto(DSLLabel label, Variable var, DSLLabel secondBranchLabel) {
        super(label);
        this.var = var;
        this.secondBranchLabel = secondBranchLabel;
    }

    @Override
    public DSLGoto getCopy() {
        return new DSLGoto(getLabel(), var, secondBranchLabel);
    }

    @Override
    public List<DSLLabel> getAllLabels() {
        List<DSLLabel> labelsList = super.getAllLabels();
        if(DSLLabel.isValidLabel(secondBranchLabel))
            labelsList.add(secondBranchLabel);
        return labelsList;
    }

    /**
     * @return a list of all the variables contained in the instruction
     */
    @Override
    public List<Variable> getAllVars() {
        List<Variable> varsList = super.getAllVars();

        if(VariableDC.isValidVariable(var))
            varsList.add(var);

        return varsList;
    }

    @Override
    public String getStringRepresentation() {
        StringBuilder sb = new StringBuilder();
        sb.append("goto(");
        sb.append(label.getName());
        sb.append(" , ");
        sb.append(var.getName());
        sb.append(" , ");
        sb.append(secondBranchLabel.getName());
        sb.append(")");

        return sb.toString();
    }
}
