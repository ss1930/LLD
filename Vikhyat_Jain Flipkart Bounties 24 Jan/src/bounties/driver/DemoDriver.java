package bounties.driver;

import bounties.exception.ValidationException;
import bounties.model.BugReport;
import bounties.model.BugStatus;
import bounties.model.Role;
import bounties.model.Severity;
import bounties.model.User;
import bounties.service.AuthService;
import bounties.service.BugReportService;
import bounties.service.CommunicationService;
import bounties.service.UserService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoDriver {
    private final UserService userService = new UserService();
    private final AuthService authService = new AuthService();
    private final CommunicationService communicationService = new CommunicationService();
    private final BugReportService bugReportService = new BugReportService(userService, communicationService);

    public void run(String inputFilePath) {
        int passed = 0;
        int failed = 0;
        boolean anyExpectation = false;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                lineNo++;
                String trimmed = line.trim();
                if (trimmed.isEmpty()) continue;

                Expectation exp = Expectation.parse(trimmed);
                if (exp.type != ExpectationType.NONE) anyExpectation = true;

                try {
                    execute(exp.commandLine);
                    if (exp.type == ExpectationType.OK) {
                        passed++;
                        System.out.println("[PASS] line " + lineNo);
                    } else if (exp.type == ExpectationType.ERROR) {
                        failed++;
                        System.out.println("[FAIL] line " + lineNo);
                    }
                } catch (RuntimeException e) {
                    if (exp.type == ExpectationType.NONE) {
                        System.out.println("[ERROR] line " + lineNo);
                        System.out.println("  cmd: " + exp.commandLine);
                        System.out.println("  ex:  " + e.getClass().getSimpleName() + " - " + e.getMessage());
                    } else if (exp.type == ExpectationType.ERROR) {
                        String actual = e.getClass().getSimpleName();
                        if (actual.equals(exp.expectedExceptionSimpleName)) {
                            passed++;
                            System.out.println("[PASS] line " + lineNo);
                        } else {
                            failed++;
                            System.out.println("[FAIL] line " + lineNo);
                            System.out.println("  expected: " + exp.expectedExceptionSimpleName);
                            System.out.println("  actual:   " + actual + " - " + e.getMessage());
                        }
                    } else {
                        failed++;
                        System.out.println("[FAIL] line " + lineNo);
                        System.out.println("  expected: OK");
                        System.out.println("  actual:   " + e.getClass().getSimpleName() + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read input file: " + inputFilePath + " (" + e.getMessage() + ")");
        } finally {
            if (anyExpectation) {
                System.out.println("passed: " + passed);
                System.out.println("failed: " + failed);
            }
        }
    }

    private void execute(String line) {
        List<String> tokens = tokenize(line);
        if (tokens.isEmpty()) return;

        String cmd = tokens.get(0);

        if (cmd.equalsIgnoreCase("preloadUsers")) {
            handlePreloadUsers(tokens.subList(1, tokens.size()));
            return;
        }

        if (cmd.equalsIgnoreCase("addUser")) {
            Map<String, String> args = parseKeyValues(tokens.subList(1, tokens.size()));
            userService.addUser(new User(
                    require(args, "user"),
                    require(args, "email"),
                    Role.fromString(require(args, "role"))
            ));
            return;
        }

        if (cmd.equalsIgnoreCase("updateUser")) {
            Map<String, String> args = parseKeyValues(tokens.subList(1, tokens.size()));
            String user = require(args, "user");
            String email = args.get("email");
            Role role = args.containsKey("role") ? Role.fromString(args.get("role")) : null;
            userService.updateUser(user, email, role);
            return;
        }

        if (cmd.equalsIgnoreCase("login")) {
            if (tokens.size() < 2) throw new ValidationException("Usage: login <userName>");
            String userName = tokens.get(1);
            authService.login(userService.getByName(userName));
            return;
        }

        if (cmd.equalsIgnoreCase("logout")) {
            authService.logout();
            return;
        }

        User actor = authService.requireLoggedInUser();

        if (cmd.equalsIgnoreCase("reportBug")) {
            Map<String, String> args = parseKeyValues(tokens.subList(1, tokens.size()));
            bugReportService.reportBug(
                    require(args, "title"),
                    require(args, "description"),
                    Severity.fromString(require(args, "severity")),
                    require(args, "reporterEmail")
            );
            return;
        }

        if (cmd.equalsIgnoreCase("assignBugReport")) {
            Map<String, String> args = parseKeyValues(tokens.subList(1, tokens.size()));
            bugReportService.assign(require(args, "title"), require(args, "user"));
            return;
        }

        if (cmd.equalsIgnoreCase("updateBugStatus")) {
            Map<String, String> args = parseKeyValues(tokens.subList(1, tokens.size()));
            bugReportService.updateStatus(actor, require(args, "title"), BugStatus.fromString(require(args, "status")));
            return;
        }

        if (cmd.equalsIgnoreCase("updateBugReport")) {
            Map<String, String> args = parseKeyValues(tokens.subList(1, tokens.size()));
            String title = require(args, "title");
            String description = args.get("description");
            Severity severity = args.containsKey("severity") ? Severity.fromString(args.get("severity")) : null;
            String reporterEmail = args.get("reporterEmail");
            Long bounty = args.containsKey("bountyAmount") ? Long.parseLong(args.get("bountyAmount")) : null;
            bugReportService.updateBugReport(actor, title, description, severity, bounty, reporterEmail);
            return;
        }

        if (cmd.equalsIgnoreCase("addComment")) {
            Map<String, String> args = parseKeyValues(tokens.subList(1, tokens.size()));
            bugReportService.addComment(actor, require(args, "title"), require(args, "text"));
            return;
        }

        if (cmd.equalsIgnoreCase("logTime")) {
            Map<String, String> args = parseKeyValues(tokens.subList(1, tokens.size()));
            String title = require(args, "title");
            int minutes = Integer.parseInt(require(args, "minutes"));
            String note = args.get("note");
            bugReportService.logTime(actor, title, minutes, note);
            return;
        }

        if (cmd.equalsIgnoreCase("deleteBugReport")) {
            Map<String, String> args = parseKeyValues(tokens.subList(1, tokens.size()));
            bugReportService.deleteBugReport(actor, require(args, "title"));
            return;
        }

        if (cmd.equalsIgnoreCase("listAllBugReports")) {
            List<BugReport> list = bugReportService.listAll();
            for (BugReport b : list) System.out.println(b);
            return;
        }

        if (cmd.equalsIgnoreCase("listAssignedReports")) {
            List<BugReport> list = bugReportService.listAssignedTo(actor.getName());
            for (BugReport b : list) System.out.println(b);
            return;
        }

        if (cmd.equalsIgnoreCase("listAssignedAndCompletedReports")) {
            List<BugReport> list = bugReportService.listAssignedToAndCompleted(actor.getName());
            for (BugReport b : list) System.out.println(b);
            return;
        }

        if (cmd.equalsIgnoreCase("listAssignedAndIncompleteReports")) {
            List<BugReport> list = bugReportService.listAssignedToAndIncomplete(actor.getName());
            for (BugReport b : list) System.out.println(b);
            return;
        }

        if (cmd.equalsIgnoreCase("viewBugReportDetails")) {
            Map<String, String> args = parseKeyValues(tokens.subList(1, tokens.size()));
            BugReport br = bugReportService.viewDetails(require(args, "title"));
            System.out.println(br);
            return;
        }

        throw new ValidationException("Unknown command: " + cmd);
    }

    private void handlePreloadUsers(List<String> tokens) {
        String joined = String.join(" ", tokens);
        String[] blocks = joined.split(";");
        List<User> users = new ArrayList<>();
        for (String b : blocks) {
            String t = b.trim();
            if (t.isEmpty()) continue;
            Map<String, String> kv = parseKeyValues(tokenize(t));
            users.add(new User(
                    require(kv, "user"),
                    require(kv, "email"),
                    Role.fromString(require(kv, "role"))
            ));
        }
        userService.preloadUsers(users);
    }

    private static String require(Map<String, String> map, String key) {
        String v = map.get(key);
        if (v == null || v.trim().isEmpty()) throw new ValidationException("Missing required field: " + key);
        return v;
    }

    private static Map<String, String> parseKeyValues(List<String> tokens) {
        Map<String, String> map = new HashMap<>();
        for (String t : tokens) {
            int idx = t.indexOf('=');
            if (idx <= 0) throw new ValidationException("Expected key=value, got: " + t);
            String k = t.substring(0, idx).trim();
            String v = t.substring(idx + 1).trim();
            map.put(k, v);
        }
        return map;
    }

    private static List<String> tokenize(String line) {
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '"') {
                inQuotes = !inQuotes;
                continue;
            }
            if (!inQuotes && Character.isWhitespace(ch)) {
                if (cur.length() > 0) {
                    out.add(cur.toString());
                    cur.setLength(0);
                }
                continue;
            }
            cur.append(ch);
        }
        if (inQuotes) throw new ValidationException("Unclosed quotes");
        if (cur.length() > 0) out.add(cur.toString());
        return out;
    }

    private enum ExpectationType { NONE, OK, ERROR }

    private static class Expectation {
        private final ExpectationType type;
        private final String expectedExceptionSimpleName;
        private final String commandLine;

        private Expectation(ExpectationType type, String expectedExceptionSimpleName, String commandLine) {
            this.type = type;
            this.expectedExceptionSimpleName = expectedExceptionSimpleName;
            this.commandLine = commandLine;
        }

        public static Expectation parse(String rawLine) {
            String t = rawLine.trim();
            if (t.startsWith("EXPECT_OK ")) {
                return new Expectation(ExpectationType.OK, null, t.substring("EXPECT_OK ".length()).trim());
            }
            if (t.startsWith("EXPECT_ERROR ")) {
                String rest = t.substring("EXPECT_ERROR ".length()).trim();
                List<String> parts = tokenize(rest);
                if (parts.size() < 2) {
                    throw new ValidationException("Usage: EXPECT_ERROR <ExceptionSimpleName> <command...>");
                }
                String expected = parts.get(0);
                String cmd = rest.substring(expected.length()).trim();
                return new Expectation(ExpectationType.ERROR, expected, cmd);
            }
            return new Expectation(ExpectationType.NONE, null, rawLine);
        }
    }
}

