package fairyShop.models;

import java.util.ArrayDeque;
import java.util.Collection;

public class ShopImpl implements Shop{


    @Override
    public void craft(Present present, Helper helper) {
        ArrayDeque<Instrument> helperInstruments = new ArrayDeque<>(helper.getInstruments());
        Instrument instrument = helperInstruments.poll();
        while (helper.canWork()&& !present.isDone()&&instrument!=null){
            while (!instrument.isBroken()){
                instrument.use();
                helper.work();
                present.getCrafted();
                if (present.isDone()){
                    break;
                }
            }
            instrument = helperInstruments.poll();
        }

    }


}
