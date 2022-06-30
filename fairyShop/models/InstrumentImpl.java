package fairyShop.models;

import fairyShop.common.ExceptionMessages;

public class InstrumentImpl implements Instrument {
    private int power;

    public InstrumentImpl(int power) {
        this.setPower(power);
    }

    protected void setPower(int power) {
        if (power < 0) {
            throw new IllegalArgumentException(ExceptionMessages.INSTRUMENT_POWER_LESS_THAN_ZERO);
        } else {
            this.power = power;
        }
    }

    @Override
    public int getPower() {
        return this.power;
    }

    @Override
    public void use() {
        if (this.getPower() - 10 <= 0) {
            this.power = 0;
        }else {
            this.power = this.getPower() - 10;
        }
    }

    @Override
    public boolean isBroken() {
        if (this.getPower()==0){
            return true;
        }
        return false;
    }
}
