package mg.yoan.libtd.mail;

import jakarta.mail.internet.InternetAddress;
import java.io.File;
import java.util.List;
import mg.yoan.libtd.PojaGenerated;

@PojaGenerated
public record Email(
    InternetAddress to,
    List<InternetAddress> cc,
    List<InternetAddress> bcc,
    String subject,
    String htmlBody,
    List<File> attachments) {}
