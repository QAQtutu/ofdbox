package com.ofdbox.core.xmlobj.st;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

/**
 * @description: 矩形区域
 * @author: 张家尧
 * @create: 2020/10/01 11:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ST_Box {
    @NotNull
    private Double x;
    @NotNull
    private Double y;
    @NotNull
    private Double w;
    @NotNull
    private Double h;
}