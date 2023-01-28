package org.assesment.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assesment.model.Address;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class JsonServiceImpl {

    public static void main(String[] args) throws IOException {
        String json = readFileFromResource("/Users/utkarshgoswami/Documents/JavaProjects/Assesment/addresses.json");
        List<Address> addresses = fetchAddressList(json);
        String prettyAddress=prettyPrintAddress(addresses.get(0));
        prettyPrintListOfAddress(addresses);
    }


    public static List<Address> fetchAddressList(String json) {
        Address[] addresses = null;
        try {
            addresses = new ObjectMapper().readValue(json, Address[].class);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return Arrays.asList(addresses);
    }

    public static String readFileFromResource(String filename) {
        String json = "";
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                json = json + myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. while reading file");
            e.printStackTrace();
        }
        return json;
    }
    public static String prettyPrintAddress(Address address) {
        StringBuilder sb = new StringBuilder();
        if (Objects.nonNull(address)) {
            if (Objects.nonNull(address.getType())) {
                sb.append(address.getType().getName());
                sb.append(" - ");
            }
            if (Objects.nonNull(address.getAddressLineDetail())) {
                sb.append(address.getAddressLineDetail().getLine1()).append(" ");
                sb.append(address.getAddressLineDetail().getLine2());
                sb.append(" - ");
            }
            sb.append(address.getCityOrTown()).append(" - ");
            if (Objects.nonNull(address.getProvinceOrState())) sb.append(address.getProvinceOrState().getName());
            sb.append(" - ");
            sb.append(address.getPostalCode()).append(" - ");
            if (Objects.nonNull(address.getCountry())) sb.append(address.getCountry().getName());

        }
        return sb.toString();
    }

    public static void prettyPrintListOfAddress(List<Address> list){
        list.forEach(a->System.out.println(prettyPrintAddress(a)));
    }

    public static boolean validateAddress(Address address){
//        Result result = new Result();
        if (address == null) {
            System.out.println("No valid address found");
            return false;
        } else {
            if (isInteger(address.getPostalCode()) == false){
                System.out.println("Postal code must be numeric");
                return false;
            }
            if (address.getCountry() == null || address.getCountry().getName() == null ||
                    address.getCountry().getName().isEmpty()){
                System.out.println("Address must contain country");
                return false;}

            if (Objects.isNull(address.getAddressLineDetail()) ||
                    ((address.getAddressLineDetail().getLine1() == null || address.getAddressLineDetail().getLine1().isEmpty()) &&
                            address.getAddressLineDetail().getLine2() == null || address.getAddressLineDetail().getLine2().isEmpty())){
                    System.out.println("Must have at least one address line that is not blank or null");
                return false;
            }

            if ("ZA".equals(address.getCountry().getCode()) && (address.getProvinceOrState() == null || address.getProvinceOrState().getName() == null
                    || address.getProvinceOrState().getName().isEmpty())){
                System.out.println("ZA country requires a province");
                return false;}
        }
        return true;
    }

    public static boolean isInteger(String s) {
        if(s == null || s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(Character.digit(s.charAt(i), 10) < 0) return false;
        }
        return true;
    }

}
