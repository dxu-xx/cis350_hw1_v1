package edu.upenn.cis350.gpx;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;


public class GPXcalculatorTest {
	//general test, ie. with two working points a, b
	@Test
	public void test_1() {
		GPXtrkpt a= new GPXtrkpt(0, 0, null);
		GPXtrkpt b= new GPXtrkpt(3, 4, null);
		GPXtrkpt c= new GPXtrkpt(3, 5, null);
		GPXtrkpt d= new GPXtrkpt(0, 0, null);
		GPXtrkpt e= new GPXtrkpt(1, 0, null);
		ArrayList<GPXtrkpt> points= new ArrayList<GPXtrkpt>();
		ArrayList<GPXtrkpt> points2= new ArrayList<GPXtrkpt>();
		points.add(a);
		points.add(b);
		points.add(c);
		points2.add(d);
		points2.add(e);
		GPXtrkseg segments= new GPXtrkseg(points);
		GPXtrkseg segments2= new GPXtrkseg(points2);
		ArrayList<GPXtrkseg> seglist= new ArrayList<GPXtrkseg>();
		ArrayList<GPXtrkseg> seglist2= new ArrayList<GPXtrkseg>();
		seglist.add(segments);
		seglist2.add(segments2);
		GPXtrk gp= new GPXtrk("seglist", seglist);
		assertEquals(GPXcalculator.calculateDistanceTraveled(gp), 6.0, 0.1);
		//test segment 2
		gp= new GPXtrk("seglist2", seglist2);
		assertEquals(GPXcalculator.calculateDistanceTraveled(gp), 1.0, 0.1);
		//test both combined segments
		seglist.add(segments2);
		gp= new GPXtrk("combinedseglist", seglist);
		assertEquals(GPXcalculator.calculateDistanceTraveled(gp), 7.0, 0.1);
		}
	
	//if GPXtrk object is null, method should return -1
	@Test
	public void test_2() {
		GPXtrk emptyclass= null;
		assertEquals(GPXcalculator.calculateDistanceTraveled(emptyclass), -1.0, 0.01);
	}
	
	//if GPXtrk contains no GPXtrkseg object, the method should return -1
	@Test
	public void test_3() {
		GPXtrk empty= new GPXtrk("GPX Test", new ArrayList<GPXtrkseg>());
		assertEquals(GPXcalculator.calculateDistanceTraveled(empty), -1.0, 0.01);
	}
	
	//if GPXtrkseg in the GPXtrk is null, distance traveled for that GPXtrkseg should be considered 0
	@Test
	public void test_4() {
		GPXtrkseg emptyseg= null;
		ArrayList<GPXtrkseg> list= new ArrayList<GPXtrkseg>();
		list.add(emptyseg);
		GPXtrk empty= new GPXtrk("empty", list);
		assertEquals(GPXcalculator.calculateDistanceTraveled(empty), 0.0, 0.01);
	}
	
	//if a GPXtrkseg contains no GPXtrkpt objects, the distance traveled for that GPXtrkseg should be considered 0
	@Test
	public void test_5() {
		GPXtrkseg emptyseg= new GPXtrkseg(new ArrayList<GPXtrkpt>());
		ArrayList<GPXtrkseg> list= new ArrayList<GPXtrkseg>();
		list.add(emptyseg);
		GPXtrk empty= new GPXtrk("empty", list);
		assertEquals(GPXcalculator.calculateDistanceTraveled(empty), 0.0, 0.01);
	}
	
	//if a GPXtrkseg contains only one GPXtrkpt, the distance traveled for the GPXtrkseg should be considered 0
	@Test
	public void test_6() {
		GPXtrkpt a= new GPXtrkpt(0, 0, null);
		ArrayList<GPXtrkpt> listpoints= new ArrayList<GPXtrkpt>();
		listpoints.add(a);
		GPXtrkseg singleseg= new GPXtrkseg(listpoints);
		ArrayList<GPXtrkseg> singlelist= new ArrayList<GPXtrkseg>();
		singlelist.add(singleseg);
		GPXtrk single= new GPXtrk("single", singlelist);
		assertEquals(GPXcalculator.calculateDistanceTraveled(single), 0.0, 0.01);
	}
	
	//If any GPXtrkpt in a GPXtrkseg is null, the distance traveled for that GPXtrkseg should be considered 0
	@Test
	public void test_7() {
		GPXtrkpt a= new GPXtrkpt(0, 0, null);
		GPXtrkpt b= null;
		ArrayList<GPXtrkpt> listpoints= new ArrayList<GPXtrkpt>();
		listpoints.add(a);
		listpoints.add(b);
		GPXtrkseg singleseg= new GPXtrkseg(listpoints);
		ArrayList<GPXtrkseg> singlelist= new ArrayList<GPXtrkseg>();
		singlelist.add(singleseg);
		GPXtrk single= new GPXtrk("single", singlelist);
		assertEquals(GPXcalculator.calculateDistanceTraveled(single), 0.0, 0.01);
	}
	
