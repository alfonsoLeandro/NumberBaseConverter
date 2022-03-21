package com.github.alfonsoleandro.numberbaseconverter;

import javax.print.DocFlavor;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

public class NumberBaseConverter {

    private static final char[] hexNumbers = new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    public static void main(String[] args) throws IOException {
        PrintStream out = System.out;
        Scanner sn = new Scanner(System.in);

        outer: while(true){
            out.print("Ingrese un numero: ");

            String input = sn.nextLine();
            Base base = Base.getByFirstChar(input.charAt(0));
            String number = input.substring(1);

            out.println();
            out.println("detected type: "+base);


            switch (base){
                case DECIMAL:
                    out.println("Binary: "+decimalToBinary(number));
                    out.println("Hex: "+decimalToHex(number));
                    break;

                case BINARY:
                    out.println("Decimal: "+binaryToDecimal(number));
                    out.println("Hex: "+binaryToHex(number));
                    break;

                case HEXADECIMAL:
                    out.println("Decimal: "+hexToDecimal(number));
                    out.println("Binary: "+hexToBinary(number));
                    break;

                case STRING:
                    if(number.equalsIgnoreCase("clear")){
                        for (int i = 0; i < 20; i++) {
                            out.println();
                        }
                    }else if(number.equalsIgnoreCase("exit")){
                        break outer;
                    }else{
                        out.println("No reconocido");
                    }

                    break;

            }
            out.println();
        }
    }


    private static String decimalToBinary(String decimal){
        StringBuilder binary = new StringBuilder();
        int d = Integer.parseInt(decimal);

        while(d >= 1){
            binary.append(d%2);
            d /= 2;
        }

        return(binary.reverse().toString());
    }

    private static String decimalToHex(String decimal){
        StringBuilder binary = new StringBuilder();
        int d = Integer.parseInt(decimal);
        if(d<1) return "0";

        while(d >= 1){
            binary.append(hexNumbers[d%16]);
            d /= 16;
        }

        return (binary.reverse().toString());
    }

    public static String binaryToDecimal(String binary){
        int exp = 0;
        int total = 0;
        List<Character> bits = binary.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        Collections.reverse(bits);

        for(char c : bits){
            total += Character.getNumericValue(c) *  (Math.pow(2,exp));
            exp++;
        }

        return String.valueOf(total);
    }
    public static String binaryToHex(String binary){
        StringBuilder sb = new StringBuilder(binary);
        sb.reverse();
        while(sb.length()%4 != 0){
            sb.append(0);
        }
        String bn = sb.reverse().toString();
        StringBuilder total = new StringBuilder();

        for(int i = 0; i < sb.length() ; i+=4){
            int decimal = Integer.parseInt(binaryToDecimal(bn.substring(i, i+4)));
            total.append(decimalToHex(String.valueOf(decimal)));
        }

        return total.toString();
    }

    public static String hexToDecimal(String hex){
//        List<Character> chars = Arrays.asList(hex.ch)
        List<Character> chars = hex.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        List<Character> hexNumbers = new ArrayList<>();
        for(char c : NumberBaseConverter.hexNumbers){
            hexNumbers.add(c);
        }
        int total = 0;
        for (int i = chars.size()-1; i >=0 ; i--) {
            System.out.println("index+1= "+(hexNumbers.indexOf(chars.get(i))+1));
            total += hexNumbers.indexOf(chars.get(i))+1*Math.pow(16, i);
        }
        return String.valueOf(total);
    }

    public static String hexToBinary(String hex){
        return decimalToBinary(hexToDecimal(hex.toUpperCase(Locale.ROOT)));
    }



    enum Base{
        DECIMAL,
        BINARY,
        HEXADECIMAL,
        STRING;


        public static Base getByFirstChar(char c){
            switch (c) {
                case 'D':
                case 'd':
                    return DECIMAL;
                case 'B':
                case 'b':
                    return BINARY;
                case 'H':
                case 'h':
                    return HEXADECIMAL;
                default:
                    return STRING;
            }
        }
    }

}
