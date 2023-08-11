package com.example.TeleMedicine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.TeleMedicine.CommonServices.DoctorProfile;
import com.example.TeleMedicine.CommonServices.SpareRepository;

@SpringBootTest
class TeleMedicineApplicationTests {

	@Test
	void contextLoads() {
	}
	
    @Autowired
    private SpareRepository spareRepositary;
    @Test
    public void getMedicalTests() {
    	List<Map<String, Object>> a=spareRepositary.getMedicalTests();
    	System.out.println(a.size());
    	assertNotNull(a);
    	assertTrue(a.size()>0);
    	assertEquals(82,a.size());
    }
    @Test
    public void getStates() {
    	List<Map<String, Object>> a=spareRepositary.getStates();
    	System.out.println(a);
    	System.out.println(a.size());
    	assertNotNull(a);
    	assertTrue(a.size()>0);
    }
    @Test
    public void getCities() {
    	int state=3;
    	List<Map<String, Object>> a=spareRepositary.getCities(state);
    	System.out.println(a);
    	assertNotNull(a);
    	assertTrue(a.size()>0);
    }
    @Test
	public void getComplaints() {
    	List<Map<String, Object>> a=spareRepositary.getComplaints();
    	assertNotNull(a);
    	assertTrue(a.size()>0);
    	System.out.println(a);
    }
	@Test
	public void getSpecializations(){
		List<Map<String, Object>> a=spareRepositary.getSpecializations();
		System.out.println(a);
		assertNotNull(a);
    	assertTrue(a.size()>0);
	}
	//@Test
    public void otpRequests() throws ParseException {
        String mobile="8499823591";
        Map<String, Object> a=spareRepositary.otpRequest(mobile);
        System.out.println(a);
        assertNotNull(a);
    }
   //@Test
   public void verifyOtp(){
        Map<String, Object> otpVerify = new LinkedHashMap<>();
           otpVerify.put("mobile", "8499823591");
           Map<String, Object> expectedResult = new LinkedHashMap<>();
           expectedResult.put("otp", "4102");
           expectedResult.put("validity", 0);
           Map<String, Object> output = spareRepositary.verifyOtp(otpVerify);
           assertEquals(expectedResult.get("otp"), output.get("otp"));
           assertNotNull(output);
   }
   //@Test
   public void registerDoctor( ) {
       DoctorProfile doctorProfile=new DoctorProfile();
       doctorProfile.setId(1);
       doctorProfile.setName("Ansh");
       doctorProfile.setImg(null);
       doctorProfile.setMobile("6441913651");
       doctorProfile.setEmail("sfnann@123");
       doctorProfile.setGenderId(1);
       doctorProfile.setCost(300);
       doctorProfile.setLicense("ABC@!234");
       doctorProfile.setState("Telangana");
       doctorProfile.setCity("hyderabad");
       doctorProfile.setSpecialization(new int[]{1});
       doctorProfile.setDeptId(1);
       doctorProfile.setExperience(1);
       doctorProfile.setAbout("good");
       doctorProfile.setFromTime("09:00:00");
       doctorProfile.setToTime("12:00:00");
       doctorProfile.setSessionTime(30);
       int result = spareRepositary.registerDoctor(doctorProfile);
       assertEquals(1, result);
       System.out.println(result);
   }

}
