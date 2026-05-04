package Splitwise;

import java.util.List;

public class EqualSplitValidator implements SplitValidator {

    @Override
    public boolean validateSplit(List<Split> splitss, double amount) {
        Split first=splitss.get(0);
        double splitAmount=first.getAmount();
        for(Split s: splitss){
            if(s.getAmount()!=splitAmount){
                return false;
            }
        }

        return ((splitAmount*splitss.size())==amount);
    }
    
}
