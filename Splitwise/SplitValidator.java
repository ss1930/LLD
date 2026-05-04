package Splitwise;

import java.util.List;

public interface SplitValidator {
    boolean validateSplit(List<Split> splitss,double amount);
}
