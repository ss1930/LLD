package Splitwise;


public class SplitValidatorFactory {
    public static SplitValidator getSplitValidator(SplitType type){
        // return switch(type){
        //     case EQUAL -> new EqualSplitValidator();
        //     case UNEQUAL -> new UnequalSplitValidator();
        // };
        
        if (type==SplitType.UNEQUAL){
            return new UnequalSplitValidator();
        }
        return new EqualSplitValidator();
    }
}
