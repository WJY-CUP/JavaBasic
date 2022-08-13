package Observer;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 11:35 2022/6/8
 * @E-mail: 15611562852@163.com
 */

public class Subject {

    private List<Observer> observers = new ArrayList<Observer>();
    private int state;

    public int getState() {
        return state;
    }

    // 当对象更新时，会通知观察它的所有对象也更新
    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    // 增加一个观察者
    public void addObserver(Observer observer){
        this.observers.add(observer);
    }

    // 删除一个观察者
    public void delObserver(Observer obs) {
        this.observers.remove(obs);
    }

    // 通知所有观察者
    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }




}
