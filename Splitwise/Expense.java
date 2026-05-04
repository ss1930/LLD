package Splitwise;

import java.util.List;

public class Expense {
    private String id;
    private String desc;
    private double amount;
    private User paidBy;
    private List<Split>splits;
    private SplitType splitType;

    public Expense(String id, double amount, String desc,
                   User paidByUser, SplitType splitType, List<Split> splitDetails) {

        this.id = id;
        this.amount = amount;
        this.desc = desc;
        this.paidBy = paidByUser;
        this.splitType = splitType;
        this.splits.addAll(splitDetails);

    }

    public String getId() {
        return this.id;
    }

    public SplitType getType() {
        return splitType;
    }

    public double getAmount() {
        return amount;
    }

    public List<Split> getSplits() {
        return splits;
    }

}
