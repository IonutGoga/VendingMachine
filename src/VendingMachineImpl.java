import java.util.List;

public class VendingMachineImpl implements VendingMachine {

    private Inventory<Coin> cashInventory = new Inventory<Coin>();
    private Inventory<Item> itemInventory = new Inventory<Item>();

    private long totalSales;
    private Item currentItem;
    private long currentBalance;

    public VendingMachineImpl(){
        initialize();
    }

    private void initialize(){
        for(Coin c : Coin.values()){
            cashInventory.put(c, 5);
        }

        for(Item i : Item.values()){
            itemInventory.put(i, 5);
        }
    }

    @Override
    public long selectItemAndGetPrice(Item item) {
        if(itemInventory.hasItem()){
            currentItem = item;
            return currentItem.getPrice();
        }
        throw new SoldOutException("Sold out !!!");
    }

    @Override
    public void insertCoin(Coin coin) {
        currentBalance = currentBalance + coin.getValue();
        cashInventory.add(coin);
    }

    @Override
    public List<Coin> refund() {
        return null;
    }

    @Override
    public PurchaseAndCoins<Item, List<Coin>> collectItemAndChange() {
        return null;
    }

    @Override
    public void reset() {

    }

    boolean isFullPaid(){
        if(currentBalance >= currentItem.getPrice()){
            return true;
        }
        return false;
    }

}
