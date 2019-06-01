public class PurchaseAndCoins<T1, T2> {

    private T1 item;
    private T2 coins;

    public PurchaseAndCoins(T1 item, T2 coins){
        this.item = item;
        this.coins = coins;
    }

    public T1 getItem(){
        return item;
    }

    public T2 getCoins(){
        return coins;
    }

}
