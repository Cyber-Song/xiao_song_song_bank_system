package com.song;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: song
 * @Date: 2023/02/24/13:00
 * @Description: 账户类Account（卡号accountId、姓名accountName、性别accountSex、密码accountPassword、余额accountBalance、每次取现额度withdrawalAmount）
 */
public class BankManage {
    private Scanner sc = new Scanner(System.in);
    //创建集合,用来存储每个账户的信息
    private ArrayList<Account> accountList = new ArrayList<>();
    //默认没有上锁
    private boolean isLock = false;
    //判断账户是否被锁定,需要全局变量,运用在userLogin()方法中
    private Account accountLock;

    //对象创建的时候就创建了,用来测试
    /*public BankManage() {
        Account account = new Account("11111111", "宋一", "男", "1", 1000, 500);
        accountList.add(account);
    }*/

    //欢迎界面
    public void start() {
        while (true) {
            System.out.println("=====欢迎进入小宋同学的银行系统=====");
            //登陆
            System.out.println("1 蓄势待发,只等出发");
            //注册
            System.out.println("2 也许您还没有一个账户");
            //退出系统
            System.out.println("3 没有钱,我不配进入小宋同学的银行");
            //任何位置输入0都可返回上一级
            System.out.println("0 只要您想要,任何时候您都可以选择逃避");
            System.out.print("进入小宋同学银行之旅:");

            //指令需要设置为字符串,否则如果输入指令不正确会报错
            String select = sc.next();
            switch (select) {
                case "1":
                    //登陆
                    userLogin();
                    break;
                case "2":
                    //注册
                    createAccount();
                    break;
                case "3":
                    //退出系统
                    System.out.println("好难过~~~我不想离开~~~");
                    System.out.println("虽然好难过,但人要学会坚强");
                    System.out.println("祝使用过此系统的朋友们在今后的日子中所求皆所愿,所行化坦途,多喜乐,常安宁");
                    //终止虚拟机
                    System.exit(0);
                case "0":
                    //没有上一级了,现在为最初的界面
                    System.out.println("到头了,还想逃避,哪里跌倒就要哪里站起来......");
                    System.out.println("----------------------------------------------------------------------------------------------------");
                    break;
                default:
                    //录入的指令有错
                    System.out.println("喂,想啥呢,醒醒了兄弟,你自己看看你输入的是个啥......");
                    System.out.println("----------------------------------------------------------------------------------------------------");
                    break;
            }
        }
    }

    //创建账户
    private int createAccount() {
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("=====选择小宋同学银行系统,保您起的早睡的香=====");

        while (true) {
            //调用方法自动生成卡号
            String accountId = createCardId();

            System.out.println("请问你幸福吗？不!我姓宋...");
            System.out.println("在此问过兄台的大名:");
            //键盘录入姓名
            String accountName = sc.next();
            System.out.println("----------------------------------------------------------------------------------------------------");

            //如果键盘输入的是0,直接结束方法，返回上一层,下面出现的代码相似
            //不撞南墙不回头
            if (accountName.equals("0")) {
                return 1;
            }

            System.out.println("Are You A Boy ???");
            System.out.println("女孩子那么可爱,你肯定不是啦~~~");
            System.out.print("也许大概可能你是:");
            String accountSex;
            //循环判断性别录入是否合法
            while (true) {
                accountSex = sc.next();
                if (accountSex.equals("0")) {
                    return 1;
                }
                //设置性别只能为“男”或“女,”不能用或，要用与运算，因为是反着判断
                if (!(accountSex.equals("男")) && !(accountSex.equals("女"))) {
                    System.out.println("好啦,好啦,知道啦,你不是地球人,不要太明显啦......");
                } else {
                    break;
                }
            }

            //调用方法判断是先生还是女士
            String appellation = judgmentIdentity(accountSex);

            System.out.println("----------------------------------------------------------------------------------------------------");
            System.out.println("你想要裸奔吗???不想的话还不赶快设置一个密码");
            //键盘录入密码
            String accountPassword = sc.next();
            if (accountPassword.equals("0")) {
                return 1;
            }


            System.out.println("话说你刚才设置密码了????");
            //循环确定输入的密码是否一致
            while (true) {
                String accountConfirmPassword = sc.next();
                if (accountConfirmPassword.equals("0")) {
                    return 1;
                }

                //如果两次输入的密码相同就停止循环,否则就重新输入,当然你选择逃避也是可以的
                if (accountConfirmPassword.equals(accountPassword)) {
                    break;
                } else {
                    //提示再次输入确认密码
                    System.out.println("你是鱼的记忆吗? 太菜了吧......");
                }
            }

            System.out.println("----------------------------------------------------------------------------------------------------");
            System.out.println("知道你很有钱,但是现在钱在小宋的裤兜里哦,小宋不想让它们一下子都跑掉,只能委屈你啦~~~");
            System.out.println("๐·°(৹˃̵﹏˂̵৹)°·๐ 你,你,你想要每次从小宋的裤兜里掏出多少钱???");
            //输入每次取款的额度
            double withdrawalAmount = sc.nextDouble();
            if (withdrawalAmount == 0) {
                return 1;
            }

            Account account = new Account(accountId, accountName, accountSex, accountPassword, withdrawalAmount);
            accountList.add(account);

            System.out.println("----------------------------------------------------------------------------------------------------");
            System.out.println("哎呦,不错哦,地球人" + account.getAccountName() + appellation + ",完成第一步啦,允许进行小宋的银行系统,通行证为" + account.getAccountId());

            System.out.println("拥有一个账户不能满足你吗: YES/NO");
            System.out.println("小宋提醒:做人不可太贪心哦");

            boolean flag = true;

            while (true) {
                String yesOrNo = sc.next();
                if (yesOrNo.equalsIgnoreCase("NO") || yesOrNo.equalsIgnoreCase("N")) {
                    flag = false;
                    break;
                } else if (yesOrNo.equalsIgnoreCase("YES") || yesOrNo.equalsIgnoreCase("Y")) {
                    break;
                } else {
                    System.out.println("两个选择,还不够你选吗???");
                }
            }
            if (!flag) {
                break;
            }
        }
        return 0;
    }

