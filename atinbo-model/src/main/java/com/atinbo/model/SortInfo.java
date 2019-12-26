package com.atinbo.model;

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

    private static final long serialVersionUID = 1L;

    private List<Field> orders = new ArrayList<>();

    public boolean isEmpty() {
        return this.orders.isEmpty();
    }

    /**
     * 加入排序字段
     *
     * @param direction
     * @param property
     * @return
     */
    public SortInfo addField(SortDir direction, String property) {
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
    public List<String> get(SortDir direction) {
        return orders.stream().filter(o -> o.direction == direction).map(Field::getProperty).collect(Collectors.toList());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(of = {"direction", "property"})
    public class Field implements Serializable {
        private SortDir direction;
        private String property;
    }
}
