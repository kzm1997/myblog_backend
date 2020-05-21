package com.kzm.blog.common.entity.User;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Comparator;

@Data
public class TimeComparator  implements Comparable<TimeComparator> {

     LocalDateTime updateTime;


    @Override
    public int compareTo(TimeComparator o) {
        return o.updateTime.compareTo(this.updateTime);
    }
}
