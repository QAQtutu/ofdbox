package com.ofdbox.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tuple2<X,Y> {
    private X first;
    private Y second;
}
