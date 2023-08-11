package com.example.TeleMedicine;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.example.TeleMedicine.AdminAppointments.AdminAppointmentsRepository;
import com.example.TeleMedicine.AdminDashboard.AdminDashboardRepository;
import com.example.TeleMedicine.AdminDoctors.AdminDoctorsRepository;
import com.example.TeleMedicine.AdminDoctors.TimeSchedule;
import com.example.TeleMedicine.AdminPatients.AdminPatientsRepository;
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class TeleMedicineAdminTests {
	
    @Autowired
    private AdminAppointmentsRepository adminAppointment;
    @Autowired
    private AdminDashboardRepository adminDashboard;
    @Autowired
    private AdminDoctorsRepository adminDoctors;
    @Autowired
    private AdminPatientsRepository adminPatients;
    
    @Nested
    class AdminAppoitments {
    //@Disabled
    @Test
    public void getAppointments() {
       int category=1;
       List<Map<String, Object>> result= adminAppointment.getAppointments(category);
       LinkedHashMap<String, Object>data=new LinkedHashMap<String,Object>();
       System.out.println(result);
           assertNotNull(result);
           assertTrue(result.size() > 0);
           Map<String, Object> Actual=result.get(0);
          data.put("appointment_id", 34);
          data.put("patientid", 3);
          data.put("patientname", "Ansh_Devil");
          data.put("agegender", "23, Male");
          data.put("mobile", "9515742401");
          data.put("doctorid", 9);
          data.put("doctorname", "Dr.roman");
          data.put("Dept_Name", "Cardiology");
          data.put("state", "TELANGANA");
          data.put("city", "HYDERABAD");
          data.put("date", "15 August,2023");
          data.put("time", "02:30 PM");
          data.put("Status", "Active");
          System.out.println(data);
          System.out.println(Actual);
        assertEquals(data,Actual);  
     }
    }
    @Nested
    class AdminDashboard{
    @Test
    public void dashBoardData() {
        Map<String, Object> data = new LinkedHashMap<>();
            data.put("category", 0);
            data.put("state", "");
            data.put("city", "");
          Map<String, Object> result = adminDashboard.dashBoardData(data);
          assertNotNull(result);
          assertEquals(26, result.get("doctorsCount"));
          assertEquals(31, result.get("patientsCount"));
          assertEquals(35, result.get("appointmentsCount"));
          assertNotNull(result.get("appointmentsList"));
          assertNotNull(result);
         Object result1 = ((List<Map<String, Object>>) adminDashboard.dashBoardData(data).get("appointmentsList")).get(0);
          System.out.println(result1);
          Map<String,Object>Expected=new LinkedHashMap<String,Object>();
          Expected.put("patient_id",1);
          Expected.put("PatientName","ansh");
          Expected.put("doctor_id",1);
          Expected.put("DoctorName","aa");
          Expected.put("Department", "Nephrology");
          Expected.put("State", "TELANGANA");
          Expected.put("City", "HYDERABAD");
          Expected.put("time", "01:32 PM");
          Expected.put("Status", "Active");
          System.out.println(Expected);
          assertEquals(Expected,result1);
    }
    }
    @Test
    public void testGetDoctorsList() {
        Map<String, Object> inputParameters = new LinkedHashMap<>();
        inputParameters.put("category", 8);
        inputParameters.put("state", "");
        inputParameters.put("city", "");
        List<Map<String, Object>> resultList = adminDoctors.getDoctorsList(inputParameters);
        assertNotNull(resultList);
        Map<String, Object> result = resultList.get(0);
        assertNotNull(result);
        assertEquals(7, result.get("doctor_id")); // Use 7L instead of 7 to match Long
        assertEquals("Dr.msd", result.get("doctor_name"));
        assertEquals("Radiology", result.get("dept_name"));
        assertEquals(2L, result.get("patients"));
        assertEquals(2L, result.get("appointments"));
        assertEquals("TELANGANA", result.get("state"));
        assertEquals("HYDERABAD", result.get("city"));
        assertEquals("Active", result.get("status"));
    }
   @Test
    public void getDoctorProfile() {
        int doctorId=1;
        Map<String, Object> All=(Map<String, Object>) adminDoctors.getDoctorProfile(doctorId);
        //System.out.println(All);
        //System.out.println(b);
        //System.out.println(c);
        Map<String, Object> a=  (Map<String, Object>) adminDoctors.getDoctorProfile(doctorId).get("overview");
        assertAll(
                ()->assertNotNull(a),
                ()->assertEquals(1,a.get("id")),
                ()->assertEquals("aa",a.get("name")),
                ()->assertEquals("8179397535",a.get("mobile")),
                ()->assertEquals("aa@gmail.com",a.get("email")),
                ()->assertEquals("123414",a.get("license")),
                ()->assertEquals("Nephrology",a.get("dept_name")),
                ()->assertEquals("i am good",a.get("about")),
                ()->assertEquals(111L,a.get("experience")),
                ()->assertEquals(500L,a.get("cost")),
                ()->assertEquals("TELANGANA",a.get("state"))
                );
        Map<String, Object> b=  (Map<String, Object>) adminDoctors.getDoctorProfile(doctorId).get("reviews");
       List<Map<String, Object>> c= (List<Map<String, Object>>) b.get("reviews");
       System.out.println(c.get(0).get("patient_name"));
       assertAll(
               ()->assertNotNull(c),
               ()->assertEquals("ansh",c.get(0).get("patient_name")),
               ()->assertEquals(4,c.get(0).get("rating")),
               ()->assertEquals("good one",c.get(0).get("comments")),
               ()->assertEquals("July 23",c.get(0).get("review_date"))
               );
       List<TimeSchedule> d=  (List<TimeSchedule>) adminDoctors.getDoctorProfile(doctorId).get("timeTable");
       System.out.println(d);
       TimeSchedule e=d.get(0);
       assertAll(
           () -> assertNotNull(d),
           () -> assertEquals(1, Integer.parseInt(e.getId())), 
           () -> assertNotNull(e.getFrom()),
           () -> assertEquals("09:00:00",e.getFrom()),
           () ->assertEquals("14:00:00",e.getTo())
       );
    }
    //@Test
    public void profileUpdate() {
         Map<String, Object> doctorProfile=new LinkedHashMap<>();
        doctorProfile.put("name","ravi");
        doctorProfile.put("experience", 1);
        doctorProfile.put("email", "is@123");
        doctorProfile.put("about", "good");
        doctorProfile.put("id", 4);
        int result = adminDoctors.profileUpdate(doctorProfile);
        assertEquals(2,result);
        System.out.println(result);
    }
    //@Test
    public void profilePicRemove() {
        int doctorId=4;
        int result = adminDoctors.profilePicRemove(doctorId);
        assertEquals(1,result);
    }
    //@Test
    public void addSpecialization() {
        int doctorId = 4;
        int[] specialization= {1,2} ;
        int rowsUpdated =adminDoctors.addSpecialization(doctorId, specialization);
        assertEquals(2,rowsUpdated);
    }
    @Nested
    class AdminPatients{
   @Test
    public void getPatientsList() {
       Map<String, Object> b = adminPatients.getPatientsList().get(0);
        System.out.println(b);
        assertNotNull(b);
        assertTrue(b.size()>0);
        assertEquals(1, b.get("patient_id"));
        assertEquals("ansh", b.get("patient_name"));
        assertEquals("7013776810", b.get("mobile"));
        assertEquals(1, b.get("age"));
        assertEquals(5, Integer.parseInt((String) b.get("height")));   
    }
    @Test
    public void getPatientProfile() {
         int patientId=1;
        Map<String, Object> a= adminPatients.getPatientProfile(patientId);
        Map<String, Object> b= (Map<String, Object>) adminPatients.getPatientProfile(patientId).get("profile");
        //System.out.println(b);
        assertAll(
                () -> assertNotNull(b),
                () ->assertEquals(11,b.size()),
                () -> assertEquals(1,b.get("patient_id")),
                () -> assertEquals("ansh",b.get("patient_name")),
                () -> assertEquals("7013776810",b.get("mobile")),
                () -> assertEquals(1,b.get("age")),
                () -> assertEquals("Male",b.get("gender")),
                () -> assertEquals("5",((String) b.get("height"))),
                () -> assertEquals(60,Integer.parseInt((String) b.get("weight"))),
                () -> assertEquals("TELANGANA",b.get("state")),
                () -> assertEquals("HYDERABAD",b.get("city")),
                () -> assertEquals("AB+",b.get("blood_group")),
                () -> assertEquals(null,b.get("img"))  
            );
        List<Map<String, Object>> c= (List<Map<String, Object>>) adminPatients.getPatientProfile(patientId).get("appointmentsHistory");
        Map<String, Object> e=c.get(0);
        //System.out.println(e);
        assertAll(
                () -> assertNotNull(c),
                () ->assertEquals(6,e.size()),
                () -> assertEquals(1,e.get("appointment_id")),
                () -> assertEquals("01 July, 2022  12:00 AM",e.get("appointment_session")),
                () -> assertEquals("RS",e.get("appointment_status")),
                () -> assertEquals(4,e.get("doctor_id")),
                () -> assertEquals("venky",e.get("doctor_name")),
                () -> assertEquals(null, e.get("img"))
                );
        List<Map<String, Object>> f= (List<Map<String, Object>>) adminPatients.getPatientProfile(patientId).get("medicalHistory");
        System.out.println(f.get(0));
        Map<String, Object> g=f.get(0);
        assertAll(
                () -> assertNotNull(g),
                () ->assertEquals(2,g.size()),
                () -> assertEquals(null,e.get("PATIENT_ID")),
                () -> assertEquals(null,e.get("COMPLAINT_ID"))
                );
    }
    }
}
