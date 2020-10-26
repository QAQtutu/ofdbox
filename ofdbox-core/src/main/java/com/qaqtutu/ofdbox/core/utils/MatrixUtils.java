package com.qaqtutu.ofdbox.core.utils;

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;

import java.awt.geom.AffineTransform;

public class MatrixUtils {

    public static Matrix base() {
        Matrix matrix = DenseMatrix.Factory.zeros(3, 3);
        matrix.setAsDouble(1.0D, 0, 0);
        matrix.setAsDouble(1.0D, 1, 1);
        matrix.setAsDouble(1.0D, 2, 2);
        return matrix;
    }

    public static Matrix create(double d1, double d2, double d3, double d4, double d5, double d6) {
        Matrix matrix = base();
        matrix.setAsDouble(d1, 0, 0);
        matrix.setAsDouble(d2, 0, 1);
        matrix.setAsDouble(d3, 1, 0);
        matrix.setAsDouble(d4, 1, 1);
        matrix.setAsDouble(d5, 2, 0);
        matrix.setAsDouble(d6, 2, 1);
        return matrix;
    }

    public static Matrix scale(Matrix matrix,double x,double y){
        return MatrixUtils.create(x,0,0,y,0,0).mtimes(matrix);
    }

    public static AffineTransform createAffineTransform(Matrix matrix) {
        return new AffineTransform(matrix.getAsFloat(0, 0), matrix.getAsFloat(0, 1), matrix.getAsFloat(1, 0), matrix.getAsFloat(1, 1), matrix.getAsFloat(2, 0), matrix.getAsFloat(2, 1));
    }

    //外接矩形的左上角
    public static Tuple2<Double, Double> leftTop(Matrix matrix) {
        Matrix m = MatrixUtils.base();
        m.setAsFloat(matrix.getAsFloat(0, 0), 0, 0);
        m.setAsFloat(matrix.getAsFloat(0, 1), 0, 1);
        m.setAsFloat(matrix.getAsFloat(1, 0), 1, 0);
        m.setAsFloat(matrix.getAsFloat(1, 1), 1, 1);
        m.setAsFloat(matrix.getAsFloat(2, 0), 2, 0);
        m.setAsFloat(matrix.getAsFloat(2, 1), 2, 1);
        m.setAsFloat(1, 2, 2);


        double x = 0, y = 0;
        Tuple2<Double, Double> tuple = leftTop(m, 0, 0);
        x = tuple.getFirst();
        y = tuple.getSecond();
        leftTop(m, 1, 0);
        if (tuple.getFirst() < x) {
            x = tuple.getFirst();
        }
        if (tuple.getSecond() < y) {
            y = tuple.getSecond();
        }
        leftTop(m, 1, 1);
        if (tuple.getFirst() < x) {
            x = tuple.getFirst();
        }
        if (tuple.getSecond() < y) {
            y = tuple.getSecond();
        }
        leftTop(m, 0, 1);
        if (tuple.getFirst() < x) {
            x = tuple.getFirst();
        }
        if (tuple.getSecond() < y) {
            y = tuple.getSecond();
        }

        return new Tuple2<>(x, y);
    }

    private static Tuple2<Double, Double> leftTop(org.ujmp.core.Matrix matrix, float x, float y) {
        org.ujmp.core.Matrix m = DenseMatrix.Factory.zeros(1, 3);
        m.setAsFloat(x, 0, 0);
        m.setAsFloat(x, 0, 1);
        m.setAsFloat(1, 0, 2);

        org.ujmp.core.Matrix result = m.mtimes(matrix);
        return new Tuple2<>(result.getAsDouble(0, 0), result.getAsDouble(0, 1));
    }

}
