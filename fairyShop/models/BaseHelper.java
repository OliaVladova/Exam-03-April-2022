package fairyShop.models;

import fairyShop.common.ExceptionMessages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public abstract class BaseHelper implements Helper{
    private String name;
    private int energy;
    private Collection<Instrument> instruments;


    public BaseHelper(String name, int energy) {
        this.setName(name);
        this.setEnergy(energy);
        this.instruments = new ArrayList<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        if (name==null||name.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.HELPER_NAME_NULL_OR_EMPTY);
        }else {
            this.name = name;
        }
    }

    @Override
    public int getEnergy() {
        return this.energy;
    }

    protected void setEnergy(int energy) {
        if (energy < 0) {
            throw new IllegalArgumentException(ExceptionMessages.HELPER_ENERGY_LESS_THAN_ZERO);
        } else {
            this.energy = energy;
        }
    }

    @Override
    public Collection<Instrument> getInstruments() {
        return Collections.unmodifiableCollection(this.instruments);
    }

    public void setInstruments(Collection<Instrument> instruments) {
        this.instruments = instruments;
    }

    @Override
    public void work() {
        if (this.getEnergy()-10<=0){
            this.energy = 0;
        }else {
            this.setEnergy(this.getEnergy()-10);
        }
    }

    @Override
    public void addInstrument(Instrument instrument) {
        this.instruments.add(instrument);
    }

    @Override
    public boolean canWork() {
        if (this.getEnergy()>0){
            return true;
        }
        return false;
    }
}
