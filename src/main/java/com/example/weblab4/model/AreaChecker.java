package com.example.weblab4.model;

import com.example.weblab4.POJO.Requests.CheckDotRequest;
import com.example.weblab4.entities.EntryEntity;
import com.example.weblab4.exceptions.NotIncludedInTheRangeException;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class AreaChecker {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public EntryEntity checkEntry(CheckDotRequest checkDotRequest) throws NotIncludedInTheRangeException {
        float x = checkDotRequest.getX();
        float y = checkDotRequest.getY();
        float r = checkDotRequest.getR();

        if (r> 2 || r < -2){
            throw new NotIncludedInTheRangeException("Значение R не входит в интервал [-2,2]");
        }else if(y<=-5 || y >= 5){
            throw new NotIncludedInTheRangeException("Значение Y не входит в интервал (-5,5)");
        }else if(x> 2 || x < -2){
            throw new NotIncludedInTheRangeException("Значение X не входит в интервал [-2,2]");
        }
        boolean entryValue = checkGetInto(checkDotRequest.getX(), checkDotRequest.getY(), checkDotRequest.getR());
        Date date = new Date();
        return new EntryEntity(checkDotRequest.getX(), checkDotRequest.getY(), checkDotRequest.getR(), entryValue, date);
    }


    public boolean checkGetInto(float x, float y, float r) {
        if (checkIntoTriangle(x, y, r) || checkIntoRectangle(x, y, r) || checkIntoCircle(x, y, r)) {
            return true;
        }
        return false;
    }

    public boolean checkIntoTriangle(float x, float y, float r) {
        if (x<=0 && y<=0){
            return -2*x-r<=y;
        }
        return false;
    }

    public boolean checkIntoRectangle(float x, float y, float r) {
        if (x>= 0 && y>=0){
            return (x <= r/2 && y <= r);
        }
        return false;
    }

    public boolean checkIntoCircle(float x, float y, float r) {
        if (x >= 0 && y <= 0){
            return (x*x+y*y <= (r/2)*(r/2));
        }
        return false;
    }
}
