package io.ermdev.mapfierj.sample.sample4;

import io.ermdev.mapfierj.TypeException;
import io.ermdev.mapfierj.TypeConverterAdapter;

public class SampleConverter extends TypeConverterAdapter<Integer, String> {

    public SampleConverter(String s) {}

    @Override
    public String convertTo(Integer o) throws TypeException {
        try {
            return String.valueOf(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Integer convertFrom(String o) throws TypeException {
        try {
            return Integer.parseInt(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}