package fairyShop.models;

public class Sleepy extends BaseHelper {
    private static final int ENERGY = 50;

    public Sleepy(String name) {
        super(name, ENERGY);
    }
    @Override
    public void work(){
        if (this.getEnergy()-5<0){
            this.setEnergy(0);
        }else {
            this.setEnergy(this.getEnergy()-5);
        }
    }
}
