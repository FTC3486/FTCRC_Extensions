package com.FTC3486.FTCRC_Extensions;

import java.util.ArrayList;

/**
 * Created by Matthew on 3/10/2016.
 */
public class Statistician {

    public static double get_mean_value(ArrayList<Double> dataList) {
        double sum = 0;
        for(Double data: dataList) {
            sum += data;
        }
        return (double) sum/dataList.size();
    }

    public static double get_standard_deviation(ArrayList<Double> dataList) {
        double difference = 0;
        for (Double data : dataList) {
            difference += (data - get_mean_value(dataList));
        }
        double variance = (difference) * (difference);
        return Math.sqrt(variance);
    }

    public static ConfidenceInterval construct_confidence_interval_from_data_list(ArrayList<Double> dataList) {
        double fenceOne = ( get_mean_value(dataList) + ((2.01)*((get_standard_deviation(dataList)) /
                (Math.sqrt(dataList.size())))) );
        double fenceTwo = ( get_mean_value(dataList) - ((2.01)*((get_standard_deviation(dataList)) /
                (Math.sqrt(dataList.size())))) );

        if(fenceOne > fenceTwo) {
            ConfidenceInterval tInterval = new ConfidenceInterval(fenceOne, fenceTwo);
            return tInterval;
        } else {
            ConfidenceInterval tInterval = new ConfidenceInterval(fenceTwo, fenceOne);
            return tInterval;
        }
    }

    public static class ConfidenceInterval {
        public double upperBound;
        public double lowerBound;

        public ConfidenceInterval(double upperBound, double lowerBound) {
            this.upperBound = upperBound;
            this.lowerBound = lowerBound;
        }
    }
}
