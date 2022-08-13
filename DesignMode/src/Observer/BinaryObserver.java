package Observer;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 11:38 2022/6/8
 * @E-mail: 15611562852@163.com
 */

public class BinaryObserver extends Observer{


    public BinaryObserver(Subject subject){
        this.subject = subject;
        this.subject.addObserver(this);
    }

    // 当观察的对象更新时，其也被通知更新
    @Override
    public void update() {
        System.out.println( "Binary String: " + Integer.toBinaryString( subject.getState() ) );
    }
}
