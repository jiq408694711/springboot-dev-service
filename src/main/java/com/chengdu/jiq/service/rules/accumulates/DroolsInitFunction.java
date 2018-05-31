package com.chengdu.jiq.service.rules.accumulates;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created by jiyiqin on 2018/5/5.
 */
public class DroolsInitFunction implements org.kie.api.runtime.rule.AccumulateFunction<DroolsInitFunction.AverageData> {

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }

    public void writeExternal(ObjectOutput out) throws IOException {

    }

    public static class AverageData implements Externalizable {
        public int count = 0;

        public AverageData() {
        }

        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            count = in.readInt();
        }

        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeInt(count);
        }

    }

    /* (non-Javadoc)
     * @see org.kie.api.runtime.engine.AccumulateFunction#createContext()
     */
    public AverageData createContext() {
        return new AverageData();
    }

    /* (non-Javadoc)
     * @see org.kie.api.runtime.engine.AccumulateFunction#init(java.io.Serializable)
     */
    public void init(AverageData context) {
        context.count = 0;
    }

    /* (non-Javadoc)
     * @see org.kie.api.runtime.engine.AccumulateFunction#accumulates(java.io.Serializable, java.lang.Object)
     */
    public void accumulate(AverageData context, Object value) {
        context.count++;
    }

    /* (non-Javadoc)
     * @see org.kie.api.runtime.engine.AccumulateFunction#reverse(java.io.Serializable, java.lang.Object)
     */
    public void reverse(AverageData context, Object value) {
        context.count--;
    }

    /* (non-Javadoc)
     * @see org.kie.api.runtime.engine.AccumulateFunction#getResult(java.io.Serializable)
     */
    public Object getResult(AverageData context) {
        return context.count;
    }

    /* (non-Javadoc)
     * @see org.kie.api.runtime.engine.AccumulateFunction#supportsReverse()
     */
    public boolean supportsReverse() {
        return true;
    }

    /* (non-Javadoc)
     * @see org.kie.api.runtime.engine.AccumulateFunction#getResultType()
     */
    public Class<?> getResultType() {
        return Number.class;
    }

}