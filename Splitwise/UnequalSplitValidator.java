package Splitwise;

import java.util.List;

public class UnequalSplitValidator implements SplitValidator {

    @Override
    public boolean validateSplit(List<Split> splitss, double amount) {
        double sum=0;
        for(Split s: splitss){
            sum+=s.getAmount();
        }

        return (sum==amount);
    }
    
}
