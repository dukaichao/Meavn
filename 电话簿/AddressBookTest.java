/**
 * Created With IntelliJ IDEA.
 * Descriptions:
 * User:Mr.Du
 * Date:2019-06-01
 * Time:18:43
 */

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 1.add
 *      1.1判断当前contact.xml文件是否为空，如果为空添加根标签<contact-list>添加子标签<contact   id属性>添加属性<name>
 *      1.2不为空直接添加相应的属性，输入编号要进行判断，不能和原有的重复，重复的话重新输入
 * 2.update
 *      根据输入的对应的id获取当前信息，打印到控制台，根据控制台信息修改要修改的数据，输入编号要进行判断，不能和原有的重复
 *      重复的话重新输入
 *
 * 3.del
 *      根据输入对应的id，获取当前信息，打印的控制台，根据Y/N删除
 *
 * 4.打印电话簿的所有信息
 */
public class AddressBookTest {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        System.out.println("1.添加\n" +"2.修改\n" + "3.删除\n" + "4.打印全部信息");
        while(true){
            int n = sc.nextInt();
            switch(n){
                case 1: add();
                    break;
                case 2: update();
                    break;
                case 3: del();
                    break;
                case 4: printMessage();
                    break;
                default:
                    System.out.println("请重新输入:");
            }
        }
    }

    public static void printMessage(){

    }

    private static void del() {
    }

    public static void update() throws Exception {
        Document doc = new SAXReader().read(new File("E:"+File.separator+"contact.xml"));
        System.out.println("请输入要更新机主的编号");
        boolean flag = true;
        Element conEle = doc.getRootElement();
        while(flag){
            String str = sc.next();
            if(isExit(str,conEle.elements("contact"))){
                flag = false;
            }else{
                System.out.println("输入编号不存在，请重新输入:");
            }
        }
        //打印当前编号机主的信息，
        List<Element> list = conEle.elements();
        System.out.println(list.size());
        for(Element elem : list){
            System.out.println(elem.getText());
        }
    }

    private static void add() throws Exception {
        Document doc = new SAXReader().read(new File("E:"+File.separator+"contact.xml"));
        //获取根标签的子标签集合
        List<Element> list = doc.getRootElement().elements();
        System.out.println(list.size());
        Element conElem = null;
        //添加属性
        boolean flag = true;
        System.out.println("请输入编号:");
        while(flag){
            String str = sc.next();
            if(isRepeat(str,doc.getRootElement().elements("contact"))){
                //创建contact标签
                conElem = DocumentHelper.createElement("contact");
                //将该标签加入第一位
                list.add(0,conElem);
                //输入对应id
                conElem.addAttribute("id",str) ;
                flag = false;
            }else{
                System.out.println("输入编号重复，请重新输入:");
            }
        }

        List<Element> list1 = conElem.elements();
        //在contact标签下继续新建其他属性标签
        System.out.println("请输入名字:");
        Element name = DocumentHelper.createElement("name");
        name.setText(sc.next());
        list1.add(name);

        System.out.println("请输入性别:");
        Element gender = DocumentHelper.createElement("gender");
        gender.setText(sc.next());
        list1.add(gender);

        System.out.println("请输入电话:");
        Element phone = DocumentHelper.createElement("phone");
        phone.setText(sc.next());
        list1.add(phone);

        System.out.println("请输入邮箱:");
        Element email = DocumentHelper.createElement("email");
        email.setText(sc.next());
        list1.add(email);

        System.out.println("请输入地址:");
        Element address = DocumentHelper.createElement("address");
        address.setText(sc.next());
        list1.add(address);



        //输出
        OutputStream out = new FileOutputStream("E:"+File.separator+"contact.xml") ;
        //输出格式:
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(out,format) ;
        writer.write(doc);
        writer.close();
    }

    /**
     *
     * @param s  输入的编号
     * @param list  获取当前电话簿所有Contact标签的集合
     * @return
     */
    public static boolean isRepeat(String s,List<Element> list){
        for(Element e : list){
            if(e.attributeValue("id").equals(s))
                return false;
        }
        return true;
    }

    public static boolean isExit(String s,List<Element> list){
        for(Element e : list){
            if(e.attributeValue("id").equals(s))
                return true;
        }
        return false;
    }

}