	//If any GPXtrkpt has a latitude greater than 90 or less than -90, the distance traveled for thatGPX trkseg should be considered 0
	@Test
	public void test_8() {
		//latitude of a is greater than 90
		GPXtrkpt a= new GPXtrkpt(91, 0, null);
		GPXtrkpt a2= new GPXtrkpt(87, 0, null);
		ArrayList<GPXtrkpt> listpoints= new ArrayList<GPXtrkpt>();
		listpoints.add(a);
		listpoints.add(a2);
		GPXtrkseg singleseg= new GPXtrkseg(listpoints);
		ArrayList<GPXtrkseg> singlelist= new ArrayList<GPXtrkseg>();
		singlelist.add(singleseg);
		GPXtrk single= new GPXtrk("single", singlelist);
		assertEquals(GPXcalculator.calculateDistanceTraveled(single), 0.0, 0.01);
	}
	@Test
	public void test_9() {
		//latitude of b is less than -90
		ArrayList<GPXtrkpt> listpoints= new ArrayList<GPXtrkpt>();
		GPXtrkpt b= new GPXtrkpt(-91, 0, null);
		GPXtrkpt b2= new GPXtrkpt(87, 0, null);
		listpoints.add(b);
		listpoints.add(b2);
		GPXtrkseg singleseg= new GPXtrkseg(listpoints);
		ArrayList<GPXtrkseg> singlelist= new ArrayList<GPXtrkseg>();
		singlelist.add(singleseg);
		GPXtrk single2= new GPXtrk("single", singlelist);
		assertEquals(GPXcalculator.calculateDistanceTraveled(single2), 0.0, 0.01);
	}
	
	@Test
	public void test_10() {
		
		//latitude of c is exactly 90, ie. should work
		ArrayList<GPXtrkpt> listpoints= new ArrayList<GPXtrkpt>();
		GPXtrkpt c= new GPXtrkpt(90, 0, null);
		GPXtrkpt c2= new GPXtrkpt(87, 0, null);
		listpoints.add(c);
		listpoints.add(c2);
		GPXtrkseg singleseg3= new GPXtrkseg(listpoints);
		ArrayList<GPXtrkseg> singlelist3= new ArrayList<GPXtrkseg>();
		singlelist3.add(singleseg3);
		GPXtrk single3= new GPXtrk("single", singlelist3);
		assertEquals(GPXcalculator.calculateDistanceTraveled(single3), 3.0, 0.01);
	}
	
	@Test
	public void test_11() {
		
		//latitude of d is exactly -90, ie. should work
		ArrayList<GPXtrkpt> listpoints= new ArrayList<GPXtrkpt>();
		GPXtrkpt d= new GPXtrkpt(-90, 0, null);
		GPXtrkpt d2= new GPXtrkpt(-89, 0, null);
		listpoints.add(d);
		listpoints.add(d2);
		GPXtrkseg singleseg4= new GPXtrkseg(listpoints);
		ArrayList<GPXtrkseg> singlelist4= new ArrayList<GPXtrkseg>();
		singlelist4.add(singleseg4);
		GPXtrk single4= new GPXtrk("single", singlelist4);
		assertEquals(GPXcalculator.calculateDistanceTraveled(single4), 1.0, 0.01);
	}
	
	//If any GPXtrkpt in a GPXtrkseg has a longitude that is greater than 180 or less than -180 the distance traveled is 0
	@Test
	public void test_12() {
		//longitude of a is greater than 180
		GPXtrkpt a= new GPXtrkpt(0, 181, null);
		GPXtrkpt a2= new GPXtrkpt(0, 0, null);
		ArrayList<GPXtrkpt> listpoints= new ArrayList<GPXtrkpt>();
		listpoints.add(a);
		listpoints.add(a2);
		GPXtrkseg singleseg= new GPXtrkseg(listpoints);
		ArrayList<GPXtrkseg> singlelist= new ArrayList<GPXtrkseg>();
		singlelist.add(singleseg);
		GPXtrk single= new GPXtrk("single", singlelist);
		assertEquals(GPXcalculator.calculateDistanceTraveled(single), 0.0, 0.01);
		
	}
	@Test
	public void test_13() {
		
		//longitude of b2 is less than -181
		GPXtrkpt b= new GPXtrkpt(0, 0, null);
		GPXtrkpt b2= new GPXtrkpt(0, -181, null);
		ArrayList<GPXtrkpt> listpoints2= new ArrayList<GPXtrkpt>();
		listpoints2.add(b);
		listpoints2.add(b2);
		GPXtrkseg singleseg2= new GPXtrkseg(listpoints2);
		ArrayList<GPXtrkseg> singlelist2= new ArrayList<GPXtrkseg>();
		singlelist2.add(singleseg2);
		GPXtrk single2= new GPXtrk("single", singlelist2);
		assertEquals(GPXcalculator.calculateDistanceTraveled(single2), 0.0, 0.01);
	}
	@Test
	public void test_14() {
		
		//longitude of c2 is exactly 180
		GPXtrkpt c= new GPXtrkpt(0, 179, null);
		GPXtrkpt c2= new GPXtrkpt(0, 180, null);
		ArrayList<GPXtrkpt> listpoints3= new ArrayList<GPXtrkpt>();
		listpoints3.add(c);
		listpoints3.add(c2);
		GPXtrkseg singleseg3= new GPXtrkseg(listpoints3);
		ArrayList<GPXtrkseg> singlelist3= new ArrayList<GPXtrkseg>();
		singlelist3.add(singleseg3);
		GPXtrk single3= new GPXtrk("single", singlelist3);
		assertEquals(GPXcalculator.calculateDistanceTraveled(single3), 1.0, 0.01);
	}
	@Test public void test_15() {
		
		//longitude of d2 is exactly -180
		GPXtrkpt d= new GPXtrkpt(0, -179, null);
		GPXtrkpt d2= new GPXtrkpt(0, -180, null);
		ArrayList<GPXtrkpt> listpoints4= new ArrayList<GPXtrkpt>();
		listpoints4.add(d);
		listpoints4.add(d2);
		GPXtrkseg singleseg4= new GPXtrkseg(listpoints4);
		ArrayList<GPXtrkseg> singlelist4= new ArrayList<GPXtrkseg>();
		singlelist4.add(singleseg4);
		GPXtrk single4= new GPXtrk("single", singlelist4);
		assertEquals(GPXcalculator.calculateDistanceTraveled(single4), 1.0, 0.01);
	}
	
}
