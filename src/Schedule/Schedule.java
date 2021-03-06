package Schedule;

import Pricing.PricingContext;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;

import Request.Request;
import Payment.Payment;
import Vehicle.VehicleAndDriver;
import Membership.*;


/**
 * Created by kdao on 8/7/16.
 */
abstract public class Schedule {
    protected Request _request ;
    protected float _price ;
    protected Payment _payment ;
    protected LocalDateTime _startTime ;
    protected LocalDateTime _finishTime ;
    protected Point _startPoint ;
    protected Point _endPoint ;
    protected long _distance ;
    protected VehicleAndDriver _vehicleAndDriver;
    protected ScheduleState _scheduleState ;
    protected ScheduleQueue _scheduleQueue ;
    protected ArrayList<ScheduleObserver> _observers ;

    public Schedule(Request request, Payment payment) {
        _request = request;
        _scheduleQueue = ScheduleQueue.getQueue();
        _scheduleState = new ScheduleQueuingState(this); //Initial setting for schedule state
        _observers = new ArrayList<>();
        _payment = payment;
        _price = 0;
        _startPoint = _request.getStartPoint();
        _endPoint = _request.getEndPoint();
    }

    abstract public void setDistance();

    abstract public long getDistanceInMiles();

    abstract public long getTotalTimeInMinutes();

    abstract public float getBidPricePerMile();

    public void addToQueue() {
        _scheduleQueue.addSchedule(this);
    }

    public void removeFromQueue() {
        _scheduleQueue.removeSchedule(this);
    }

    public void addObserver(ScheduleObserver schedule) {
        _observers.add(schedule);
    }

    public void removeObserver(ScheduleObserver schedule) {
        _observers.remove(schedule);
    }

    /*********************************************************/
    //Getter
    public ScheduleState get_scheduleState() {
        return _scheduleState;
    }

    public void set_scheduleState(ScheduleState s) {
        _scheduleState = s ;
    }

    public Request get_request() {
        return _request;
    }

    public float get_price() {
        return _price;
    }

    public Payment get_payment() {
        return _payment;
    }

    public LocalDateTime get_startTime() {
        return _startTime;
    }

    public LocalDateTime get_finishTime() {
        return _finishTime;
    }

    public Point get_startPoint() {
        return _startPoint;
    }

    public Point get_endPoint() {
        return _endPoint;
    }

    public long get_distance() {
        return _distance;
    }

    public VehicleAndDriver get_vehicleAndDriver() {
        return _vehicleAndDriver;
    }

    public void set_vehicleAndDriver(VehicleAndDriver _vehicleAndDriver) {
        this._vehicleAndDriver = _vehicleAndDriver;
    }

    //End Getter
    /**************************************************************************************/

    /*************************************************************************************/
    //The following is to execute state of a schedule
    public void queuingSchedule() {
        _scheduleState.queuing();
    }
    public void approveSchedule() {
        _scheduleState.approve();
    }

    public void startSchedule() {
        _scheduleState.start();
    }

    public void completeSchedule() {
        _scheduleState.complete();
    }

    public void paySchedule() {
        _scheduleState.pay();
    }

    public void cancelSchedule() {
        _scheduleState.cancel();
    }
}
