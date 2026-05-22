package bounties;

import bounties.driver.DemoDriver;

public class Main {
    public static void main(String[] args) {
        String inputFile = args.length > 0 ? args[0] : "input/sample_input.txt";
        System.out.println("Running with input file: " + inputFile);
        new DemoDriver().run(inputFile);
    }
}

