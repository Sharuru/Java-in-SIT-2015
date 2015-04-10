/**
 * Created by Mave on 2015/4/10 0010.
 * Extra question: Design a role module for a game which each character have one appearance and different skills sorted by run, attack and defend.
 */
interface IAppSkill {
    void appearance();
}

interface IAttackSkill {
    void attack();
}

interface IDefendSkill {
    void defend();
}

interface IRunSkill {
    void run();
}

//Skills
class AppElichika implements IAppSkill {
    @Override
    public void appearance() {
        System.out.println("I am Eli Ayase.");
    }
}

class AttKashikoi implements IAttackSkill {
    @Override
    public void attack() {
        System.out.println("Kashikoi, Kawaii?");
    }
}

class DefElichika implements IDefendSkill {
    @Override
    public void defend() {
        System.out.println("Elichika!");
    }
}

class RunKhorosho implements IRunSkill {
    @Override
    public void run() {
        System.out.println("Khorosho!");
    }
}

class Roles {
    IAppSkill iapp;
    IAttackSkill iattack;
    IDefendSkill idefend;
    IRunSkill irun;

    void appearance() {
        iapp.appearance();
    }

    void attack() {
        iattack.attack();
    }

    void defend() {
        idefend.defend();
    }

    void run() {
        irun.run();
    }

    public void setIapp(IAppSkill iapp) {
        this.iapp = iapp;
    }

    public void setIattack(IAttackSkill iattack) {
        this.iattack = iattack;
    }

    public void setIdefend(IDefendSkill idefend) {
        this.idefend = idefend;
    }

    public void setIrun(IRunSkill irun) {
        this.irun = irun;
    }
}

public class Ex42_InterfaceClass {
    public static void main(String[] args) {
        Roles chara1 = new Roles();
        chara1.setIapp(new AppElichika());
        chara1.setIattack(new AttKashikoi());
        chara1.setIdefend(new DefElichika());
        chara1.setIrun(new RunKhorosho());

        chara1.appearance();
        chara1.attack();
        chara1.defend();
        chara1.run();
    }
}
