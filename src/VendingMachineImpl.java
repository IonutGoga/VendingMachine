import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class VendingMachineImpl implements VendingMachine {

    private Inventory<Coin> cashInventory = new Inventory<Coin>();
    private Inventory<Item> itemInventory = new Inventory<Item>();
    private long totalSales;
    private Item currentItem;
    private long currentBalance;

    public VendingMachineImpl() {
        initialize();
    }

    private void initialize() {
        for (Coin c : Coin.values()) {
            cashInventory.put(c, 5);
        }

        for (Item i : Item.values()) {
            itemInventory.put(i, 5);
        }
    }

    @Override
    public long selectItemAndGetPrice(Item item) {
        if (itemInventory.hasItem(item)) {
            currentItem = item;
            return currentItem.getPrice();
        }
        throw new SoldOutException("Sold Out, Please buy another item");
    }

    @Override
    public void insertCoin(Coin coin) {
        currentBalance = currentBalance + coin.getValue();
        cashInventory.add(coin);
    }

    @Override
    public PurchaseAndCoins<Item, List<Coin>> collectItemAndChange() {
        Item item = collectItem();
        totalSales = totalSales + currentItem.getPrice();

        List<Coin> change = collectChange();

        return new PurchaseAndCoins<Item, List<Coin>>(item, change);
    }

    private Item collectItem() throws NotSufficientChangeException,
            NotFullPaidException {
        if (isFullPaid()) {
            if (hasSufficientChange()) {
                itemInventory.decrease(currentItem);
                return currentItem;
            }
            throw new NotSufficientChangeException("Not Sufficient change in Inventory");

        }
        long remainingBalance = currentItem.getPrice() - currentBalance;
        throw new NotFullPaidException("Price not full paid, remaining : ", remainingBalance);
    }

    private List<Coin> collectChange() {
        long changeAmount = currentBalance - currentItem.getPrice();
        List<Coin> change = getChange(changeAmount);
        updateCashInventory(change);
        currentBalance = 0;
        currentItem = null;
        return change;
    }

    @Override
    public List<Coin> refund() {
        List<Coin> refund = getChange(currentBalance);
        updateCashInventory(refund);
        currentBalance = 0;
        currentItem = null;
        return refund;
    }


    private boolean isFullPaid() {
        if (currentBalance >= currentItem.getPrice()) {
            return true;
        }
        return false;
    }


    private List<Coin> getChange(long amount) throws NotSufficientChangeException {
        List<Coin> changes = Collections.EMPTY_LIST;

        if (amount > 0) {
            changes = new ArrayList<Coin>();
            long balance = amount;
            while (balance > 0) {
                if (balance >= Coin.FIFTY.getValue()
                        && cashInventory.hasItem(Coin.FIFTY)) {
                    changes.add(Coin.FIFTY);
                    balance = balance - Coin.FIFTY.getValue();
                    continue;

                } else if (balance >= Coin.TWENTY.getValue()
                        && cashInventory.hasItem(Coin.TWENTY)) {
                    changes.add(Coin.TWENTY);
                    balance = balance - Coin.TWENTY.getValue();
                    continue;

                } else if (balance >= Coin.TEN.getValue()
                        && cashInventory.hasItem(Coin.TEN)) {
                    changes.add(Coin.TEN);
                    balance = balance - Coin.TEN.getValue();
                    continue;

                } else if (balance >= Coin.FIVE.getValue()
                        && cashInventory.hasItem(Coin.FIVE)) {
                    changes.add(Coin.FIVE);
                    balance = balance - Coin.FIVE.getValue();
                    continue;

                } else {
                    throw new NotSufficientChangeException("NotSufficientChange, Please try another product");
                }
            }
        }

        return changes;
    }

    @Override
    public void reset() {
        cashInventory.clear();
        itemInventory.clear();
        totalSales = 0;
        currentItem = null;
        currentBalance = 0;
    }

    public void printStats() {
        System.out.println("Total Sales : " + totalSales);
        System.out.println("Current Item Inventory : " + itemInventory);
        System.out.println("Current Cash Inventory : " + cashInventory);
    }


    private boolean hasSufficientChange() {
        return hasSufficientChangeForAmount(currentBalance - currentItem.getPrice());
    }

    private boolean hasSufficientChangeForAmount(long amount) {
        boolean hasChange = true;
        try {
            getChange(amount);
        } catch (NotSufficientChangeException nsce) {
            return hasChange = false;
        }

        return hasChange;
    }

    private void updateCashInventory(List<Coin> change) {
        for (Coin c : change) {
            cashInventory.decrease(c);
        }
    }

    public long getTotalSales() {
        return totalSales;
    }
}

