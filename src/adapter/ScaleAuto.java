package adapter;

public interface ScaleAuto {
    public void syncUpdateOptionSetName(int threadNum, String key, String opsetName, String newOpsetName);
    public void syncUpdateOptionName(int threadNum, String key, String opsetName, String opName, String newOpName);
    public void syncUpdateOptionPrice(int threadNum, String key, String opsetName, String opName, float newPrice);
}
