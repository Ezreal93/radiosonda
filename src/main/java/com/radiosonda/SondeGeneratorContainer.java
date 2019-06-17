package com.radiosonda;

import com.radiosonda.serialrx.SondeData;
import com.radiosonda.serialrx.SondeDataGenerator;
import io.reactivex.Flowable;
import io.reactivex.internal.operators.flowable.FlowableInterval;
import io.reactivex.schedulers.Schedulers;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class SondeGeneratorContainer {
    public static FlowableInterval interval;
    public static Flowable<SondeData> sondeData;

    public static Flowable<SondeData> createSondeFlowable() {
        if(sondeData == null) {
            SondeDataGenerator sondeDataGenerator = new SondeDataGenerator();
            interval = new FlowableInterval(1, 1, TimeUnit.SECONDS, Schedulers.single());
            sondeData = interval.map(aLong -> LocalTime.now())
                    .map(localTime -> sondeDataGenerator.generate());
        }
        return sondeData;
    }
}
