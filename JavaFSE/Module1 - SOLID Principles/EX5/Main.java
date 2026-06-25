package DecoratorPatternExample;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Scenario 1: Base Email Notification ---");
        Notifier emailNotifier = new EmailNotifier();
        emailNotifier.send("System alert!");

        System.out.println("\n--- Scenario 2: Email + SMS Notification ---");
        Notifier emailAndSMS = new SMSNotifierDecorator(new EmailNotifier());
        emailAndSMS.send("System alert!");

        System.out.println("\n--- Scenario 3: Email + SMS + Slack Notification ---");
        Notifier multiChannelNotifier = new SlackNotifierDecorator(
                                            new SMSNotifierDecorator(
                                                new EmailNotifier()
                                            )
                                        );
        multiChannelNotifier.send("Critical system alert!");
    }
}