    //随机生成卡号
    private String createCardId() {
        Random r = new Random();

        while (true) {
            //判断条件，如果为false，则说明卡号已经存在，为true表示卡号唯一
            boolean flag = true;
            //获取随机的数字
//            String[] randomNumber = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            String cardId = "";
            //生成卡号第一位不能为0
            int indexFirst = r.nextInt(9) + 1;
            //将随机的字符累加成字符串
            cardId += indexFirst;
            for (int i = 0; i < 7; i++) {
                int index = r.nextInt(10);
                cardId += index;
            }
            //判断卡号是否重复
            for (int i = 0; i < accountList.size(); i++) {
                //获取账户集合中的每一个元素
                Account account = accountList.get(i);
                //判断是否有重复的卡号
                if (account.getAccountId().equals(cardId)) {
                    flag = false;
                    break;
                }
            }

            //如果卡号唯一就返回卡号
            if (flag) {
                return cardId;
            }
            //如果boolean反着来，
            /*
            if (!flag) {
                break;
            }
             */
        }
    }

    //用户登陆界面
    private void userLogin() {
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("=====呜啦啦呜啦啦乌拉乌拉嘞=====");
        //判断该银行中是否存在银行账户,系统中没有账户的话不允许登陆
        if (accountList.size() == 0) {
            System.out.println("空空如也,你还想要怎样......");
            //直接跳转到用户开户界面
            //没有账户的话要进行开户
            System.out.println("或许你可以改变一下现状??? YES/NO");
            System.out.println("人生嘛,总要改变一下才会看到意想不到的惊喜");
            while (true) {
                String select = sc.next();
                if (select.equals("0")) {
                    return;
                }
                if (select.equalsIgnoreCase("YES") || select.equalsIgnoreCase("Y")) {
                    //进行账户创建

                    //判断是否需要返回
                    int num = createAccount();

                    //如果为1退出
                    if (num == 1) {
                        return;
                    }

                    //创建账户后选择要进行的操作
                    //系统登陆
                    System.out.println("1 进去看一下吧");
                    //返回上一级
                    System.out.println("0 切,什么**,我才不要进去");
                    System.out.print("我命由我不由天:");
                    String select1 = sc.next();
                    if (select1.equals("0")) {
                        return;
                    } else {
                        break;
                    }
                } else if (select.equalsIgnoreCase("NO") || select.equalsIgnoreCase("N")) {
                    return;
                } else {
                    System.out.println("两个选择,还不够你选吗???");
                }
            }
        }

        //输入账号ID
        System.out.println("掏出你的通行证吧......");

        //声明账户ID
        String accountId;

        //设置初始坐标
        int index = -1;

        while (true) {
            //键盘录入ID
            accountId = sc.next();

            if (accountId.equals("0")) {
                return;
            }

            //判断一下用户输入的卡号是否存在,如果存在就将索引赋值给index
            for (int i = 0; i < accountList.size(); i++) {
                accountLock = accountList.get(i);
                if (accountLock.getAccountId().equals(accountId)) {
                    index = i;
                }
            }

            //如果index没有被重新赋值的话,就说明卡号不存在
            if (index == -1) {
                System.out.println("你是偷渡来的吧,偷渡客还不做攻略......");
            } else {
                //获取指定索引的Account对象
                //上面求出了输入卡号对应的集合下标，通过下标创建Account对象获取对象的密码属性
                accountLock = accountList.get(index);
                if (accountLock.isLock()) {
                    //输入密码错误次数超过三次,账户被锁定
                    System.out.println("哈哈哈哈哈哈,被我发现了吧,狡猾的人类,你是进不去的,明天再来吧");
                    System.exit(0);
                }
                break;
            }
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
        //提示输入密码
        System.out.println("万能钥匙连WIFI都破解不了,你还指望它吗???");
        System.out.println("人一路走来,会遇到很多很多人,不断相识,不断错过,也许错过才是常态,多年后我们才能懂得人生活着就好,我想你已经知道答案了");
        for (int i = 0; i < 3; i++) {

            String inputPassword = sc.next();
            if (inputPassword.equals("0")) {
                return;
            }

            if (!(inputPassword.equals(accountLock.getAccountPassword()))) {
                if (i == 2) {
                    //如果全局变量的属性lock属性为true,就是输入三次密码错误,账户今日被锁定
                    accountLock.setLock(true);
                    System.out.println("你到底行不行啊,三次机会都不中,可以回家洗洗睡了......");
                    System.out.println("----------------------------------------------------------------------------------------------------");
                    while (true) {
                        //登陆其他账户
                        System.out.println("按 1 大哥有钱,再换一个");
                        //退出系统
                        System.out.println("按 0 技不如人,回炉重造");
                        System.out.println("010100110到底是0还是1:");
                        String select = sc.next();
                        if (select.equals("0")) {
                            //输入0直接结束
                            System.out.println("你已经很努力了,放过自己吧,山重水复疑无路,柳暗花明才能又一村嘛!");
                            System.exit(0);
                            //如果输入1的话就再跳转到登陆的界面
                        } else if (select.equals("1")) {
                            userLogin();
                            return;
                        } else {
                            //提示输入的指令不正确,重新输入
                            System.out.println("咳,看这程序员,还没五十呢,腰不行了,颈椎也不中了,这眼也看不清了,上面的数字看不见啊......");
                            System.out.println("年轻人啊,还是要早早的保温杯里泡枸杞");
                        }
                    }
                } else {
                    //提醒今日还剩多少输入密码的机会
                    System.out.println("年轻才有试错的机会,你瞅瞅就剩" + (2 - i) + "次机会了,要深思而后行......");
                }
            } else {
                break;
            }
        }

        //获得先生或者女士
        String appellation = judgmentIdentity(accountLock.getAccountSex());


        System.out.println("哇哦," + accountLock.getAccountName() + appellation + ",竟然进来了,看来是难不倒你了,那就让我们坦诚相待吧,再提醒你一下吧,通行证为" + accountLock.getAccountId());

        //进入用户操作界面,将根据账户id获得到的对象和对象的下标作为形参，传给用户操作的方法
        userActions(accountLock, index);
    }

    //用户操作界面
    //可以不传形参,直接将account的级别提到最高级
    private void userActions(Account account, int index) {
        while (true) {
            System.out.println("----------------------------------------------------------------------------------------------------");
            System.out.println("=====" + account.getAccountName() + judgmentIdentity(account.getAccountSex()) + ",让我们来为所欲为吧=====");
            //查询账户
            System.out.println("1 坦诚相见");
            //存款
            System.out.println("2 存钱是不能发家致富的");
            //取款
            System.out.println("3 GTA5模式也不是不可以");
            //转账
            System.out.println("4 $重要我重要?当然$重要");
            //修改密码
            System.out.println("5 人靠衣装,马靠鞍,密码还是要重装");
            //退出
            System.out.println("6 开头见吧");
            //注销
            System.out.println("7 一切归零,重头再来");
            System.out.print("条条大路通罗马,有人出生在罗马:");
            String select = sc.next();

            switch (select) {
                case "1":
                    //展示相关信息
                    queryAccount(account);
                    break;
                case "2":
                    //存款
                    deposit(account);
                    break;
                case "3":
                    //取款
                    withdrawal(account);
                    break;
                case "4":
                    //转账
                    transfer(account);
                    break;
                case "5":
                    int num1 = changeThePassword(account);
                    if (num1 == 1) {
                        return;
                    }
                    break;
                //返回
                case "6":
                    return;//输入6后,return,返回userActions后,下面没代码直接结束,返回第37行,方法执行结束,直接break结束switch,返回最初的界面
                //销户
                case "7":
                    int num = deleteAccount(account, index);
                    //如果注销成功的话返回两级
                    if (num == 0) {
                        return;
                    }
                    break;
                default:
                    //输入的字符不合法
                    System.out.println("有人出生在罗马,有人马拉反着跑......");
                    break;
            }
        }
    }

    //销户
    private int deleteAccount(Account account, int index) {
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("=====有谁会知道,也许擦肩而过就是最后一面=====");
        //提示是否要进行销户
        System.out.println("您确定要毁尸灭迹吗? YES/NO");
        while (true) {
            String input = sc.next();
            if (input.equalsIgnoreCase("YES") || input.equalsIgnoreCase("Y")) {
                //如果账户金额不为0,则不允许销户
                if (account.getAccountBalance() > 0) {
                    System.out.println("哇塞,西红柿首富吗???");
                    System.out.println("你要留这么多钱给小宋的话,我要好好想想怎么花了......");
                    return 1;
                } else {
                    //删除账户成功
                    accountList.remove(index);
                    System.out.println("风萧萧兮易水寒,壮士一去兮不复返!");
                    return 0;
                }
            } else if (input.equalsIgnoreCase("NO") || input.equalsIgnoreCase("n")) {
                return 1;
            } else {
                System.out.println("两个选择,还不够你选吗???");
            }
        }
    }

    //修改密码
    private int changeThePassword(Account account) {
        if (isLock) {
            //提示账户修改密码功能已锁定,提醒明日再试
            System.out.println("上帝给你关上了一扇门,放心,还会为你关上一扇窗");
            System.out.println("不过,明天会更好的啦");
            return 0;
        }
        //修改密码界面
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("==知错就改,善莫大焉==");
        System.out.println("过往的字符也许是一个名字,也许是一个故事,也许是一段过往,也请我们把这段字符深深的记在心中");
        System.out.println("喜新厌旧,人之常情,但陪伴是最长情的告白:");
        //三次机会
        for (int i = 0; i <= 3; i++) {
            String oldPassword = sc.next();
            if (oldPassword.equals("0")) {
                return 0;
            }
            //如果输入的旧密码正确,就提示输入新密码
            if (oldPassword.equals(account.getAccountPassword())) {
                System.out.println("人面不知何处去,桃花依旧笑春风");
                System.out.println("人不知道去了哪里,密码还是需要重新载入的......");
                String newPassword = sc.next();
                if (newPassword.equals("0")) {
                    return 0;
                }
                //提示输入确认密码
                System.out.println("我是复读机我是复读机......");
                while (true) {
                    String password = sc.next();
                    if (password.equals("0")) {
                        break;
                    }
                    if (password.equals(newPassword)) {
                        break;
                    } else {
                        //提示输入的确认密码不正确,请重新输入
                        System.out.println("你个*男,刚认识的就不记得了吗???");
                    }
                }
                //密码修改成功,重新登陆
                account.setAccountPassword(newPassword);
                System.out.println("帽子一带,退出重来!");
                return 1;
            } else {
                //三次机会使用完了,提示次数用完,明日再试
                if (3 - i == 0) {
                    System.out.println("不是什么都有第二次机会的,况且你还使用了3次,不过小宋在这里告诉你也许你会转生成为异世界.......");
                    isLock = true;
                } else {
                    //提示还剩多少次机会
                    System.out.println("只有再一再二,如果还要再三再四的话,就说明你需要重修马原了,您今日还剩" + (3 - i) + "次机会,就可以重新进入哲学的课堂了......");
                }
            }
        }
        return 0;
    }

    //转账
    private void transfer(Account account) {
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("=====我的钱是我的,你的钱也是我的=====");

        //判断系统中的账户是否有两个,当前系统,账户不足2个,不能转账......
        if (accountList.size() < 2) {
            System.out.println("你一根独苗,哪里还有人......");
            //判断账户余额是否小于100,有钱没钱,当前账户余额不足,不允许转账......
        } else if (account.getAccountBalance() < 100) {
            System.out.println("哇塞,比我还穷,要向小宋借点吗?当然那是不可能的......");
        } else {
            //输入转账卡号
            System.out.println("门前大桥下,游过一群鸭....话说哪个门?");
            //循环输入卡号判断账户是否存在
            while (true) {
                String inputCardId = sc.next();
                if (inputCardId.equals("0")) {
                    return;
                }
                int index = -1;
                Account account1;
                //循环集合中的元素,判断每个元素的卡号属性是否与输入的卡号一致
                for (int i = 0; i < accountList.size(); i++) {
                    account1 = accountList.get(i);
                    if (account1.getAccountId().equals(inputCardId)) {
                        index = i;
                    }
                }

                //如果index还是最初的值的话,就说明没有找到
                if (index == -1) {
                    //不存在该账户,请重新输入
                    System.out.println("海底捞针小宋可以做到,井里捞月小宋可办不到,要不你来......");
                } else {
                    //或许被转元素对象
                    account1 = accountList.get(index);
                    //获取被转对象的名字
                    String getName = account1.getAccountName();
                    //将被转对象的名字第一个字设为星星
                    String replaceFirstChar = getName.replace(getName.charAt(0), '*');
                    //提示在为谁转账
                    System.out.println("这位" + replaceFirstChar + "你认识???");
                    //提示输入姓氏确认
                    System.out.println("也许是?赵钱孙李,周吴郑王......");
                    while (true) {
                        String firstName = sc.next();
                        if (firstName.equals("0")) {
                            return;
                        }
                        //判断输入的姓氏是否相同
                        if (firstName.equals(getName.charAt(0) + "")) {
                            //如果符合,提示输入转账金额
                            System.out.println("我有钱,说吧,你想要多少......");
                            double money = sc.nextDouble();
                            if (money == 0) {
                                return;
                            }
                            //当前转账的用户
                            double resultAccount = account.getAccountBalance() - money;
                            //被转账的用户
                            double resultAccount1 = account1.getAccountBalance() + money;
                            account.setAccountBalance(resultAccount);
                            account1.setAccountBalance(resultAccount1);
                            //提示转账成功
                            System.out.println("西湖的水,我的泪,钱包飕飕变为空,偷偷摸一摸还有几张:" + account.getAccountBalance());
                            break;
                        } else {
                            //提示输入的姓氏不正确,重新输入
                            System.out.println("你是谁啊?尔康吗???......");
                        }
                    }
                    break;
                }
            }
        }
    }

    //取款
    private void withdrawal(Account account) {
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("=====我丢丢,其他银行破产了吗?进小宋兜里的钱就是小宋的,你休想=====");
        //循环判断输入的金额是否合法
        boolean flag = true;
        while (true) {
            //提醒取款的金额
            System.out.println("你,你,你想从小宋这里拿走多少(☍﹏⁰)。");
            while (true) {
                //要把取款金额放到循环里面,不然会发生死循环
                double money = sc.nextDouble();
                if (money == 0) {
                    flag = false;
                    break;
                }
                //判断账户是否有钱
                if (account.getAccountBalance() >= 100) {
                    //判断取款金额是不是100的倍数
                    if (money > 0) {
                        if (money >= 100 && money % 100 == 0) {
                            if (money > account.getAccountBalance()) {
                                //取款金额大于余额
                                System.out.println("小伙子,志向远大呀,不过仅靠" + account.getAccountBalance() + "元是模仿不了GTA的,只会获得豪华单人间......");
                                //取款金额大于当前限额
                            } else if (money > account.getWithdrawalAmount()) {
                                System.out.println("你每次只能从小宋这里拿到" + account.getWithdrawalAmount() + ",小宋会时刻紧盯着你的,当你凝望深渊的时候,深渊也在凝望你......");
                            } else {
                                //获取取款账户的金额
                                double accountBalance = account.getAccountBalance();
                                //减去取款金额
                                double resultMoney = accountBalance - money;
                                //将最终金额重设一下
                                account.setAccountBalance(resultMoney);
                                //提示账户剩余金额
                                System.out.println("快走快走,剩下的" + resultMoney + "元都是小宋的");
                                break;
                            }
                        } else {
                            //提示取款金额必须是100的倍数,请重新输入
                            System.out.println("还有零有整的,你休想多从小宋这里拿走一分钱......");
                        }
                    } else {
                        System.out.println("警报警报~~~biubiubiu");
                    }

                } else {
                    //提示余额不足,不支持取款
                    System.out.println("嚯哈哈哈哈哈,没钱还来小宋的银行,洗洗睡吧,梦里啥都有......");
                    //按exit退出系统
                    System.out.println("exit强制回家睡觉");
                    String s = sc.next();
                    if (s.equals("exit")) {
                        return;
                    }
                }
            }
            if (!flag) {
                break;
            }
            //需要放在最外层
            //判断是否进行取款
            System.out.println("是否狠心掏空小宋的钱包,喂饱自己的? YES/NO");
            System.out.println("好吧,你不用说了,我已经知道答案了༼ಢ_ಢ༽");
            while (true) {
                String select = sc.next();
                if (select.equalsIgnoreCase("NO") || select.equalsIgnoreCase("N")) {
                    return;
                } else if (select.equalsIgnoreCase("YES") || select.equalsIgnoreCase("Y")) {
                    break;
                } else {
                    System.out.println("两个选择,还不够你选吗???");
                }
            }
        }
    }

    //存款
    private void deposit(Account account) {
        System.out.println("=====哈哈哈哈哈,我亲爱的宝贝,快来快来=====");
        //如果不把maney定义在外边,再次存钱的时候就会被覆盖掉
        /*
        ==存款界面==
        请您输入要存款的金额:
        1000
        您的当前账户总金额为1000.0
        您是否要继续存款 YES/NO
        YES
        请您输入要存款的金额:
        1000
        您的当前账户总金额为1000.0
        您是否要继续存款 YES/NO
         */
        while (true) {
            //提示输入存款金额
            System.out.println("来吧,大方一点,不要扣扣嗖嗖的:");
            double maney = sc.nextDouble();
            if (maney < 0) {
                System.out.println("想从我口袋里拿钱???门都没有,还是老老实实做人吧......");
                continue;
            }
            if (maney == 0) {
                return;
            }
            //account.getAccountBalance() += maney;这样写是错误的,因为余额是封装在account类中的,不允许直接修改,只能使用set方法修改
            double resultManey = account.getAccountBalance() + maney;

            //出现上面情况的原因是没有将集合中的金额重新设置
            account.setAccountBalance(resultManey);

            //提示存入的金额,账户的金额
            System.out.println("才给" + maney + "这一点?我就勉勉强强收下吧,现在的话一共" + resultManey);
            System.out.println("兄弟,只有努力挣钱才能走上人生巅峰");
            //提示是否要继续存钱
            System.out.println("别人怎么对我,是我的因果; 我怎么对别人,是我的修行...");
            System.out.println("我觉得不够,再给点 YES/NO");
            boolean flag = true;
            while (true) {
                String s = sc.next();
                if (s.equalsIgnoreCase("NO") || s.equalsIgnoreCase("N")) {
                    flag = false;
                    break;
                } else if (s.equalsIgnoreCase("YES") || s.equalsIgnoreCase("Y")) {
                    break;
                } else {
                    System.out.println("两个选择,还不够你选吗???");
                }
            }
            if (!flag) {
                break;
            }
        }
    }

    //查询账户的相关信息
    private void queryAccount(Account account) {
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("=====嘿嘿嘿,让我们乘着阳光,海上冲浪......=====");
        //卡号
        System.out.println("3.1415926...都没你的卡号难记:" + account.getAccountId());
        //姓名
        System.out.println("王也张楚岚诸葛青都没你的大名响亮:" + account.getAccountName());
        //性别
        System.out.println("女娲创造了亚当和夏娃,然后用鞭子甩出了你:" + account.getAccountSex());
        //密码
        System.out.println("密码看是不可能给你看的啦,人与人之间哪有信任: **********");
        //账户余额
        System.out.println("比尔盖茨和你谁更有钱?你看着自己的" + account.getAccountBalance() + "块,默默的闭上了嘴......");
        //每次取现额度
        System.out.println("月光两行泪,超前需谨慎,对自己狠点,每天有个馒头钱就好了,你每次可以从小宋这里拿走的馒头钱为" + account.getWithdrawalAmount());
    }

    //判断是先生还是女士
    public String judgmentIdentity(String accountSex) {
        return accountSex.equals("男") ? "先生" : "女士";
    }
}
