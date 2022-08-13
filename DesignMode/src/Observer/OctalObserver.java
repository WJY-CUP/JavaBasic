package Observer;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 11:39 2022/6/8
 * @E-mail: 15611562852@163.com
 */

public class OctalObserver extends Observer {
    public OctalObserver(Subject subject){
        this.subject = subject;
        this.subject.addObserver(this);
    }

    // 当观察的对象更新时，其也被通知更新
    @Override
    public void update() {
        System.out.println( "Octal String: " + Integer.toOctalString( subject.getState() ) );
    }


}
