package Dispatch;

import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Vehicle.*;
import Report.*;
import Membership.*;

/**
 * Created by kdao on 8/13/16.
 */
public class DataTest {
    private static DataTest test = null;
    private static ArrayList<VehicleAndDriver> availVehicles = null;
    //private static ArrayList<Vehicle> inoperateVehicles = null;
    private static ArrayList<VehicleAndDriverReport> reportList;

    private DataTest() {
        availVehicles = new ArrayList<VehicleAndDriver>();
        //inoperateVehicles = new ArrayList<Vehicle>();
        reportList = new ArrayList<VehicleAndDriverReport>();
    }

    public static synchronized DataTest getTest() {
        if (test == null) {
            test = new DataTest();
        }
        return test;
    }

    public List<VehicleAndDriver> getActiveVehicleList() {
        return availVehicles;
    }

//    public List<Vehicle> getCompanyVehicleList() {
//        return companyVehicles;
//    }

    public ArrayList<VehicleAndDriverReport> getReportList() {
        return reportList;
    }

    public VehicleAndDriver getFreeVehicleAndDriver() {
        for (VehicleAndDriver vd : availVehicles) {
            if (vd.getVehicle().getState().toString().equalsIgnoreCase("available")) {
                return vd;
            }
        }
        return null;
    }

    public void loadObjectsForTesting(int objCount) {
        Random rand = new Random(LocalTime.now().toNanoOfDay());
        for (int i = 0; i < objCount; i++) {
            VehicleAndDriver vd = new VehicleAndDriver();
            vd.setDriver(new Driver("driver#" + i, "phone#", "email@", "lic#", "insur#"));
            CompactVehicle c = new CompactVehicle("vin#" + i, "make-x", "model-x", 2016, new PersonalOwnedVehicle("driver#" + i));
            c.setLocation(new Point(rand.nextInt(100), rand.nextInt(100)));
            vd.setVehicle(c);

            DataTest.getTest().addVehicleAndDriver(vd);
            reportAddDriverOwnedInventory(vd);
        }
        for (int i = 0; i < objCount; i++) {
            VehicleAndDriver vd = new VehicleAndDriver();
            vd.setDriver(new Driver("CompanyDriver-" + i, "phone#", "email@", "lic#", "insur#"));
            Vehicle v = new CompactVehicle("company#" + i, "make-x", "model-x", 2016, new CompanyOwnedVehicle("company#" + i));
            v.setLocation(new Point(50, 50));// right in the middle
            vd.setVehicle(v);
            DataTest.getTest().addVehicleAndDriver(vd);
            //DataTest.getTest().addVehicleAndDriver(v);
            reportAddCompanyOwnedInventory(vd);
        }
    }

    public synchronized void addVehicleAndDriver(VehicleAndDriver vd) {
        availVehicles.add(vd);
    }

//    public synchronized void addVehicle(Vehicle v) {
//        companyVehicles.add(v);
//    }

    public void reportAddDriverOwnedInventory(VehicleAndDriver vd) {
        VehicleAndDriverReport vehicleAndDriverReport = new VehicleAndDriverReport(vd);
        System.out.println("Add driver owned inventory:");
        reportList.add(vehicleAndDriverReport);
        vehicleAndDriverReport.printReport();
    }

    public void reportAddCompanyOwnedInventory(VehicleAndDriver vd) {
        VehicleAndDriverReport vehicleAndDriverReport = new VehicleAndDriverReport(vd);
        System.out.println("Add company owned inventory:");
        reportList.add(vehicleAndDriverReport);
        vehicleAndDriverReport.printReport();
    }
}
