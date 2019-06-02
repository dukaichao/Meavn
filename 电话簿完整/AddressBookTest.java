/**
 * Created With IntelliJ IDEA.
 * Descriptions:
 * User:Mr.Du
 * Date:2019-06-01
 * Time:18:43
 */

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;

/**
 * 1.add
 *      1.添加子标签<contact   id属性>添加属性<name>......输入编号要进行判断，不能和原有编号重复，重复重新输入
 * 2.update
 *      判断电话簿是否为空，为空提示添加联系人，不为空输入要修改的编号
 *      根据输入的对应的id获取当前信息，打印到控制台，输入编号要进行判断，判断该编号是否存在
 *
 * 3.del
 *      判断电话簿是否为空，为空提示添加联系人根据输入对应的id，获取当前信息，打印的控制台，根据Y/N确认是否删除
 *
 * 4.打印电话簿的所有信息
 */
public class AddressBookTest {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        while(true){
            System.out.println("1.添加\n" +"2.修改\n" + "3.删除\n" + "4.打印全部信息");
            int n = sc.nextInt();
            System.out.println("==========");
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

    public static void printMessage() throws Exception {
        Document doc = new SAXReader().read(new File("E:"+File.separator+"contact.xml"));
        if(isNull(doc)){
            System.out.println("电话簿为空，请添加联系人");
            System.out.println("============");
            return;
        }
        List<Element> list = doc.getRootElement().elements("contact");
        for(Element e : list){
            printContact(e);
        }
    }

    private static void del() throws Exception {
        Document doc = new SAXReader().read(new File("E:"+File.separator+"contact.xml"));
        if(isNull(doc)){
            System.out.println("电话簿为空，请添加联系人");
            System.out.println("============");
            return;
        }
        boolean flag = true;
        //获取对应编号的标签
        Element conEle = null;
        System.out.println("请输入删除编号:");
        while(flag){
            String str = sc.next();
            //返回对应编号标签
            conEle = isIDExit(str,doc.getRootElement().elements("contact"));
            if(conEle!=null){
                flag = false;
            }else{
                System.out.println("输入编号不存在，请重新输入:");
            }
        }
        printContact(conEle);
        flag = true;
        while(flag){
            System.out.println("删除请输入Y/y，否则输入N/n");
            String str = sc.next();
            if(str.compareToIgnoreCase("Y") == 0){
                conEle.detach();
                out(doc);
                System.out.println("已删除");
                System.out.println("============");
                return;
            }else if(str.compareToIgnoreCase("N") == 0){
                flag = false;
            }else{
                System.out.println("输入不正确，请重新输入");
            }
        }

    }

