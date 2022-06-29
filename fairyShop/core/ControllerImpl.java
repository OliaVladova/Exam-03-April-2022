package fairyShop.core;

import fairyShop.common.ConstantMessages;
import fairyShop.common.ExceptionMessages;
import fairyShop.models.*;
import fairyShop.repositories.HelperRepository;
import fairyShop.repositories.PresentRepository;
import fairyShop.repositories.Repository;

import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private Repository<Helper> helpers;
    private Repository<Present> presents;
    private Shop shop;

    public ControllerImpl() {
        this.helpers = new HelperRepository();
        this.presents = new PresentRepository();
        this.shop = new ShopImpl();
    }

    @Override
    public String addHelper(String type, String helperName) {
        Helper helper = null;
        switch (type) {
            case "Happy":
                helper = new Happy(helperName);
                break;
            case "Sleepy":
                helper = new Sleepy(helperName);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.HELPER_TYPE_DOESNT_EXIST);
        }
        this.helpers.add(helper);

        return String.format(ConstantMessages.ADDED_HELPER, type, helperName);
    }

    @Override
    public String addInstrumentToHelper(String helperName, int power) {
        Instrument instrument = new InstrumentImpl(power);
        Helper helper = this.helpers.findByName(helperName);
        if (helper == null) {
            throw new IllegalArgumentException(ExceptionMessages.HELPER_DOESNT_EXIST);
        }
        this.helpers.findByName(helperName).addInstrument(instrument);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_INSTRUMENT_TO_HELPER, power, helperName);
    }

    @Override
    public String addPresent(String presentName, int energyRequired) {
        Present present = new PresentImpl(presentName, energyRequired);
        this.presents.add(present);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_PRESENT, presentName);
    }

    @Override
    public String craftPresent(String presentName) {
       int brokenInstr = 0;
        String done = "";
        Present present = this.presents.findByName(presentName);
        List<Helper> suitable = this.helpers.getModels().stream().filter(helper -> helper.getEnergy() > 50).collect(Collectors.toList());
        if (suitable.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.NO_HELPER_READY);
        }
        if (present != null) {
            for (Helper helper : suitable) {
                this.shop.craft(present, helper);
                List<Instrument> brokenInstruments = helper.getInstruments().stream().filter(Instrument::isBroken).collect(Collectors.toList());
                brokenInstr+=brokenInstruments.size();
            }
            if (present.isDone()){
                done = "done";
            }else {
                done = "not done";
            }

        }


        return String.format(ConstantMessages.PRESENT_DONE + ConstantMessages.COUNT_BROKEN_INSTRUMENTS,presentName,done,brokenInstr);
    }

    @Override
    public String report() {

        StringBuilder builder = new StringBuilder();
        long crafted = this.presents.getModels().stream().filter(Present::isDone).count();
        builder.append(String.format("%d presents are done!",crafted)).append(System.lineSeparator())
                .append("Helpers info:").append(System.lineSeparator());
        for (Helper helper:this.helpers.getModels()) {
            long brokenInstr = helper.getInstruments().stream().filter(instrument -> !instrument.isBroken()).count();
                    builder.append(String.format("Name: %s",helper.getName())).append(System.lineSeparator())
                    .append(String.format("Energy: %d",helper.getEnergy())).append(System.lineSeparator())
                    .append(String.format("Instruments: %d not broken left", brokenInstr)).append(System.lineSeparator());
        }
        return builder.toString().trim();
    }
}
