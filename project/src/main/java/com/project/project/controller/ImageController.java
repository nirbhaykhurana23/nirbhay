package com.project.project.controller;

import com.project.project.Exceptions.BadRequestException;
import com.project.project.Exceptions.ResourceNotFoundException;
import com.project.project.entities.Product;
import com.project.project.entities.ProductVariation;
import com.project.project.repositories.ProductRepository;
import com.project.project.repositories.ProductVariationRepository;
import com.project.project.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
public class ImageController {

    @Autowired
    private UserDaoService userDaoService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    private static String UPLOADED_FOLDER = "/home/nirbhay/git-repo/project/images";

    @PostMapping("/profile/uploadImage/{userId}")
    public ResponseEntity<Object> uploadProfileImage(@RequestParam("file") MultipartFile file,
                                                     @PathVariable Integer userId) throws IOException {

        if (file.isEmpty()) {
            throw new ResourceNotFoundException("Upload Image");
        }

        try {
            String fileExtension = null;
            byte[] bytes = file.getBytes();
            fileExtension = file.getOriginalFilename().split("\\.")[1];

            if (fileExtension.equals("jpeg") || fileExtension.equals("jpg")
                    || fileExtension.equals("png") || fileExtension.equals("bmp")) {
                Path path = Paths.get(UPLOADED_FOLDER + "/users/" + userId + "." + fileExtension);
                Files.write(path, bytes);

                return new ResponseEntity<>("Uploaded successfully", HttpStatus.CREATED);
            } else {
                throw new BadRequestException("Invalid file format, Kindly use .jpg, .jpeg, png, .bmp format" +
                        " for uploading image");
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @PostMapping("/product/uploadImage/{sellerid}/{pid}")
    public ResponseEntity<Object> uploadProductImage(@RequestParam("file") MultipartFile file,
                                                     @PathVariable Integer sellerid,
                                                     @PathVariable Long pid) throws IOException {

        if (file.isEmpty()) {
            throw new ResourceNotFoundException("Upload Image");
        }

        Optional<Product> optionalProduct = productRepository.findById(pid);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            Integer sid = product.getSeller().getUser_id();

            if (sid.equals(sellerid)) {
                try {
                    String fileExtension = null;
                    byte[] bytes = file.getBytes();
                    fileExtension = file.getOriginalFilename().split("\\.")[1];

                    if (fileExtension.equals("jpeg") || fileExtension.equals("jpg")
                            || fileExtension.equals("png") || fileExtension.equals("bmp")) {


                        File newPath = new File(UPLOADED_FOLDER + "/products/" + pid);
                        if (!newPath.exists()) {
                            if (newPath.mkdirs()) {
                                System.out.println("********** New Directory Created for Product **********");
                            } else {
                                System.out.println("********** Failed to create directory for Product **********");
                            }
                        }

                        Path path = Paths.get(newPath.toString() + "/" + pid + "." + fileExtension);
                        Files.write(path, bytes);

                        return new ResponseEntity<>("Uploaded successfully", HttpStatus.CREATED);

                    } else {
                        throw new BadRequestException("Invalid file format, Kindly use .jpg, .jpeg, png, .bmp format" +
                                " for uploading image");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }

            } else {
                    throw new BadRequestException("Product not associated to Logged in Seller");
                }

        } else {
            throw new ResourceNotFoundException("Product not found with requested ID");
        }
    }

    @PostMapping("/product-variant/uploadImage/{sellerid}/{vid}")
    public ResponseEntity<Object> uploadProductVariationImage(@RequestParam("file") MultipartFile file,
                                                              @PathVariable Integer sellerid,
                                                              @PathVariable Integer vid) throws IOException {

        if (file.isEmpty()) {
            throw new ResourceNotFoundException("Upload Image");
        }

        Optional<ProductVariation> optionalProductVariation = productVariationRepository.findById(vid);
        if (optionalProductVariation.isPresent()) {

            ProductVariation productVariation = optionalProductVariation.get();
            Long pid = productVariation.getProduct().getId();
            Integer sid = productVariation.getProduct().getSeller().getUser_id();

            Optional<Product> optionalProduct = productRepository.findById(pid);
            if (optionalProduct.isPresent()) {

                if (sid.equals(sellerid)) {

                    try {
                        String fileExtension = null;
                        byte[] bytes = file.getBytes();
                        fileExtension = file.getOriginalFilename().split("\\.")[1];

                        if (fileExtension.equals("jpeg") || fileExtension.equals("jpg")
                                || fileExtension.equals("png") || fileExtension.equals("bmp")) {

                            File newPath = new File(UPLOADED_FOLDER + "/products/" + pid + "/variations");
                            if (!newPath.exists()) {
                                if (newPath.mkdirs()) {
                                    System.out.println("********** New Directory Created for Product-Variation **********");
                                } else {
                                    System.out.println("********** Failed to create directory for Product-Variation **********");
                                }
                            }

                            Path path = Paths.get(newPath.toString() + "/" + vid + "." + fileExtension);
                            Files.write(path, bytes);

                            return new ResponseEntity<>("Uploaded successfully", HttpStatus.CREATED);

                        } else {
                            throw new BadRequestException("Invalid file format, Kindly use .jpg, .jpeg, png, .bmp format" +
                                        " for uploading image");
                            }

                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }
                } else {
                    throw new BadRequestException("Product not associated to Logged in Seller");
                }

            } else {
                throw new ResourceNotFoundException("Product not found with requested ID");
            }
        } else {
            throw new ResourceNotFoundException("Invalid Product-Variation Id");
        }
    }

}
