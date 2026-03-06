package com.oceanviewresort.dao;

import com.oceanviewresort.model.Discount;
import java.util.List;

public interface DiscountDAO {
    List<Discount> getActiveDiscounts();
}