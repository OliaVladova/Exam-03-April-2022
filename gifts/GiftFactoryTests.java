package gifts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GiftFactoryTests {
    private Gift giftIn;
    private Gift giftToAdd;
    private GiftFactory giftFactory;

    @Before
    public void setUp() {
        this.giftIn = new Gift("Doll", 10);
        this.giftToAdd = new Gift("Bear", 10);
        this.giftFactory = new GiftFactory();
        this.giftFactory.createGift(giftIn);
    }

    @Test
    public void testShouldReturnCountCorrectly() {
        this.giftFactory.createGift(giftToAdd);
        int size = this.giftFactory.getCount();
        Assert.assertEquals(2, size);
    }

    @Test
    public void testShouldAddCorrectly() {
        this.giftFactory.createGift(giftToAdd);
        int size = this.giftFactory.getCount();
        Assert.assertEquals(2, size);
    }

    @Test
    public void testShouldAddCorrectlyMessage() {
        String returned = this.giftFactory.createGift(giftToAdd);
        String other = "Successfully added gift Bear with magic 10.00.";
        String expected = "Successfully added gift Bear with magic 10,00.";
        Assert.assertEquals(other, returned);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowExceptionWhenGiftIsPresent() {
        String returned = this.giftFactory.createGift(giftToAdd);
        this.giftFactory.createGift(giftIn);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowExceptionWhenGiftIsPresentInShop() {
        String returned = this.giftFactory.createGift(giftIn);
        String expected = "gifts. Gift with name Doll already exists.";
        Assert.assertEquals(expected,returned);
    }

    @Test
    public void testShouldRemoveGiftCorrectly() {
        this.giftFactory.createGift(giftToAdd);
        boolean returned = this.giftFactory.removeGift("Doll");
        Assert.assertTrue(returned);
    }

    @Test
    public void testShouldReturnFalse() {
        this.giftFactory.createGift(giftToAdd);
        boolean returned = this.giftFactory.removeGift("Horse");
        Assert.assertFalse(returned);
    }

    @Test(expected = NullPointerException.class)
    public void testShouldThrowExceptionWhenNameIsNull() {
        this.giftFactory.createGift(giftToAdd);
        this.giftFactory.removeGift(null);
    }

    @Test
    public void testShouldGetPresentWithLeastMagicCorrectly() {
        this.giftFactory.createGift(giftToAdd);
        Gift newGift = new Gift("Horse", 5);
        this.giftFactory.createGift(newGift);
        Gift returned = this.giftFactory.getPresentWithLeastMagic();
        Assert.assertEquals(newGift, returned);
    }

    @Test
    public void testShouldReturnNullWhenGettingPresentWithLeastMagic() {
        this.giftFactory.removeGift("Doll");
        Gift newGift = null;
        Gift returned = this.giftFactory.getPresentWithLeastMagic();
        Assert.assertEquals(newGift, returned);
    }
    @Test
    public void testShouldGetPresentCorrectly() {
        this.giftFactory.createGift(giftToAdd);
        Gift gift = this.giftFactory.getPresent("Doll");
        Assert.assertEquals(giftIn,gift);
    }
    @Test
    public void testShouldReturnNullIfThereIsNoPresent() {
        this.giftFactory.removeGift("Doll");
        Gift gift = this.giftFactory.getPresent("Doll");
        Assert.assertNull(gift);
    }
    @Test(expected = UnsupportedOperationException.class)
    public  void testShouldThrowExceptionWhenModifying(){
        this.giftFactory.getPresents().clear();
    }
   /* @Test
    public  void testShouldReturnPresents(){
        this.giftFactory.createGift(giftToAdd);
        Collection<Gift>returned = this.giftFactory.getPresents();
        Collection<Gift>expected = new ArrayList<>();
        expected.add(giftIn);
        expected.add(giftToAdd);
        expected =Collections.unmodifiableCollection(expected);
        Assert.assertEquals(expected,returned);
    }*/

}
