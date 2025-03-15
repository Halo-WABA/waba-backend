package com.halo.eventer.domain.widget.feature;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
public class DisplayOrderFeature {

    @Column(name = "display_order")
    private Integer displayOrder;

    private DisplayOrderFeature(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public static DisplayOrderFeature of(Integer displayOrder) {
        return new DisplayOrderFeature(displayOrder);
    }

}
