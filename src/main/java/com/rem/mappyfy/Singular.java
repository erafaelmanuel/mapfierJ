package com.rem.mappyfy;

public class Singular extends Optional {

    public Singular(Object arg) {
        super(arg);
    }

    public <T> T mapTo(Class<T> c) {
        try {
            if (getNodes().size() != 0) {
                final Node node = getNodes().iterator().next();
                final Transaction<T> transaction = new Transaction<>(node);
                return transaction.mkInstance(c);
            } else {
                throw new RuntimeException("Error!");
            }
        } catch (RuntimeException e) {
            return null;
        }
    }

    public void mapTo(final Object o) {
        try {
            if (getNodes().size() != 0 && o != null) {
                final Node node = getNodes().iterator().next();
                final Transaction transaction = new Transaction(node);
                transaction.bind(o);
            } else {
                throw new RuntimeException("Error!");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public Singular bind(String from, String to) {
        for (Node node : getNodes()) {
            final Variable variable = node.getVariables().get(from);
            if (variable.getValue() != null) {
                node.getVariables().put(to, new Variable(variable.getType(), variable.getValue()));
                node.getVariables().remove(from);
                break;
            }
        }
        return this;
    }

    public Singular ignore(String fromField) {
        getNodes().iterator().next()
                .getVariables().remove(fromField);
        return this;
    }

    public Singular parseFieldWith(String fromField, TypeConverter typeConverter) {
        final Node node = getNodes().iterator().next();
        final Variable variable = node.getVariables().get(fromField);

        if (variable.getValue() != null) {
            final Object newValue = typeConverter.convert(variable.getValue());
            if (newValue != null) {
                variable.setValue(newValue);
                node.getVariables().put(fromField, variable);
            } else {
                node.getVariables().remove(fromField);
            }
        } else {
            node.getVariables().remove(fromField);
        }
        return this;
    }
}
