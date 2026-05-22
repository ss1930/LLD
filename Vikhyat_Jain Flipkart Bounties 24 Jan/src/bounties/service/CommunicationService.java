package bounties.service;

public class CommunicationService {
    public void notifyReporterRejected(String reporterEmail, String bugTitle) {
        System.out.println("[COMM] To: " + reporterEmail + " | Subject: Bug Report Rejected"
                + " | Message: Your report '" + bugTitle + "' has been rejected.");
    }

    public void notifyReporterBountyPaid(String reporterEmail, String bugTitle, Long bountyAmount) {
        System.out.println("[COMM] To: " + reporterEmail + " | Subject: Bounty Paid"
                + " | Message: Your report '" + bugTitle + "' has been paid. Amount: " + bountyAmount);
    }
}

