package com.food.repository.base;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.Selection;
import java.util.List;


@Getter
@Setter
@Builder
public class MultipleField {
    private List<String> select;
    private Selection<?> selection;
}
