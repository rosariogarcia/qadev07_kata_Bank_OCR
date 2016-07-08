package com.drde.kataocr;

import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Charito on 7/7/2016.
 */
public class KataBank {

    private static final int COLS_DIGITS = 3;
    private static final int ROWS_DIGITS = 3;
    private static final int NUMBER_DIGITS = 9;

    public String accountNumber() throws IOException {

        List<String> accountNumbers = new ArrayList<String>();
        String[] fileAccounts = readLines();

        for (int line = 0; line < fileAccounts.length; line += 4) {
            String[] accountEntry = new String[3];
            accountEntry[0] = fileAccounts[line];
            accountEntry[1] = fileAccounts[line + 1];
            accountEntry[2] = fileAccounts[line + 2];
            String accountOut = parseAsIntegerAccount(accountEntry);
            accountsOutFile(accountOut);
            accountNumbers.add(accountOut);
        }

        return StringUtils.join(accountNumbers, accountNumbers.toString());
    }

    private void accountsOutFile(String accountOut) {
        Writer outputFileAccountd = null;
        try {
            outputFileAccountd = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("accountsout.txt")));
            if (!(isValidAccount(accountOut))) {
                outputFileAccountd.write(accountOut + "ERR");
            }
            outputFileAccountd.write(accountOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] readLines() throws IOException {

        FileInputStream fileInputStream = new FileInputStream("accountsocr.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        String strLine;
        ArrayList<String> lines = new ArrayList<String>();
        while ((strLine = bufferedReader.readLine()) != null) {
            lines.add(strLine);
        }
        return lines.toArray(new String[]{});
    }

    public String parseAsIntegerAccount(String[] accountEntry) {
        StringBuilder digit = new StringBuilder();
        for (int digitIndex = 0; digitIndex < NUMBER_DIGITS; digitIndex++) {
            String[] digitRows = new String[ROWS_DIGITS];
            int substringStartIndex = digitIndex * COLS_DIGITS;
            digitRows[0] = accountEntry[0].substring(substringStartIndex, substringStartIndex + 3);
            digitRows[1] = accountEntry[1].substring(substringStartIndex, substringStartIndex + 3);
            digitRows[2] = accountEntry[2].substring(substringStartIndex, substringStartIndex + 3);

            digit.append(parseDigit(digitRows));
        }
        return digit.toString();
    }

    private String parseDigit(String[] digit) {

        if (Arrays.equals(digit, Digits.ZERO)) {
            return "0";
        } else if (Arrays.equals(digit, Digits.ONE)) {
            return "1";
        } else if (Arrays.equals(digit, Digits.TWO)) {
            return "2";
        } else if (Arrays.equals(digit, Digits.THREE)) {
            return "3";
        } else if (Arrays.equals(digit, Digits.FOUR)) {
            return "4";
        } else if (Arrays.equals(digit, Digits.FIVE)) {
            return "5";
        } else if (Arrays.equals(digit, Digits.SIX)) {
            return "6";
        } else if (Arrays.equals(digit, Digits.SEVEN)) {
            return "7";
        } else if (Arrays.equals(digit, Digits.EIGHT)) {
            return "8";
        } else if (Arrays.equals(digit, Digits.NINE)) {
            return "9";
        } else {
            throw new IllegalArgumentException("Cannot parse digit " + Arrays.toString(digit));
        }
    }

    public boolean isValidAccount(String accountOut) {
        int checksum = 0;
        int i = 1;
        for (char charDigit : new StringBuilder(accountOut).reverse().toString().toCharArray()) {
            checksum += (charDigit - '0') * i++;
        }
        return (checksum % 11) == 0;
    }
}
