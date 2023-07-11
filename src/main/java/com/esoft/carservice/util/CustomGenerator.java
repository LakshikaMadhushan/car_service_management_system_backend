package com.esoft.carservice.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


@RequiredArgsConstructor
@Slf4j
@Component
public class CustomGenerator {




    /**
     * @return this function will return a five digit random number as String
     */
    public String generateOTP() {
        Random r = new Random();
        int low = 1000;
        int high = 10000;
        int code = r.nextInt(high - low) + low;
        return String.valueOf(code);
    }

    public Date changeDateFromMinutes(Date date, int minutes) {
        if (date != null) {
            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime(date); // sets calendar time/date
            cal.add(Calendar.MINUTE, minutes); // plus or minus min
            return cal.getTime();
        }
        return null;
    }

    /**
     * @return a string password with given length
     */
    public String createPassword() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@!#$*&";
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(chars.length());
            stringBuilder.append(chars.charAt(randomIndex));
        }

        return stringBuilder.toString();
    }

//    public String generateCBNumber(String year, long courseIndex, String batchId, String batchIndex, String branchId, String branchIndex, long studentId) {
//
//        log.info("Generating CB number year {}, courseIndex {}, batchId {}, batchIndex {}, branchId {}, branchIndex {}, studentId {} cbNumberFormat {}", year, courseIndex, batchId, batchIndex, branchId, branchIndex, studentId, CB_NUMBER_FORMAT);
//
//        switch (CB_NUMBER_FORMAT) {
//            case STUDENT_ID:
//                return String.format(REGISTRATION_NUMBER_FORMAT, (REGISTRATION_NUMBER_STARTING_FROM + (studentId + 1)));
//            case REG_YEAR_COURSE_INDEX_BATCH_INDEX_BRANCH_INDEX_STUDENT_ID:
//                return year + courseIndex + batchIndex + branchIndex + generateStudentNumberForIJSE(batchId, branchId);
//            default:
//                return String.valueOf(studentId);
//        }
//    }

//    public String generateStudentNumberForIJSE(String batchId, String branchId) {
//        try {
//            log.info("Generating CB number for IJSE batchId {}, branchId {}", batchId, branchId);
//
//            long bId = Long.parseLong(batchId);
//            long brId = Long.parseLong(branchId);
//            long lastStudentNumber = studentRepository.getLastStudentNumber(bId, brId);
//            return String.format(REGISTRATION_NUMBER_FORMAT, lastStudentNumber);
//        } catch (Exception e) {
//            log.error("Error occurred when creating a student number for IJSE e {}", e.getMessage());
//            return "001";
//        }
//    }
}
