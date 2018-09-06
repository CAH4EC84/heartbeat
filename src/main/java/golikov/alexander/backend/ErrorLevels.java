package golikov.alexander.backend;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public enum ErrorLevels {
    NO {
        public InternetAddress[] getRecipients () {
            InternetAddress[] recipients;
            {
                try {
                    recipients = new InternetAddress[]{
                            new InternetAddress("it@medline.spb.ru")};
                    return  recipients;
                } catch (AddressException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    },

    TRACE{
        public InternetAddress[] getRecipients () {
            InternetAddress[] recipients;
            {
                try {
                    recipients = new InternetAddress[]{
                            new InternetAddress("it@medline.spb.ru")};
                    return  recipients;
                } catch (AddressException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    },

    INFO{
        public InternetAddress[] getRecipients () {
            InternetAddress[] recipients;
            {
                try {
                    recipients = new InternetAddress[]{
                            new InternetAddress("it@medline.spb.ru"),
                            new InternetAddress("ko@medline.spb.ru")};
                    return  recipients;
                } catch (AddressException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    },

    WARN {
        public InternetAddress[] getRecipients () {
            InternetAddress[] recipients;
            {
                try {
                    recipients = new InternetAddress[]{
                            new InternetAddress("89043303886@mail.ru"),
                            new InternetAddress("it@medline.spb.ru"),
                            new InternetAddress("ko@medline.spb.ru")};
                    return  recipients;
                } catch (AddressException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    },

    ERROR{
        public InternetAddress[] getRecipients () {
        InternetAddress[] recipients;
        {
            try {
                recipients = new InternetAddress[]{
                        new InternetAddress("89043303886@mail.ru"),
                        new InternetAddress("Login121288@gmail.com"),
                        new InternetAddress("G423xx@gmail.com"),
                        new InternetAddress("it@medline.spb.ru"),
                        new InternetAddress("ko@medline.spb.ru")};

                return  recipients;
            } catch (AddressException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    },
    FATAL{
        public InternetAddress[] getRecipients () {
            InternetAddress[] recipients;
            {
                try {
                    recipients = new InternetAddress[]{
                            new InternetAddress("89043303886@mail.ru"),
                            new InternetAddress("G423xx@gmail.com"),
                            new InternetAddress("Login121288@gmail.com"),
                            new InternetAddress("it@medline.spb.ru"),
                            new InternetAddress("ko@medline.spb.ru"),
                            new InternetAddress("krimchak@list.ru")};
                    return  recipients;
                } catch (AddressException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    };

    public abstract InternetAddress[] getRecipients ();

    public ErrorLevels getNextLevel(Integer i) {
        if (i<5) i++;

        switch (i) {
            case 0 : return NO;
            case 1 : return TRACE;
            case 2 : return INFO;
            case 3 : return WARN;
            case 4 : return ERROR;
            case 5 : return FATAL;
        }
        return NO;
    }
}
