package com.project.project.services;
import com.project.project.entities.Seller;
import com.project.project.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import java.util.Iterator;

@Service
public class DailyOrderStatusEmailService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    public void saveEmailReport() {

        Iterable<Seller> sellers = sellerRepository.findAll();
        Iterator<Seller> sellerIterator = sellers.iterator();
        while (sellerIterator.hasNext()) {
            Seller seller = sellerIterator.next();
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(seller.getEmail());
            mailMessage.setSubject("Order Updates");
            mailMessage.setText("Your order has been cancelled");

            emailSenderService.sendEmail(mailMessage);

            System.out.println("********** Email sent to all sellers **********");

        }
    }
}