    public static void update() throws Exception {
        Document doc = new SAXReader().read(new File("E:"+File.separator+"contact.xml"));
        if(isNull(doc)){
            System.out.println("电话簿为空，请添加联系人");
            System.out.println("============");
            return;
        }
        System.out.println("请输入要更新机主的编号");
        boolean flag = true;
        //对应编号的标签
        Element conEle = null;
        while(flag){
            String str = sc.next();
            conEle = isIDExit(str,doc.getRootElement().elements("contact"));
            if(conEle!=null){
                //打印当前编号机主的信息，
                flag = false;
            }else{
                System.out.println("输入编号不存在，请重新输入:");
            }
        }
        //打印当前编号机主的信息，
        printContact(conEle);
        System.out.println("1.修改编号\n" +"2.修改名字\n" + "3.修改性别\n" + "4.修改电话\n"+"5.修改邮箱\n"+"6.修改地址");
        flag = true;
        while (flag){
            System.out.println("按照提示请自行修改");
            while(flag) {
                int n = sc.nextInt();
                switch (n) {
                    case 1:
                        System.out.println("请输入新的编号:");
                        while (flag) {
                            String str = sc.nextLine();
                            if (isIdRepeat(str, doc.getRootElement().elements("contact"))) {
                                up(conEle, "id");
                                flag = false;
                            } else {
                                System.out.println("输入编号重复，请重新输入:");
                            }
                        }
                        break;
                    case 2:
                        System.out.println("请输入新的名字:");
                        up(conEle, "name");
                        flag = false;
                        break;
                    case 3:
                        System.out.println("请输入新的性别:");
                        up(conEle, "gender");
                        flag = false;
                        break;
                    case 4:
                        System.out.println("请输入新的电话:");
                        up(conEle, "phone");
                        flag = false;
                        break;
                    case 5:
                        System.out.println("请输入新的邮箱:");
                        up(conEle, "email");
                        flag = false;
                        break;
                    case 6:
                        System.out.println("请输入新的地址:");
                        up(conEle, "address");
                        flag = false;
                        break;
                    default:
                        System.out.println("-------------------------");
                        System.out.println("1.修改编号\n" +"2.修改名字\n" + "3.修改性别\n" + "4.修改电话\n"+"5.修改邮箱\n"+"6.修改地址");
                        System.out.println("请按提示输入正确选项:");
                }
            }
            flag = true;
            while(flag){
                System.out.println("继续修改请输入Y/y，否则输入N/n");
                String str = sc.next();
                if(str.compareToIgnoreCase("Y") == 0){
                    break;
                }else if(str.compareToIgnoreCase("N") == 0){
                    System.out.println("修改完成");
                    flag = false;
                }else{
                    System.out.println("输入不正确，请重新输入");
                }
            }
        }
        out(doc);
        System.out.println("============");
    }

    private static void add() throws Exception {
        Document doc = new SAXReader().read(new File("E:"+File.separator+"contact.xml"));
        //获取根标签的子标签集合
        List<Element> list = doc.getRootElement().elements();
        Element conElem = null;
        //添加属性
        boolean flag = true;
        System.out.println("请输入编号:");
        while(flag){
            String str = sc.next();
            if(isIdRepeat(str,doc.getRootElement().elements("contact"))){
                //创建contact标签
                conElem = DocumentHelper.createElement("contact");
                //将该标签插入第一位
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
        //进行输出
        out(doc);
        System.out.println("添加成功");
        System.out.println("============");
    }

    /**
     *
     * @param s  输入的编号
     * @param list  获取当前电话簿所有Contact标签的集合
     * @return
     */
    //判断编号是否重复
    public static boolean isIdRepeat(String s, List<Element> list){
        for(Element e : list){
            if(e.attributeValue("id").equals(s))
                return false;
        }
        return true;
    }

    //判断编号是否存在
    public static Element isIDExit(String s, List<Element> list){
        for(Element e : list){
            if(e.attributeValue("id").equals(s))
                return e;
        }
        return null;
    }

    //根据所传参数进行修改信息
    public static void up(Element conEle,String name){
        //通过属性名称获取属性
        if("id".equals(name)) {
            Attribute Attr = conEle.attribute(name);
            Attr.setValue(sc.next());
        }else{
            Element elem = conEle.element(name);
            elem.setText(sc.next());
        }
    }

    //输出到E:/contact.xml
    public static void out(Document doc) throws Exception {
        //输出
        OutputStream out = new FileOutputStream("E:"+File.separator+"contact.xml") ;
        //输出格式:
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(out,format) ;
        writer.write(doc);
        writer.close();
    }

    //打印对应编号的信息
    public static void printContact(Element conEle){
        //打印当前编号机主的信息，
        List<Element> list = conEle.elements();
        //获取id属性值
        Attribute idAttr = conEle.attribute("id");
        System.out.println(idAttr.getName()+":"+idAttr.getValue());
        for(Element e : list)
            System.out.println(e.getName()+":"+e.getText());
        System.out.println("------------");
    }

    //判断文档是否存有联系人
    public static boolean isNull(Document doc){
        List<Element> list = doc.getRootElement().elements("contact");
        if(list.size() == 0){
            return true;
        }
        return false;
    }

}