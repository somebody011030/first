package com.company;
import java.util.Scanner;

interface INIT_MENU
{
    int INPUT=1, SEARCH=2,DELETE=3,EXIT=4;
}

interface INPUT_SELECT
{
    int NORMAL=1, UNIV=2,COMPANY=3;
}

class MenuChoiceException extends Exception{
    int wrongChoice;

    public MenuChoiceException(int choice) {
        super("잘못된 선택이 이뤄졌습니다.");
    }
    public void showWrongChoice() {
        System.out.println(wrongChoice+"에 해당하는 선택은 존재하지 않습니다.");
    }
}

class PhoneUnivInfo extends PhoneInfo{ //대학 동기들의 전화번호 저장
    String major;
    int year;

    public PhoneUnivInfo(String name, String phoneNum,String major,int year) {
        super(name,phoneNum);
        this.major=major;
        this.year=year;
    }
    public void showPhoneInfo() {
        System.out.println("name : "+name);
        System.out.println("phone : "+phoneNumber);
        System.out.println("major : "+major);
        System.out.println("year : "+year);
        System.out.println("");
    }

}

class PhoneCompanyInfo extends PhoneInfo{ //회사 동료들의 전화번호 저장
    String company;
    public PhoneCompanyInfo(String name,String num,String company) {
        super(name,num);
        this.company=company;
    }
    public void showPhoneInfo() {
        System.out.println("name : "+name);
        System.out.println("phone : "+phoneNumber);
        System.out.println("company : "+company);
    }

}




class MenuViewer{
    public static Scanner keyboard=new Scanner(System.in);


    public static void showMenu() {
        System.out.println("선택하세요..");
        System.out.println("1. 데이터 입력");
        System.out.println("2. 데이터 검색");
        System.out.println("3. 데이터 삭제");
        System.out.println("4. 프로그램 종료");
        System.out.print("선택 : ");

    }
}

class PhoneInfo{
    String name;
    String phoneNumber;

    public PhoneInfo(String name,String num) {
        this.name=name;
        this.phoneNumber=num;
    }

    public void showPhoneInfo() {
        System.out.println("name : "+name);
        System.out.println("phone : "+phoneNumber);
        System.out.println("");
    }

}

class PhoneBookManager{
    Scanner sc=new Scanner(System.in);
    final int MAX_CNT=100;
    PhoneInfo[] infoStorage=new PhoneInfo[MAX_CNT];
    int curCnt=0;

    static PhoneBookManager inst=null;
    public static PhoneBookManager createManagerInst() {
        if(inst==null) {
            inst=new PhoneBookManager();
        }
        return inst;
    }
    private PhoneBookManager() {}


    private PhoneInfo readFriendInfo() {
        System.out.print("이름 : ");
        String name=MenuViewer.keyboard.nextLine();
        System.out.print("전화번호 : ");
        String num=MenuViewer.keyboard.nextLine();
        return new PhoneInfo(name,num);
    }

    private PhoneInfo readUnivFriendInfo() {
        System.out.print("이름 : ");
        String name=MenuViewer.keyboard.nextLine();
        System.out.print("전화번호 : ");
        String num=MenuViewer.keyboard.nextLine();
        System.out.print("전공 : ");
        String major=MenuViewer.keyboard.nextLine();
        System.out.print("학년 : ");
        int year=MenuViewer.keyboard.nextInt();
        return new PhoneUnivInfo(name,num,major,year);
    }

    private PhoneInfo readCompanyFriendInfo() {
        System.out.print("이름 : ");
        String name=MenuViewer.keyboard.nextLine();
        System.out.print("전화번호 : ");
        String num=MenuViewer.keyboard.nextLine();
        System.out.print("회사 : ");
        String company=MenuViewer.keyboard.nextLine();
        return new PhoneCompanyInfo(name,num,company);
    }

    public void inputData() throws MenuChoiceException {
        System.out.println("데이터 입력을 시작합니다..");
        System.out.println("1. 일반, 2. 대학, 3. 회사");
        System.out.print("선택>> ");
        int choice=sc.nextInt();
        sc.nextLine();

        PhoneInfo info=null;

        if(choice<INPUT_SELECT.NORMAL || choice>INPUT_SELECT.COMPANY)
            throw new MenuChoiceException(choice);

        switch(choice) {
            case 1:
                info=readFriendInfo();
                break;
            case 2:
                info=readUnivFriendInfo();
                break;
            case 3:
                info=readCompanyFriendInfo();
                break;
        }

        infoStorage[curCnt++]=info;
        System.out.println("데이터 입력이 완료되었습니다. \n");
    }

    public void searchData() {
        System.out.println("데이터 검색을 시작합니다..");

        System.out.print("이름 : ");
        String name=MenuViewer.keyboard.nextLine();

        int dataIdx=search(name);
        if(dataIdx<0) {
            System.out.println("해당하는 데이터가 존재하지 않습니다. \n");
        }
        else {
            infoStorage[dataIdx].showPhoneInfo();
            System.out.println("데이터 검색이 완료되었습니다.");
        }
    }

    public void deleteData() {
        System.out.println("데이터 삭제를 시작합니다.");

        System.out.print("이름 : ");
        String name=MenuViewer.keyboard.nextLine();

        int dataIdx=search(name);
        if(dataIdx<0) {
            System.out.println("해당하는 데이터가 존재하지 않습니다. \n");
        }
        else {
            for(int idx=dataIdx;idx<(curCnt-1);idx++) {
                infoStorage[idx]=infoStorage[idx+1];
            }
            curCnt--;
            System.out.println("데이터 삭제가 완료되었습니다.");
        }
    }
    private int search(String name) {
        for(int idx=0;idx<curCnt;idx++) {
            PhoneInfo curInfo=infoStorage[idx];
            if(name.compareTo(curInfo.name)==0) {
                return idx;
            }
        }
        return -1;
    }

}

class Main{
    public static void main(String[] args){
        PhoneBookManager manager=PhoneBookManager.createManagerInst();
        int choice;

        while(true) {
            try {
                MenuViewer.showMenu();
                choice=MenuViewer.keyboard.nextInt();
                MenuViewer.keyboard.nextLine();

                if(choice<INIT_MENU.INPUT || choice>INIT_MENU.EXIT)
                    throw new MenuChoiceException(choice);

                switch(choice) {
                    case INIT_MENU.INPUT:
                        manager.inputData();
                        break;
                    case INIT_MENU.SEARCH:
                        manager.searchData();
                        break;
                    case INIT_MENU.DELETE:
                        manager.deleteData();
                    case INIT_MENU.EXIT:
                        System.out.println("프로그램을 종료합니다.");
                        return;
                }
            }
            catch(MenuChoiceException e){
                e.showWrongChoice();
                System.out.println("메뉴선택을 처음부터 다시 진행합니다.\n");
            }
        }
    }
}
