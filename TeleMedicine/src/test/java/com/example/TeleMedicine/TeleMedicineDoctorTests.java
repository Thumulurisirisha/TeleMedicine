package com.example.TeleMedicine;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.TeleMedicine.DoctorAppointments.DoctorAppointmentRepository;
import com.example.TeleMedicine.DoctorLogin.DoctorLoginRepository;
import com.example.TeleMedicine.DoctorProfile.DoctorProfileRepository;
import com.example.TeleMedicine.DoctorProfile.Doctor_Details;
import com.example.TeleMedicine.DoctorSpare.DoctorSpareRepository;
@SpringBootTest
public class TeleMedicineDoctorTests {
	@Autowired
	private DoctorAppointmentRepository doctorAppointment;
	@Autowired
	private DoctorLoginRepository doctorLogin;
	@Autowired
	private DoctorProfileRepository doctorProfile;
	@Autowired
	private DoctorSpareRepository doctorSpare;
     @Test
     public void getAppointmentsHistory() {
      int doctorId=1;
      List<Map<String, Object>> a=doctorAppointment.getAppointmentsHistory(doctorId);
      assertNotNull(a);
      assertTrue(a.size()>0);
      Map<String, Object> b= a.get(0);
      System.out.println(b);
      assertAll(
              ()->assertNotNull(b),
              ()->assertEquals(3,b.get("appointment_id")),
              ()->assertEquals(1,b.get("patient_id")),
              ()->assertEquals("ansh",b.get("patient_name")),
              ()->assertEquals(1,b.get("age")),
              ()->assertEquals("Male",b.get("gender")),
              ()->assertEquals("C",b.get("appointment_status"))
              );
     }
      @Test
      public void getUpcomingAppointments() {
          int doctorId=3;
          List<Map<String, Object>> a=doctorAppointment.getUpcomingAppointments(doctorId);
          assertNotNull(a);
          assertFalse(a.size()>0);
          System.out.println(a);
     }
      //@Test
      public void postponeAppointment() {
          int appointmentId=1;
          String dateTime="2022-07-01 00:00:00";
          int a=doctorAppointment.postponeAppointment(appointmentId, dateTime);
          assertTrue(a>0);
          assertNotNull(a);
          System.out.println(a);
      }
      //@Test
  	public void otpRequest() throws ParseException {
  		String mobile="9441913651";
  		Map<String, Object> a=doctorLogin.otpRequest(mobile);
  		System.out.println(a);
  		assertNotNull(a);
  	}
  //@Test
    public void verifyOtps() {
        Map<String, Object> otpVerify = new LinkedHashMap<>();
        otpVerify.put("mobile", "9441913651");
        Map<String, Object> expectedResult = new LinkedHashMap<>();
        expectedResult.put("otp", "0534");
        expectedResult.put("validity", 0);
        Map<String, Object> output = doctorLogin.verifyOtp(otpVerify);
        assertEquals(expectedResult.get("otp"), output.get("otp"));
        assertNotNull(output);
  }
@Test
  public void  getProfileData() {
      String mobile="9441913651";
      Doctor_Details a=doctorProfile.getProfileData(mobile);
      System.out.println(a);
      assertNotNull(a);
      assertEquals("siri",a.getName());
      assertEquals("Cardiology",a.getDept());
  }
//@Test
 public void profileUpdate() {
	Map<String,Object> profile=new LinkedHashMap<>();
     profile.put("name", "venky");
     profile.put("about", "i am doctor");
     profile.put("email", "venky@123");
     profile.put("id", 4);
     int a=doctorProfile.profileUpdate(profile);
     System.out.println(a);
     assertNotNull(a);
     assertTrue(a>0);
 }
//@Test
public void profilePicRemove() {
    int doctorId=1;
    int result = doctorProfile.profilePicRemove(doctorId);
    assertEquals(1,result);
}
@Test
public void profileHistory() {
    int doctorId=1;
    Map<String, Object> All=doctorProfile.profileHistory(doctorId);
    Map<String, Object> a=(Map<String, Object>) doctorProfile.profileHistory(doctorId).get("profileData");
    Map<String, Object> b=(Map<String, Object>) doctorProfile.profileHistory(doctorId).get("RatingReviews");
    List<Map<String, Object>> c= (List<Map<String, Object>>) doctorProfile.profileHistory(doctorId).get("patientReviews");
    System.out.println(All);
    System.out.println("profileData"+a);
    System.out.println("RatingReviews"+b);
    System.out.println("patientReviews"+c);
    assertNotNull(a);
    assertNotNull(b);
    assertNotNull(c);
   assertTrue(a!=null);
   assertTrue(b!=null);
   assertTrue(c!=null);
 }
//@Test
public void timeChangeForDate() {
    Map<String,Object> change=new LinkedHashMap<>();
    change.put("doctorId", 1);
    change.put("date", "2023-07-10");
    change.put("from", "09:00:00");
    change.put("to", "12:00:00");
    change.put("time", 15);
    int a=doctorProfile.timeChangeForDate(change);
    assertTrue(a>0);
    System.out.println(a);
    System.out.println(change);
}
@Test
public void getPatientData() {
    int patientId=1;
    Map<String, Object> a=doctorSpare.getPatientData(patientId);
    assertNotNull(a);
    System.out.println(a);
    assertEquals("7013776810",a.get("mobile"));
}  
@Test
public void getAppointments() {
	int patientId=1;
	Map<String, Object> a=doctorSpare.getAppointments(patientId).get(0);
	System.out.println(a);
	assertNotNull(a);
	assertEquals(3,a.get("appointment_id"));
	assertEquals("aa",a.get("doctor_name"));
	
}
}
