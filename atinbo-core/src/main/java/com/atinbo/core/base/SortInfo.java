package com.atinbo.core.base;

import com.atinbo.core.constants.SortDirection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 排序
 *
 * @author breggor
 */
@Data
public class SortInfo implements Serializable {

    private List<Field> orders = new ArrayList<>();

    private boolean isEmpty() {
        return this.orders.isEmpty();
    }

    /**
     * 加入排序字段
     *
     * @param direction
     * @param property
     * @return
     */
    public SortInfo addField(SortDirection direction, String property) {
        Field field = new Field(direction, property);
        if (!orders.contains(field)) {
            this.orders.add(field);
        }
        return this;
    }

    /**
     * 获取方向字段
     *
     * @param direction
     * @return
     */
    public List<String> get(SortDirection direction) {
        return orders.stream().filter(o -> o.direction == direction).map(Field::getProperty).collect(Collectors.toList());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(of = {"direction", "property"})
    public class Field implements Serializable {
        private SortDirection direction;
        private String property;
    }
}