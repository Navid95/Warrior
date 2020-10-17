package noc;

import com.jcraft.jsch.*;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {

        try {
            String workingDirectory, scriptDirectory, scriptName;
            String host,username,password;
//            WarriorUcip warriorUcip = new WarriorUcip();
            Scanner scanner = new Scanner(System.in);
            int action=0;
            String command = "" , choice = "";

//            **************** Connecting to server ****************
            System.out.println("Enter Server IP: ");
            host=scanner.nextLine();
            System.out.println("Enter Username: ");
            username=scanner.nextLine();
            System.out.println("Enter Password: ");
            password=scanner.nextLine();
            WarriorUcip warriorUcip = new WarriorUcip(host , username ,password);

//            ************************ Menu ************************
            while (!choice.equals("exit")) {
                System.out.println("Select needed action from below list : [1-2]");
                System.out.println("1- Exec command");
                System.out.println("2- Sftp put file on server");
                System.out.println("3- Sftp get file from server");
                System.out.println("4- Exit\n");
                System.out.print("Enter a number: ");
                action = Integer.parseInt(scanner.nextLine());
                switch (action) {
                    case 1:
                        System.out.print("Enter your command: ");
                        command = scanner.nextLine();
                        System.out.println(warriorUcip.exec_cmd(command));
                        break;
                    case 2:
                        String src , dst;
                        System.out.println("Enter file path on local computer: ");
                        src = scanner.nextLine();
                        System.out.println("Enter destination path on server computer: ");
                        dst = scanner.nextLine();
                        warriorUcip.put_sftp(src,dst);
                        break;
                    case 3:
                        String src1 , dst1;
                        System.out.println("Enter file path on server computer: ");
                        src1 = scanner.nextLine();
                        System.out.println("Enter destination path on local computer: ");
                        dst1 = scanner.nextLine();
                        warriorUcip.get_sftp(src1,dst1);
                        break;
                    case 4:
                        System.exit(0);
                }
                System.out.println("Press Enter to continue");
                scanner.nextLine();
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
            warriorUcip.closeSession();
        } catch (JSchException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(2);
        } catch (SftpException e) {
            e.printStackTrace();
            System.exit(3);
        } catch (java.lang.NumberFormatException e){
            e.printStackTrace();
            System.exit(4);
        } catch (Exception e){
            e.printStackTrace();
            System.exit(999);
        }
    }
}
