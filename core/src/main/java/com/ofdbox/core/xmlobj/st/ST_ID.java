package com.ofdbox.core.xmlobj.st;

import lombok.Data;

import javax.validation.constraints.Min;
import java.util.Objects;

@Data
public class ST_ID {
    @Min(0)
    private Integer id;

}
