//atm系统入口类

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATMSystem {

    public static void main(String[] args) {
        //定义账户类

        //定义用于存储账户对象的集合容器
        ArrayList<Account> accounts = new ArrayList<>();
        //系统首页
        Scanner sc = new Scanner(System.in);
        System.out.println("请您选择操作");
        while (true) {
            System.out.println("==========ATM系统==========");
            System.out.println("1.账户登录");
            System.out.println("2.账户开户");


            int command = sc.nextInt();

            switch (command){
                case 1:
                    //登录
                    login(accounts,sc);

                    break;
                case 2:
                    //开户
                    register(accounts,sc);
                    break;
                default:
                    System.out.println("对不起，您输入的命令不存在！");


            }
        }
    }
//登录
    private static void login(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("==========系统登录操作==========");
        //判断是否存在账户，如果没有账户，无法登录
        if(accounts.size() == 0){
            System.out.println("对不起，系统中无任何账户，请先开户");
            return;
        }
        //有账户，正式登陆
        while (true) {
            System.out.println("请您输入账户卡号");
            String cardId = sc.next();
            //判断该卡号是否存在
            Account acc = getAccountByCardId(cardId,accounts);
            if(acc != null){
                //卡号存在，继续登录，判断密码
                while (true) {
                    System.out.println("请您输入账户密码");
                    String passWord = sc.next();
                    if(acc.getPassWord().equals(passWord)){
                        //密码正确，登陆成功
                        System.out.println("恭喜您，" + acc.getUserName() + "进入系统");
                        //展示用户登陆后的操作页面
                        showUserCommand(sc,acc,accounts);
                        break;
                    }else{
                        System.out.println("对不起，您输入的密码有误");
                    }
                }
            }else{
                //卡号不存在，重新输入卡号
                System.out.println("对不起，该卡号不存在");
            }
        }
    }
//展示登陆成功后的操作页面
    private static void showUserCommand(Scanner sc,Account acc,ArrayList<Account> accounts) {
        while (true) {
            System.out.println("==========用户操作页面==========");
            System.out.println("1.查询账户");
            System.out.println("2.存款");
            System.out.println("3.取款");
            System.out.println("4.转账");
            System.out.println("5.修改密码");
            System.out.println("6.退出");
            System.out.println("7.注销账户");
            System.out.println("请选择：");
            int command = sc.nextInt();
            switch (command){
                case 1:
                    //查询账户，展示当前登录成功的账户信息
                    showAccount(acc);
                    break;
                case 2:
                    //存款
                    depositMoney(acc,sc);
                    break;
                case 3:
                    //取款
                    drawMoney(acc,sc);
                    break;
                case 4:
                    //转账
                    transfermoney(acc,sc,accounts);
                    break;
                case 5:
                    //修改密码
                    updatePassWord(sc,acc);
                    return;
                case 6:
                    //退出
                    System.out.println("退出成功，欢迎下次光临");
                    return;
                case 7:
                    //注销账户
                    accounts.remove(acc);
                    System.out.println("您的账户销户完成");
                    break;
                default:
                    System.out.println("您输入的操作命令不正确，请重新输入");
            }
        }
    }
//修改密码方法
    private static void updatePassWord(Scanner sc, Account acc) {
        System.out.println("==========用户密码修改==========");

        while (true) {
            System.out.println("请您输入当前密码");
            String passWord = sc.next();
            //判断密码是否正确
            if(acc.getPassWord().equals(passWord)){
                while (true) {
                    //密码正确，输入新密码
                    System.out.println("请您输入新密码");
                    String newPassWord = sc.next();
                    System.out.println("请您再次输入新密码");
                    String okNewPassWord = sc.next();
                    if(newPassWord.equals(okNewPassWord)){
                        //两次密码一致，可以修改
                        acc.setPassWord(newPassWord);
                        System.out.println("恭喜您，您的密码修改成功");
                        return;
                    }else{
                        //不一致，重新输入
                        System.out.println("您两次输入的密码不一致，请重新输入");
                    }
                }
            }else{
                //密码错误
                System.out.println("您输入的密码不正确");
            }
        }
    }

    //转账
    private static void transfermoney(Account acc, Scanner sc,ArrayList<Account> accounts) {
        System.out.println("==========用户转账操作==========");
        //判断是否有两个账户，满足转账条件
        if(accounts.size() < 2){
            System.out.println("对不起，当前系统中不足两个账户，无法转账，请先开户");
            return;
        }
        //账户足够，开始转账
        //判断账户是否有钱
        if(acc.getMoney() == 0){
            System.out.println("对不起，您没钱");
            return;
        }

        while (true) {
            //有钱，可以转账了
            System.out.println("请您输入对方账户的卡号");
            String cardId = sc.next();

            //判断卡号是否存在
            Account account = getAccountByCardId(cardId,accounts);
            if(account == null){
                System.out.println("对不起，您输入的卡号不存在");
            }else{
                //账户对象存在，认证姓氏
                String userName = account.getUserName();
                String tip = "*" + userName.substring(1);
                System.out.println("请您输入["+ tip + "]的姓氏");
                String preName = sc.next();
                if(userName.startsWith(preName)){

                    while (true) {
                        //认证通过，开始转账
                        System.out.println("请您输入转账金额");
                        double money = sc.nextDouble();

                        //判断余额是否足够
                        if(money > acc.getMoney()){
                            System.out.println("对不起，您没钱了，您最多可转账" + acc.getMoney() + "元");
                        }else{
                            //足够，可以转
                            account.setMoney(account.getMoney() + money);
                            acc.setMoney(acc.getMoney() - money);
                            System.out.println("转账成功，您的余额为：" + acc.getMoney() + "元");
                            return;
                        }
                    }
                }else{
                    System.out.println("对不起，您输入的信息有误");
                }
            }
        }
    }

    //取款功能
    private static void drawMoney(Account acc, Scanner sc) {
        System.out.println("==========用户取款操作==========");
        //判断余额，如果为0，则无法取款
        if (acc.getMoney() <= 0){
            System.out.println("对不起，当前账户余额为0，无法取钱");
            return;
        }
        while (true) {
            //余额充足，输入取款金额
            System.out.println("请输入取款金额");
            double money = sc.nextDouble();

            //判断这个金额是否能取
            if(money > acc.getQuotaMoney()){
                System.out.println("对不起，您当前取款金额超过每次限额，每次最多可取：" + acc.getQuotaMoney());
            } else if (money > acc.getMoney()) {
                System.out.println("对不起，您当前取款金额大于余额，余额为：" + acc.getMoney());
            }else{
                //可以成功取钱
                System.out.println("恭喜您，取钱" + money +"元，成功！");
                acc.setMoney(acc.getMoney() - money);
                //取钱结束，展示账户信息
                showAccount(acc);
                return;
            }
        }
    }

    //存款方法
    private static void depositMoney(Account acc, Scanner sc) {
        System.out.println("==========用户存款操作==========");
        double money = sc.nextDouble();
        while (true) {
            System.out.println("请输入存款金额：");
            //判断存款是否合理
            if(money >= 0){
                //存款输入合理，更新账户余额
                acc.setMoney(acc.getMoney() + money);
                System.out.println("恭喜您，存款成功，当前账户信息如下：");
                showAccount(acc);
                break;
            }
        }
    }


    //展示账户信息
    private static void showAccount(Account acc) {
        System.out.println("==========当前账户信息如下==========");
        System.out.println("卡号：" + acc.getCardId());
        System.out.println("用户名：" + acc.getUserName());
        System.out.println("余额：" + acc.getMoney());
        System.out.println("单次限额：" + acc.getQuotaMoney());

    }

    //开户方法，接收账户,使用main方法中的扫描器
    private static void register(ArrayList<Account> accounts,Scanner sc) {
        System.out.println("==========系统开户操作==========");
        //创建新的账户对象
        Account account = new Account();
        //给新对象录入账户信息
        System.out.println("请您输入用户名");
        String userName = sc.next();
        account.setUserName(userName);

        while (true) {
            System.out.println("请您输入账户密码");
            String passWord = sc.next();
            System.out.println("请您再次输入账户密码");
            String okPassWord = sc.next();
            if (passWord.equals(okPassWord)){
                //两次密码一致，录入密码
                account.setPassWord(okPassWord);
                break;//密码录入成功，跳出死循环
            }else{
                //两次密码不一致，重新输入
                System.out.println("对不起，两次密码不一致，请重新输入账户密码");
            }
        }

        while (true) {
            System.out.println("请您输入账户当次限额");
            double quotaMoney = sc.nextDouble();
            if(quotaMoney >= 0){
                account.setQuotaMoney(quotaMoney);
                break;
            }else{
                System.out.println("限额不能为负数，请重新输入");
            }
        }

        //为账户随机一个8位且与其他账户的卡号不重复的号码
        String cardId = getRandomCardId(accounts);
        account.setCardId(cardId);
        //把账户对象添加到集合中去
        accounts.add(account);
        System.out.println("恭喜您，" + userName + "，您开户成功，您的卡号是:" + cardId + "，请您妥善保管卡号");
    }

    //为账户生成8位随机卡号，且与其他账户卡号不重复
    private static String getRandomCardId(ArrayList<Account> accounts) {
        Random r = new Random();
        while (true) {
            //生成8位数字
            String cardId = "";

            for (int i = 0; i < 8; i++) {
                cardId += r.nextInt(10);
            }
            //判断是否重复
            Account acc = getAccountByCardId(cardId,accounts);
            if(acc == null){
                //说明无重复，可以作为新卡号
                return cardId;
            }
        }
    }
//通过卡号查找用户
    private static Account getAccountByCardId(String cardId,ArrayList<Account> accounts){
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if(acc.getCardId().equals(cardId)){
                return acc;
            }
        }
        return null;
    }
}
