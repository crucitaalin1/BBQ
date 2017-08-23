package alin.bbq;

/**
 * Created by alinp on 01/08/2017.
 */

public interface DataProgress {

    void onGet();
    void onSuccess(String weather_list);
    void onFailed();
}
