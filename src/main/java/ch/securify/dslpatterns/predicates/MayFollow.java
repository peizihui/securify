package ch.securify.dslpatterns.predicates;

import ch.securify.dslpatterns.util.DSLLabel;

import java.util.ArrayList;
import java.util.List;

/**
 * The may follow DSL predicate
 */
public class MayFollow extends AbstractPredicate {
    private DSLLabel l1, l2;

    public MayFollow(DSLLabel l1, DSLLabel l2) {
        this.l1 = l1;
        this.l2 = l2;
    }

    public DSLLabel getL1() {
        return l1;
    }

    public DSLLabel getL2() {
        return l2;
    }

    @Override
    public String getStringRepresentation() {
        StringBuilder sb = new StringBuilder();
        sb.append("mayFollow(");
        sb.append(l1.getName());
        sb.append(" , ");
        sb.append(l2.getName());
        sb.append(")");

        return sb.toString();
    }

    @Override
    public List<DSLLabel> getLabels() {
        List<DSLLabel> labels = new ArrayList<>(2);
        labels.add(l1);
        labels.add(l2);

        return labels;
    }
}
