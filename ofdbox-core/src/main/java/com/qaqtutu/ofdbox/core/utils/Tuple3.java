package com.qaqtutu.ofdbox.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class  Tuple3<X,Y,Z> {
    private X first;
    private Y second;
    private Z third;
}
